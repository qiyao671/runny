package com.wyq.study.service.impl;

import com.wyq.study.dao.UserMapper;
import com.wyq.study.pojo.User;
import com.wyq.study.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangyiqiang on 16/6/1.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public User selectByUserName(String userName) {
        User user = userMapper.selectByUserName(userName);
        return user;
    }

}
