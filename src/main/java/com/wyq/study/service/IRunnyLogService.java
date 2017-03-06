package com.wyq.study.service;

import com.wyq.study.pojo.RunnyLog;

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


    RunnyLog getPersonalLogInfo(RunnyLog runnyLog);

    RunnyLog getFarthestLogInfo(Integer userId);

    RunnyLog getLongestLogInfo(Integer userId);
}
