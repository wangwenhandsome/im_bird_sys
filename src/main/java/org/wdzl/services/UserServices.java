package org.wdzl.services;

import org.wdzl.pojo.User;

/**
 * @Author: 王文
 * @Date: 2020/4/7 11:37
 * @Version: 1.0
 * @Description:
 */
public interface UserServices {
     User getUserById(String id);
     //根据用户名查找指定用户对象
     User queryUserNameIsExit(String username);
     //保存
     User insert(User user);
     //修改用户
     User updateUserInfo(User user);
     //搜索好友的前置条件接口
     Integer preconditionSearchFriends(String myUserId,String friendUserName);
     //发送好友请求
     void  sendFriendRequest(String myUserId,String friendUserName);
}
