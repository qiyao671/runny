package com.wyq.study.service;

import com.wyq.study.pojo.User;

/**
 * Created by wangyiqiang on 16/6/1.
 */
public interface IUserService {
    int insert(User user);

    User selectByUserName(String userName);
}
