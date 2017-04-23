package com.wyq.study.service;

import com.github.pagehelper.PageInfo;
import com.wyq.study.pojo.RunnyLog;

import java.util.Date;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-05 下午4:27
 * 系统版本：1.0.0
 **/
public interface IRunnyLogService {
    RunnyLog getTotalLogInfo(Integer userId);

    RunnyLog getBestLogInfo(Integer userId);

    RunnyLog getRunnyLog(Integer logId);

    RunnyLog getPersonalLogInfo(RunnyLog runnyLog);

    RunnyLog getFarthestLogInfo(Integer userId);

    RunnyLog getLongestLogInfo(Integer userId);

    RunnyLog getFastLogInfo(Integer userId);

    RunnyLog getFastPaceLog(Integer userId);

    PageInfo listTotalRank(Integer num, Integer pageSize);

    PageInfo listTimeRank(Integer userId, Integer num, Integer pageSize, Date startTime, Date endTime);

    int saveRunnyLog(RunnyLog runnyLog);

    RunnyLog getTimeTotalLogInfo(RunnyLog runnyLog);


    PageInfo listAllUserRunnyLogsByUserId(Integer userId, Integer num, Integer pageSize);
}
