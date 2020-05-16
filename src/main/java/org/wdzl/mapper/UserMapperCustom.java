package org.wdzl.mapper;

import org.wdzl.vo.FriendsRequestVO;

import java.util.List;

/**
 * @Author: 王文
 * @Date: 2020/5/15 21:46
 * @Version: 1.0
 * @Description:
 */
public interface UserMapperCustom {
    List<FriendsRequestVO> queryFriendRequestList(String acceptUserId);
}
