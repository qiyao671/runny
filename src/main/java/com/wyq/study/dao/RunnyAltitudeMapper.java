package com.wyq.study.dao;

import com.wyq.study.pojo.RunnyAltitude;

public interface RunnyAltitudeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RunnyAltitude record);

    int insertSelective(RunnyAltitude record);

    RunnyAltitude selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RunnyAltitude record);

    int updateByPrimaryKey(RunnyAltitude record);
}