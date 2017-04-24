package com.wyq.study.service;

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
    void updateUserProfile(Integer userId, String profile);

    List<User> listFriendsIgnoreStatus(Integer userId);

    List<User> listUsersByUserNameLike(User user);

    List<Integer> listAllFriendIds(Integer userId);

}
