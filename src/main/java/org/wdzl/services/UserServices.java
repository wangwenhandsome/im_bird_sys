package org.wdzl.services;

import org.wdzl.netty.ChatMsg;
import org.wdzl.pojo.FriendsRequest;
import org.wdzl.pojo.User;
import org.wdzl.vo.FriendsRequestVO;
import org.wdzl.vo.MyFriendsVO;

import java.util.List;

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
     //好友请求列表查询
     List<FriendsRequestVO> queryFriendRequestList(String acceptUserId);
     //处理好友请求--忽略好友请求
     void deleteFriendRequest(FriendsRequest friendsRequest);
     //处理好友请求--通过好友请求
     void passFriendRequest(String sendUserId,String acceptUserId);
     //好友列表查询
     List<MyFriendsVO> queryMyFriends(String userId);
     //保存用户聊天消息
     String saveMsg(ChatMsg chatMsg);

     void updateMsgSigned(List<String> msgIdList);

     //获取未接受的消息列表
     List<org.wdzl.pojo.ChatMsg> getUnReadMsgList(String acceptUserId);
}
