package com.wyq.study.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wyq.study.pojo.Callback;
import com.wyq.study.pojo.RunnyLog;
import com.wyq.study.service.IRunnyLogService;
import com.wyq.study.utils.AppSessionHelper;
import com.wyq.study.utils.DateUtils;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 跑步记录
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-05 下午4:21
 * 系统版本：1.0.0
 **/
@Controller
@RequestMapping("/log")
public class RunnyLogController extends BaseController {
    private static final double FIVE_KM = 5.0d;
    private static final double TEN_KM = 10.0d;
    private static final double HALF_MA = 21.09d;
    private static final double FULL_MA = 42.09d;

    @Autowired
    private IRunnyLogService runnyLogService;

    /**
     * 用户 累计总跑步记录 月累计跑步记录 周累计跑步记录
     *
     * @param flag  1 , 2 , 3
     * @param token
     * @return
     */
    @RequestMapping(value = "/getTotalLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getTotalLogInfo(String token, Integer flag) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        RunnyLog runnyLogVO = null;
        if (flag == 1) {
            //累计总跑步记录
            runnyLogVO = runnyLogService.getTotalLogInfo(userId);
        } else if (flag == 2) {
            //月累计跑步记录
            RunnyLog runnyLogQry = new RunnyLog();
            runnyLogQry.setUserId(userId);
            runnyLogQry.setBeginTime(DateUtils.monthStartTime(new Date()));
            runnyLogQry.setEndTime(DateUtils.monthEndTime(new Date()));
            runnyLogVO = runnyLogService.getTimeTotalLogInfo(runnyLogQry);
        } else if (flag == 3) {
            //周累计跑步记录
            RunnyLog runnyLogQry = new RunnyLog();
            runnyLogQry.setUserId(userId);
            runnyLogQry.setBeginTime(DateUtils.weekStartTime(new Date()));
            runnyLogQry.setEndTime(DateUtils.weekEndTime(new Date()));
            runnyLogVO = runnyLogService.getTimeTotalLogInfo(runnyLogQry);
        }
        if (runnyLogVO == null) {
            return returnCallback(false, null, "查不到您的跑步记录!");
        }
        JSONObject totalLogInfo = new JSONObject();
        totalLogInfo.put("totalDistance", runnyLogVO.getTotalDistance());
        totalLogInfo.put("totalSpendTime", runnyLogVO.getTotalSpendTime());
        totalLogInfo.put("totalTimes", runnyLogVO.getTotalCount());
        totalLogInfo.put("totalEnergy", runnyLogVO.getTotalEnergy());

        return returnCallback(true, totalLogInfo, null);
    }

    /**
     * 最佳个人跑步记录
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/getBestRunningLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getBestRunningLogInfo(String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        JSONObject bestLogInfo = new JSONObject();
        RunnyLog runnyLog = new RunnyLog();
        runnyLog.setUserId(userId);
        //五公里最快
        runnyLog.setMinDistance(FIVE_KM);
        runnyLog.setMaxDistance(TEN_KM);
        RunnyLog fiveRunLogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("fivePB", fiveRunLogVO);
        //十公里最快
        runnyLog.setMinDistance(TEN_KM);
        runnyLog.setMaxDistance(HALF_MA);
        RunnyLog tenRunLogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("tenPB", tenRunLogVO);
        //半马最快
        runnyLog.setMinDistance(HALF_MA);
        runnyLog.setMaxDistance(FULL_MA);
        RunnyLog halfMaRunLogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("halfMaPB", halfMaRunLogVO);
        //全马最快
        runnyLog.setMinDistance(FULL_MA);
        RunnyLog fullMaRunLogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("fullMaPB", fullMaRunLogVO);
        //最远距离
        RunnyLog farthestRunLogVO = runnyLogService.getFarthestLogInfo(userId);
        bestLogInfo.put("farthestLogInfo", farthestRunLogVO);
        //最长时间
        RunnyLog longestRunLogVO = runnyLogService.getLongestLogInfo(userId);
        bestLogInfo.put("longestRunLogVO", longestRunLogVO);
        //最快速度
        RunnyLog fastRunLogVO = runnyLogService.getFastLogInfo(userId);
        bestLogInfo.put("fastSpeed", fastRunLogVO);
        //最快配速 --这个怎么计算勒
        RunnyLog fastAveLogVO = runnyLogService.getFastPaceLog(userId);
        bestLogInfo.put("fastAveLogVO", fastAveLogVO);

        return returnCallback(true, bestLogInfo, null);
    }

    /**
     * 获得总排行
     *
     * @param token
     * @param num      1
     * @param pageSize 10
     * @return
     */
    @RequestMapping(value = "/listTotalRank", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listTotalRank(String token, Integer num, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (num == null || pageSize == null) {
            return returnCallback(false, null, "您的分页参数有误");
        }
        PageInfo pageInfo = runnyLogService.listTotalRank(num, pageSize);
        return returnCallback(true, pageInfo, null);
    }

    /**
     * 本月好友榜单
     *
     * @param token
     * @param num
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listMonthRank", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listMonthRank(String token, Integer num, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (num == null || pageSize == null) {
            return returnCallback(false, null, "您的分页参数有误");
        }
        Date beginMonth = DateUtils.monthStartTime(new Date());
        Date endMonth = DateUtils.monthEndTime(new Date());
        PageInfo pageInfo = runnyLogService.listTimeRank(userId, num, pageSize, beginMonth, endMonth);
        return returnCallback(true, pageInfo, null);
    }

    /**
     * 本周好友榜单
     *
     * @param token
     * @param num
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listWeekRank", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listWeekRank(String token, Integer num, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (num == null || pageSize == null) {
            return returnCallback(false, null, "您的分页参数有误");
        }
        Date beginWeek = DateUtils.weekStartTime(new Date());
        Date endWeek = DateUtils.weekEndTime(new Date());
        PageInfo pageInfo = runnyLogService.listTimeRank(userId, num, pageSize, beginWeek, endWeek);

        return returnCallback(true, pageInfo, null);
    }

    /**
     * 今日好友榜单
     *
     * @param token
     * @param num
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listDayRank", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listDayRank(String token, Integer num, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (num == null || pageSize == null) {
            return returnCallback(false, null, "您的分页参数有误");
        }
        Date beginDay = DateUtil.beginOfDay(new Date());
        Date endDay = DateUtil.endOfDay(new Date());
        PageInfo pageInfo = runnyLogService.listTimeRank(userId, num, pageSize, beginDay, endDay);

        return returnCallback(true, pageInfo, null);
    }

    /**
     * 添加跑步记录
     *
     * @param token
     * @param runnyLog
     * @return
     */
    @RequestMapping(value = "/saveRunnyLog", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback saveRunnyLog(String token, RunnyLog runnyLog) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (runnyLog == null) {
            return returnCallback(false, null, "您还未跑步!");
        }
        runnyLogService.saveRunnyLog(runnyLog);

        return returnCallback(true, "记录成功！", null);
    }

    /**
     * 我的所有跑步记录
     *
     * @param token
     * @param num
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listMyAllRunnyLogs", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listMyAllRunnyLogs(String token, Integer num, Integer pageSize) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (num == null || pageSize == null) {
            return returnCallback(false, null, "您的分页参数有误");
        }
        PageInfo pageInfo = runnyLogService.listAllUserRunnyLogsByUserId(userId, num, pageSize);

        return returnCallback(true, pageInfo, null);
    }

}
