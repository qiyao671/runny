package com.wyq.study.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyq.study.pojo.Callback;
import com.wyq.study.pojo.RunnyLog;
import com.wyq.study.service.IRunnyLogService;
import com.wyq.study.utils.AppSessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private static final Double FIVE_KM = 5.0d;
    private static final Double TEN_KM = 10.0d;
    private static final Double HALF_MA = 21.09d;
    private static final Double FULL_MA = 42.09d;

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
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        RunnyLog runnyLogVO = runnyLogService.getTotalLogInfo(userId);
        if (runnyLogVO == null) {
            return returnCallback("Error", "查不到您的跑步记录!");
        }
        JSONObject totalLogInfo = new JSONObject();
        totalLogInfo.put("totalDistance", runnyLogVO.getTotalDistance());
        totalLogInfo.put("totalSpendTime", runnyLogVO.getTotalSpendTime());
        totalLogInfo.put("totalTimes", runnyLogVO.getTotalCount());
        totalLogInfo.put("totalEnergy", runnyLogVO.getTotalEnergy());

        return returnCallback("Success", totalLogInfo);
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
            return returnCallback("Error", "您还未登录，请您先登录!");
        }
        JSONObject bestLogInfo = new JSONObject();
        RunnyLog runnyLog = new RunnyLog();
        runnyLog.setUserId(userId);
        //五公里最快
        runnyLog.setMinDistance(FIVE_KM);
        runnyLog.setMaxDistance(TEN_KM);
        RunnyLog fiveRunlogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("fivePB", fiveRunlogVO);
        //十公里最快
        runnyLog.setMinDistance(TEN_KM);
        runnyLog.setMaxDistance(HALF_MA);
        RunnyLog tenRunlogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("tenPB", tenRunlogVO);
        //半马最快
        runnyLog.setMinDistance(HALF_MA);
        runnyLog.setMaxDistance(FULL_MA);
        RunnyLog halfMaRunlogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("halfMaPB", halfMaRunlogVO);
        //全马最快
        runnyLog.setMinDistance(FULL_MA);
        RunnyLog fullMaRunlogVO = runnyLogService.getPersonalLogInfo(runnyLog);
        bestLogInfo.put("fullMaPB", fullMaRunlogVO);
        //最远距离
        RunnyLog farthestRunLogVO = runnyLogService.getFarthestLogInfo(userId);
        bestLogInfo.put("farthestLogInfo", farthestRunLogVO);
        //最长时间
        RunnyLog longestRunLogVO = runnyLogService.getLongestLogInfo(userId);
        bestLogInfo.put("longestRunLogVO", longestRunLogVO);
        //最快速度
        RunnyLog fastRunLogVO = runnyLogService.getFastLogInfo(userId);
        bestLogInfo.put("fastSpeed", fastRunLogVO);

        return returnCallback("Success", bestLogInfo);
    }

}
