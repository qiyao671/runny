package com.wyq.study.dao;

import com.wyq.study.pojo.Friend;
import com.wyq.study.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);

    void deleteByFriend(Friend friend);

    Friend getFriendByUserIdAndFriendId(@Param("userId") Integer userId, @Param("friendUserId") Integer friendUserId);

    Integer getFriendStatus(@Param("user1Id") Integer user1Id, @Param("user2Id") Integer user2Id);

    List<User> listFriendsByUserIdAndStatusStatement(@Param("userId") Integer userId, @Param("statusList") List<Integer> statusList);

    List<User> listFriendsByUserIdAndStatusStatementWithIdSequence(@Param("userId") Integer userId, @Param("statusList") List<Integer> statusList);

    List<User> listFriendsByUserId(Integer userId);
}