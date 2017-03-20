package com.wyq.study.dao;

import com.wyq.study.pojo.Friend;

public interface FriendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);

    void deleteByFriend(Friend friend);

    Friend getFriendByUserId(Integer friendUserId);
}