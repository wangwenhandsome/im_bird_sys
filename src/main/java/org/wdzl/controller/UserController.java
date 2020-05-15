package org.wdzl.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.wdzl.bo.UserBO;
import org.wdzl.enums.SearchFriendsStatusEnum;
import org.wdzl.pojo.User;
import org.wdzl.services.UserServices;
import org.wdzl.utils.FastDFSClient;
import org.wdzl.utils.FileUtils;
import org.wdzl.utils.IWdzlJSONResult;
import org.wdzl.utils.MD5Utils;
import org.wdzl.vo.FriendsRequestVo;
import org.wdzl.vo.UserVo;

import java.util.List;

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
        String thump="_150x150.";
        String[] arr = url.split("\\.");
        String thumpImgUrl=arr[0]+thump+arr[1];

//        String bigllFace ="sddsdsdsdcx.png";
//        String smallFace ="sddsdsdsdcx_150×150.png";
        //更新用户头像
        User user=new User();
        user.setId(userBO.getUserId());
        user.setFaceImage(thumpImgUrl);
        user.setFaceImageBig(url);
        User result = userServices.updateUserInfo(user);
        return IWdzlJSONResult.ok(result);
    }
    @RequestMapping("/setNickname")
    @ResponseBody
    //修改昵称方法
    public IWdzlJSONResult setNickName(User user){
        User userResult=userServices.updateUserInfo(user);

        return IWdzlJSONResult.ok(userResult);
    }
    //搜索好友的方法
    @RequestMapping("/searchFriend")
    @ResponseBody
    public IWdzlJSONResult searchFriend(String myUserId,String friendUserName){
        /**
         * 前置条件：
         * 1.搜索的用户如果不存在，则返回【无此用户】
         * 2.搜索的账号如果是你自己，则返回【不能添加自己】
         * 3.搜索的朋友已经是你好友，返回【该用户已经是你的好友】
         */
        Integer status = userServices.preconditionSearchFriends(myUserId,friendUserName);
        if(status== SearchFriendsStatusEnum.SUCCESS.status){
            User user = userServices.queryUserNameIsExit(friendUserName);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            return IWdzlJSONResult.ok(userVo);
        }else{
            String msg = SearchFriendsStatusEnum.getMsgByKey(status);
            return IWdzlJSONResult.errorMsg(msg);
        }
    }
    //搜索好友的方法
    @RequestMapping("/addFriendRequest")
    @ResponseBody
    public IWdzlJSONResult addFriendRequest(String myUserId,String friendUserName){
        if(StringUtils.isBlank(myUserId)||StringUtils.isBlank(friendUserName)){
            return IWdzlJSONResult.errorMsg("好友信息为空");
        }
        Integer status=userServices.preconditionSearchFriends(myUserId,friendUserName);
        if (status==SearchFriendsStatusEnum.SUCCESS.status){
            userServices.sendFriendRequest(myUserId,friendUserName);
        } else{
            String msg=SearchFriendsStatusEnum.getMsgByKey(status);
            return IWdzlJSONResult.errorMsg(msg);
        }
        return IWdzlJSONResult.ok();
    }
    //好友列表的查询
    @RequestMapping("/queryFriendRequestList")
    @ResponseBody
    public IWdzlJSONResult queryFriendRequestList(String UserId){
        List<FriendsRequestVo> friendsRequestVoList = userServices.queryFriendRequestList(UserId);
        return IWdzlJSONResult.ok(friendsRequestVoList);
    }
}
