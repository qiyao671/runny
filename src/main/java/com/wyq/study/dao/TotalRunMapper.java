package com.wyq.study.dao;

import com.wyq.study.pojo.TotalRun;

public interface TotalRunMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TotalRun record);

    int insertSelective(TotalRun record);

    TotalRun selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TotalRun record);

    int updateByPrimaryKey(TotalRun record);
}