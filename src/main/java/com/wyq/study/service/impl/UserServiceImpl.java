package com.wyq.study.service.impl;

import com.wyq.study.constant.FriendConsts;
import com.wyq.study.dao.UserMapper;
import com.wyq.study.pojo.User;
import com.wyq.study.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public User selectByUserName(String username) {
        User user = userMapper.selectByUserName(username);
        return user;
    }


    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 用户信息校验
     *
     * @param user
     * @return
     */
    @Override
    public String checkUser(User user) {
        if (StringUtils.isEmpty(user.getUsername())) {
            return "用户名不能为空！";
        }
//        else if (user.getSex().isEmpty()) {
//            return "请填写您的性别！";
//        } else if (user.getBirthday() == null || !Validator.isBirthday(user.getBirthday().toString())) {
//            return "您的出生日期无效，请重新输入";
//        } else if (user.getHeight() == null) {
//            return "请填写您的身高";
//        }else if (user.getWeight() == null) {
//            return "请填写您的体重";
//        }
        return null;
    }

    @Override
    public User getByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUserProfile(Integer userId, String profile) {
        userMapper.updateProfileByPrimaryKey(userId, profile);
    }

    /**
     * 根据用户id 查出所有friend表中存在关系的用户
     *
     * @param userId
     * @return
     */
    @Override
    public List<User> listFriendsIgnoreStatus(Integer userId) {
        return userMapper.listFriendsByUserId(userId);
    }

    @Override
    public List<User> listUsersByUserNameLike(User user) {
        return userMapper.listUsersByUserNameLike(user);
    }

    /**
     * 根据用户id 查出所有好友的id
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> listAllFriendIds(Integer userId) {
        List<Integer> fids = new ArrayList<Integer>();
        List<User> userList = userMapper.listAllFriendIds(userId);
        for (User user : userList) {
            fids.add(user.getId());
        }
        return fids;
    }

/*    *//**
     * 判断该用户是否是我的好友
     *//*
    @Override
    public Boolean isFriend(Integer userId, Integer friendId) {
        Boolean isFriend = false;
        List<User> userList = userMapper.listAllFriendIds(userId);
        for (User user : userList) {
            if (user.getId() == userId) {
                isFriend = true;
            }
        }
        return isFriend;
    }*/

}
