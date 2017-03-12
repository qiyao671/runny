package com.wyq.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyq.study.dao.RunnyLogMapper;
import com.wyq.study.pojo.RunnyLog;
import com.wyq.study.service.IRunnyLogService;
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

    @Override
    public RunnyLog getTotalLogInfo(Integer userId) {
        RunnyLog runnyLog = runnyLogMapper.selectTotalLogInfo(userId);
        return runnyLog;
    }

    @Override
    public RunnyLog getBestLogInfo(Integer userId) {

        return null;
    }

    @Override
    public RunnyLog getPersonalLogInfo(RunnyLog runnyLog) {
        RunnyLog logInfo = runnyLogMapper.selectPersonalLogInfo(runnyLog);
        return logInfo;
    }

    @Override
    public RunnyLog getFarthestLogInfo(Integer userId) {
        RunnyLog logInfo = runnyLogMapper.selectFarthestLogInfo(userId);
        return logInfo;
    }

    @Override
    public RunnyLog getLongestLogInfo(Integer userId) {
        RunnyLog logInfo = runnyLogMapper.selectLongestLogInfo(userId);
        return logInfo;
    }

    @Override
    public RunnyLog getFastLogInfo(Integer userId) {
        RunnyLog logInfo = runnyLogMapper.selectFastLogInfo(userId);
        Double fastSpeed = 0.0;
        if (logInfo != null) {
            BigDecimal distance = new BigDecimal(Double.valueOf(logInfo.getDistance()));
            BigDecimal spendTime = new BigDecimal(Double.valueOf(logInfo.getSpendTime()));
            fastSpeed = distance.divide(spendTime, 2).doubleValue();
        }
        logInfo.setFastSpend(fastSpeed);
        return logInfo;
    }

    @Override
    public RunnyLog getFastPaceLog(Integer userId) {
        RunnyLog logInfo = runnyLogMapper.selectFastPaceLogInfo(userId);

        return logInfo;
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
    public PageInfo listTimeRank(Integer num, Integer pageSize, Date startTime, Date endTime) {
        PageHelper.startPage(num, pageSize);
        RunnyLog runnyLogDTO = new RunnyLog();
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
