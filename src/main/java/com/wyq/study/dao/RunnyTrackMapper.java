package com.wyq.study.dao;

import com.wyq.study.pojo.RunnyTrack;

public interface RunnyTrackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RunnyTrack record);

    int insertSelective(RunnyTrack record);

    RunnyTrack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RunnyTrack record);

    int updateByPrimaryKey(RunnyTrack record);
}