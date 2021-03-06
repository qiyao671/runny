package com.wyq.study.dao;

import com.wyq.study.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.jboss.logging.Field;

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

    List<User> listAllFriendIds(Integer userId);

    int updateProfileByPrimaryKey(@Param("userId") Integer userId, @Param("profile") String profile);
}