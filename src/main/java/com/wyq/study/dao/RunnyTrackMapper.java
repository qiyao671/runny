package com.wyq.study.dao;

import com.wyq.study.pojo.RunnyTrack;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunnyTrackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RunnyTrack record);

    int insertSelective(RunnyTrack record);

    RunnyTrack selectByPrimaryKey(Integer id);

    List<RunnyTrack> selectByLogId(Integer logId);

    int updateByPrimaryKeySelective(RunnyTrack record);

    int updateByPrimaryKey(RunnyTrack record);
}