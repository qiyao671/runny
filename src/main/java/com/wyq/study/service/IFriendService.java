package com.wyq.study.service;

import com.wyq.study.pojo.Friend;
import com.wyq.study.pojo.User;

import java.util.List;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-11 下午7:48
 * 系统版本：1.0.0
 **/
public interface IFriendService {
    int saveFriend(Friend friend);

    void deleteFriend(Integer userId, Integer friendUserId);

    Friend getFriendByUserIdAndFriendId(Integer userId, Integer friendUserId);

    int updateFriendByPrimaryKey(Friend friend);

    Integer getFriendRelationStatus(Integer user1Id, Integer user2Id);

    List<User> listFriendsAndRequest(Integer userId);

    List<User> listFriends(Integer userId);

    List<User> getRequest(Integer userId);
}
