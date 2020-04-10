package org.wdzl.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wdzl.pojo.User;
import org.wdzl.srevices.UserServices;
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

         user.setNickname("王文");
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
}
