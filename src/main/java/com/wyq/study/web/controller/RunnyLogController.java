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
     * 用户累计跑步记录
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/getTotalLogInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getTotalLogInfo(String token) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        RunnyLog runnyLogVO = runnyLogService.getTotalLogInfo(userId);
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
            return returnCallback(false, null, "您的分页参数为空");
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
            return returnCallback(false, null, "您的分页参数为空");
        }
        Date beginMonth = DateUtils.monthStartTime(new Date());
        Date endMonth = DateUtils.monthEndTime(new Date());
        PageInfo pageInfo = runnyLogService.listTimeRank(num, pageSize, beginMonth, endMonth);
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
            return returnCallback(false, null, "您的分页参数为空");
        }
        Date beginWeek = DateUtils.weekStartTime(new Date());
        Date endWeek = DateUtils.weekEndTime(new Date());
        PageInfo pageInfo = runnyLogService.listTimeRank(num, pageSize, beginWeek, endWeek);

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
            return returnCallback(false, null, "您的分页参数为空");
        }
        Date beginDay = DateUtil.beginOfDay(new Date());
        Date endDay = DateUtil.endOfDay(new Date());
        PageInfo pageInfo = runnyLogService.listTimeRank(num, pageSize, beginDay, endDay);

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

}
