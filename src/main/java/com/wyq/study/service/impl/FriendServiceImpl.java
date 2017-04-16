package com.wyq.study.service.impl;

import com.wyq.study.constant.FriendConsts;
import com.wyq.study.dao.FriendMapper;
import com.wyq.study.pojo.Friend;
import com.wyq.study.pojo.User;
import com.wyq.study.service.IFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.plaf.metal.MetalFileChooserUI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 好友逻辑层
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-11 下午7:48
 * 系统版本：1.0.0
 **/
@Service
public class FriendServiceImpl implements IFriendService {
    @Resource
    private FriendMapper friendMapper;

    @Override
    public int saveFriend(Friend friend) {
        if (friendMapper.getFriendByUserIdAndFriendId(friend.getUserId(), friend.getFriendId()) == null) {
            friend.setCreateTime(new Date());
            return friendMapper.insert(friend);
        }
        else return -1;
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendUserId) {
        Friend friendDO = new Friend();
        friendDO.setUserId(userId);
        friendDO.setFriendId(friendUserId);
        friendMapper.deleteByFriend(friendDO);
    }

    @Override
    public Friend getFriendByUserIdAndFriendId(Integer userId, Integer friendUserId) {
        return friendMapper.getFriendByUserIdAndFriendId(userId, friendUserId);
    }

    @Override
    public int updateFriendByPrimaryKey(Friend friend) {
        return friendMapper.updateByPrimaryKey(friend);
    }

    @Override
    public Integer getFriendRelationStatus(Integer user1Id, Integer user2Id) {
        Integer status = friendMapper.getFriendStatus(user1Id, user2Id);
        return status == null ? FriendConsts.NOT_FRIEND : status;
    }

    /**
     * 根据用户id 查出所有好友与好友请求
     *
     * @param userId
     * @return
     */
    @Override
    public List<User> listFriendsAndRequest(Integer userId) {
        return friendMapper.listFriendsByUserIdAndStatusStatementWithIdSequence(userId, Arrays.asList(FriendConsts.HAS_BEEN_FRIENDS, FriendConsts.ADD_FRIENDS));
    }

    /**
     * 根据用户id 查出所有好友
     *
     * @param userId
     * @return
     */
    @Override
    public List<User> listFriends(Integer userId) {
        return friendMapper.listFriendsByUserIdAndStatusStatement(userId, Collections.singletonList(FriendConsts.HAS_BEEN_FRIENDS));
    }

    @Override
    public List<User> getRequest(Integer userId) {
        return friendMapper.listFriendsByUserIdAndStatusStatementWithIdSequence(userId, Collections.singletonList(FriendConsts.ADD_FRIENDS));
    }
}
