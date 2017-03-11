package com.wyq.study.dao;

import com.wyq.study.pojo.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String username);

    List<User> listFriendsByUserId(Integer userId);


    List<User> listUsersByUserNameLike(User user);

}