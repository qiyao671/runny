package com.wyq.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyq.study.dao.RunnyLogMapper;
import com.wyq.study.pojo.RunnyLog;
import com.wyq.study.service.IRunnyAltitudeService;
import com.wyq.study.service.IRunnyLogService;
import com.wyq.study.service.IRunnyTrackService;
import com.wyq.study.utils.DateUtils;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-05 下午4:27
 * 系统版本：1.0.0
 **/
@Service
public class RunnyLogServiceImpl implements IRunnyLogService {
    @Resource
    private RunnyLogMapper runnyLogMapper;

    @Resource
    private IRunnyAltitudeService runnyAltitudeService;

    @Resource
    private IRunnyTrackService runnyTrackService;

    @Override
    public RunnyLog getTotalLogInfo(Integer userId) {
        return runnyLogMapper.selectTotalLogInfo(userId);
    }

    @Override
    public RunnyLog getBestLogInfo(Integer userId) {
        return null;
    }

    @Override
    public RunnyLog getRunnyLog(Integer logId) {
        RunnyLog runnyLog = runnyLogMapper.getRunnyLogByPrimaryKey(logId);
        runnyLog.setAltitudeLists(runnyAltitudeService.getRunnyAltitudeList(logId));
        runnyLog.setTracks(runnyTrackService.getRunnyTracks(logId));
        return runnyLog;
    }

    @Override
    public RunnyLog getPersonalLogInfo(RunnyLog runnyLog) {
        return runnyLogMapper.selectPersonalLogInfo(runnyLog);
    }

    @Override
    public RunnyLog getFarthestLogInfo(Integer userId) {
        return runnyLogMapper.selectFarthestLogInfo(userId);
    }

    @Override
    public RunnyLog getLongestLogInfo(Integer userId) {
        return runnyLogMapper.selectLongestLogInfo(userId);
    }

    @Override
    public RunnyLog getFastLogInfo(Integer userId) {
        RunnyLog logInfo = runnyLogMapper.selectFastLogInfo(userId);
        Double fastSpeed = 0.0;
        if (logInfo != null) {
            BigDecimal distance = null;
            BigDecimal spendTime = null;
            if (logInfo.getDistance() != null) {
                distance = new BigDecimal(logInfo.getDistance());
            }
            if (logInfo.getSpendTime() != null) {
                spendTime = new BigDecimal(Double.valueOf(logInfo.getSpendTime()));
            }
            if (distance != null && spendTime != null) {
                fastSpeed = distance.divide(spendTime, 2).doubleValue();
            }
            logInfo.setFastSpend(fastSpeed);
        }
        return logInfo;
    }

    @Override
    public RunnyLog getFastPaceLog(Integer userId) {
        return runnyLogMapper.selectFastPaceLogInfo(userId);
    }

    @Override
    public PageInfo listTotalRank(Integer num, Integer pageSize) {
        PageHelper.startPage(num, pageSize);
        RunnyLog runnyLog = new RunnyLog();
        List<RunnyLog> totalRankList = runnyLogMapper.listTotalRank(runnyLog);
        if (totalRankList == null || totalRankList.size() == 0) {
            return null;
        }
        PageInfo pageInfo = new PageInfo(totalRankList);
        return pageInfo;
    }

    @Override
    public PageInfo listTimeRank(Integer userId, Integer num, Integer pageSize, Date startTime, Date endTime) {
        PageHelper.startPage(num, pageSize);
        RunnyLog runnyLogDTO = new RunnyLog();
        runnyLogDTO.setUserId(userId);
        runnyLogDTO.setBeginTime(startTime);
        runnyLogDTO.setEndTime(endTime);
        List<RunnyLog> timeRankList = runnyLogMapper.listTimeRank(runnyLogDTO);
        if (timeRankList == null || timeRankList.size() == 0) {
            return null;
        }
        PageInfo pageInfo = new PageInfo(timeRankList);
        return pageInfo;
    }

    @Override
    public int saveRunnyLog(RunnyLog runnyLog) {
        runnyLogMapper.insert(runnyLog);
        return runnyLog.getId();
    }

    @Override
    public RunnyLog getTimeTotalLogInfo(RunnyLog runnyLog) {
        return runnyLogMapper.getTimeTotalLogInfo(runnyLog);
    }

    @Override
    public PageInfo listAllUserRunnyLogsByUserId(Integer userId, Integer num, Integer pageSize) {
        PageHelper.startPage(num, pageSize);
        List<RunnyLog> runnyLogs = runnyLogMapper.listMyAllRunnyLogs(userId);
        PageInfo pageInfo = new PageInfo(runnyLogs);
        return pageInfo;
    }

    public static void main(String args[]) {
        Date now = new Date();
        int age = 0;
        try {
            age = DateUtils.getAge(DateUtil.offsiteMonth(now, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(age);
    }
}
