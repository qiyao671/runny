package com.wyq.study.dao;

import com.wyq.study.pojo.Moment;

import java.util.List;

public interface MomentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Moment record);

    int insertSelective(Moment record);

    Moment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Moment record);

    int updateByPrimaryKeyWithBLOBs(Moment record);

    int updateByPrimaryKey(Moment record);

    Moment getNewestMoment(Integer userId);

    List<Moment> listNewestMoments(Moment moment);

    List<Moment> listPageMoments(Moment momentQry);

    void deleteMoment(Moment momentDO);

}