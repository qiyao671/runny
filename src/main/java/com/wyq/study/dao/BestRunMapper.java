package com.wyq.study.dao;

import com.wyq.study.pojo.BestRun;

public interface BestRunMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BestRun record);

    int insertSelective(BestRun record);

    BestRun selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BestRun record);

    int updateByPrimaryKey(BestRun record);
}