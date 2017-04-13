package com.wyq.study.dao;

import com.wyq.study.pojo.Moment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MomentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Moment record);

    int insertSelective(Moment record);

    Moment selectByPrimaryKey(Integer id);

    Moment selectMomentByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Moment record);

    int updateByPrimaryKeyWithBLOBs(Moment record);

    int updateByPrimaryKey(Moment record);

    Moment getFriendsNewestMoment(Integer userId);

    List<Moment> listNewestMoments(Moment moment);

    void deleteMoment(Moment momentDO);

    List<Moment> listMoreMoments(Moment momentQry);

    List<Moment> listUserMomentByUserId(Integer userId);

    List<Moment> listUserMoments(Moment moment);

    List<Moment> listFriendsMoment(@Param("userId") Integer userId, @Param("maxId") Integer maxId, @Param("minId") Integer minId, @Param("pageSize") Integer pageSize);
}