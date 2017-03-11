package com.wyq.study.service.impl;

import com.wyq.study.dao.FriendMapper;
import com.wyq.study.pojo.Friend;
import com.wyq.study.service.IFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        return friendMapper.insert(friend);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendUserId) {
        Friend friendDO = new Friend();
        friendDO.setUserId(userId);
        friendDO.setFriendId(friendUserId);
        friendMapper.deleteByFriend(friendDO);
    }
}
