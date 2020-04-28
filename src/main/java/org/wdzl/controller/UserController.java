package org.wdzl.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.wdzl.bo.UserBO;
import org.wdzl.pojo.User;
import org.wdzl.srevices.UserServices;
import org.wdzl.utils.FastDFSClient;
import org.wdzl.utils.FileUtils;
import org.wdzl.utils.IWdzlJSONResult;
import org.wdzl.utils.MD5Utils;
import org.wdzl.vo.UserVo;

/**
 * @Author: 王文
 * @Date: 2020/4/7 11:41
 * @Version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServices userServices;

    @Autowired
    FastDFSClient fastDFSClient;

    @RequestMapping("/getUser")
    public String getUserById(String id, Model model){
        User user= userServices.getUserById(id);
        model.addAttribute("user",user);
        return "user_list";
    }

   //用户登陆与注册一体化方法
    @RequestMapping("/registerOrLogin")
    @ResponseBody
    public IWdzlJSONResult registerOrlogin(User user) {
        User userResult = userServices.queryUserNameIsExit(user.getUsername());
        if (userResult != null) {//此用户存在，可登陆
           if (!userResult.getPassword().equals(MD5Utils.getPwd(user.getPassword()))){
               return IWdzlJSONResult.errorMsg("密码不正确");
           }
        }else{//注册

         user.setNickname("大朋友");
         user.setQrcode("");
         user.setPassword(MD5Utils.getPwd(user.getPassword()));
         user.setFaceImage("");
         user.setFaceImageBig("");
         userResult=userServices.insert(user);
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(userResult,userVo);
        return IWdzlJSONResult.ok(userVo);
    }
    @RequestMapping("/uploadFaceBase64")
    @ResponseBody
    //用户头像上传访问
    public IWdzlJSONResult registerOrlogin(@RequestBody  UserBO userBO) throws Exception {
        //获取前端传过来的Base64的字符串，然后转化为文件对象在进行上传
        String base64Data=userBO.getFaceData();
        String userFacePath="D:\\"+userBO.getUserId()+"userFaceBase64.png";
        //调用FileUtils类的方法将base64字符串转为文件对象
        FileUtils.base64ToFile(userFacePath,base64Data);
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);
        //获取fastDFS上传图片的路径
        String url= fastDFSClient.uploadBase64(multipartFile);
        System.out.println(url);
        String thump="_150×150.";
        String[] arr = url.split("\\.");
        String thumpImgUrl=arr[0]+thump+arr[1];

//        String bigllFace ="sddsdsdsdcx.png";
//        String smallFace ="sddsdsdsdcx_150×150.png";
        //更新用户头像
        User user=new User();
        user.setId(userBO.getUserId());
        user.setFaceImage(thumpImgUrl);
        user.setFaceImageBig(url);
        User result = userServices.updateUserinfo(user);
        return IWdzlJSONResult.ok(result);
    }
    @RequestMapping("/setNickname")
    @ResponseBody
    //修改昵称方法
    public IWdzlJSONResult setNickName(User user){
        User userResult=userServices.updateUserinfo(user);

        return IWdzlJSONResult.ok(userResult);
    }
}
