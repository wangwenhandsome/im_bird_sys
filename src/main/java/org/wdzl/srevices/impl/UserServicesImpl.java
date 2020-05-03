package org.wdzl.srevices.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wdzl.enums.SearchFriendsStatusEnum;
import org.wdzl.mapper.FriendsRequestMapper;
import org.wdzl.mapper.MyFriendsMapper;
import org.wdzl.mapper.UserMapper;
import org.wdzl.pojo.MyFriends;
import org.wdzl.pojo.User;
import org.wdzl.srevices.UserServices;
import org.wdzl.utils.FastDFSClient;
import org.wdzl.utils.FileUtils;
import org.wdzl.utils.QRCodeUtils;

import java.io.IOException;

/**
 * @Author: 王文
 * @Date: 2020/4/7 11:39
 * @Version: 1.0
 * @Description:
 */
@Service
public class UserServicesImpl implements UserServices {
    //注入Mapper
    @Autowired
    UserMapper userMapper;

    @Autowired
    MyFriendsMapper myFriendsMapper;

    @Autowired
    FriendsRequestMapper friendsRequestMapper;

    @Autowired
    Sid sid;

    @Autowired
    QRCodeUtils qrCodeUtils;

    @Autowired
    FastDFSClient fastDFSClient;

    @Override
    public User getUserById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User queryUserNameIsExit(String username) {
        return userMapper.queryUserNameIsExit(username);
    }

    @Override
    public User insert(User user) {
         String userId=sid.nextShort();
         //为每一个注册用户生成一个唯一的二维码
        String qrCodePath="D://user"+userId+"qrcode.png";
        //创建二维码对象信息
        qrCodeUtils.createQRCode(qrCodePath,"bird_qrcode"+user.getUsername());
        MultipartFile qrcodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeURl ="";
        try {
            qrCodeURl = fastDFSClient.uploadQRCode(qrcodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setId(userId);
        user.setQrcode(qrCodeURl);
         userMapper.insert(user);
         return user;
    }

    @Override
    public User updateUserinfo(User user) {
        userMapper.updateByPrimaryKeySelective(user);
        User result= userMapper.selectByPrimaryKey(user.getId());
        return result;
    }

    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUserName) {
        User user = queryUserNameIsExit(friendUserName);
        //1.搜索的用户如果不存在，则返回【无此用户】
        if(user==null){
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        //2.搜索的账号如果是你自己，则返回【不能添加自己】
        if(myUserId.equals(user.getId())){
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        //3.搜索的朋友已经是你好友，返回【该用户已经是你的好友】
        MyFriends myfriend = new MyFriends();
        myfriend.setMyUserId(myUserId);
        myfriend.setMyFriendUserId(user.getId());
        MyFriends myF = myFriendsMapper.selectOneByExample(myfriend);
        if(myF!=null){
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }
}
