package com.wyq.study.service;

import com.github.pagehelper.PageInfo;
import com.wyq.study.pojo.User;

import java.util.List;

/**
 * Created by wangyiqiang on 16/6/1.
 */
public interface IUserService {
    int insert(User user);

    User selectByUserName(String userName);

    void updateUser(User user);

    String checkUser(User user);

    User getByUserId(Integer userId);


    PageInfo listFriends(Integer userId, Integer num, Integer pageSize);

    List<User> listUsersByUserNameLike(User user);

}
