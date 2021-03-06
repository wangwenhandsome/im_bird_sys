package org.wdzl.mapper;

import org.wdzl.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    User queryUserNameIsExit(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}