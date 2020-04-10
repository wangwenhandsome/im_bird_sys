package org.wdzl.srevices.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wdzl.mapper.UserMapper;
import org.wdzl.pojo.User;
import org.wdzl.srevices.UserServices;

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
    Sid sid;
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
         user.setId(sid.nextShort());
         userMapper.insert(user);
         return user;
    }
}
