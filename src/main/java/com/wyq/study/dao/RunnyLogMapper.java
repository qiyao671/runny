package com.wyq.study.dao;

import com.wyq.study.pojo.RunnyLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunnyLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RunnyLog record);

    int insertSelective(RunnyLog record);

    RunnyLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RunnyLog record);

    int updateByPrimaryKey(RunnyLog record);

    RunnyLog selectTotalLogInfo(Integer userId);

    RunnyLog selectPersonalLogInfo(RunnyLog runnyLog);

    RunnyLog selectFarthestLogInfo(Integer userId);

    RunnyLog selectLongestLogInfo(Integer userId);

    RunnyLog selectFastLogInfo(Integer userId);

    List<RunnyLog> listTotalRank(RunnyLog runnyLog);

    List<RunnyLog> listTimeRank(RunnyLog runnyLog);

    RunnyLog selectFastPaceLogInfo(Integer userId);

    RunnyLog getTimeTotalLogInfo(RunnyLog runnyLogDTO);

    List<RunnyLog> listMyAllRunnyLogs(Integer userId);

    RunnyLog getRunnyLogByPrimaryKey(Integer logId);
}