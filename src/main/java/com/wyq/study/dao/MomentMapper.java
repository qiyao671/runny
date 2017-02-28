package com.wyq.study.dao;

import com.wyq.study.pojo.Moment;

public interface MomentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Moment record);

    int insertSelective(Moment record);

    Moment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Moment record);

    int updateByPrimaryKey(Moment record);
}