package com.wyq.study.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wyq.study.pojo.Callback;
import com.wyq.study.pojo.RunnyAltitude;
import com.wyq.study.pojo.RunnyLog;
import com.wyq.study.pojo.RunnyTrack;
import com.wyq.study.service.IRunnyAltitudeService;
import com.wyq.study.service.IRunnyLogService;
import com.wyq.study.service.IRunnyTrackService;
import com.wyq.study.utils.AppSessionHelper;
import com.wyq.study.utils.DateUtils;
import com.xiaoleilu.hutool.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private IRunnyTrackService runnyTrackService;

    @Autowired
    private IRunnyAltitudeService runnyAltitudeService;

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
     * 好友榜单
     *
     * @param token
     * @param num
     * @param pageSize
     * @param flag 2,3,4(月，周，日）
     * @return
     */
    @RequestMapping(value = "/listFriendsRank", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback listFriendsRank(String token, Integer num, Integer pageSize, Integer flag) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (num == null || pageSize == null) {
            return returnCallback(false, null, "您的分页参数有误");
        }
        if (flag == null) {
            return returnCallback(false, null, "参数错误");
        }
        Date begin;
        Date end;
        switch (flag) {
            case 2:
                begin = DateUtils.monthStartTime(new Date());
                end = DateUtils.monthEndTime(new Date());
                break;
            case 3:
                begin = DateUtils.weekStartTime(new Date());
                end = DateUtils.weekEndTime(new Date());
                break;
            case 4:
                begin = DateUtil.beginOfDay(new Date());
                end = DateUtil.endOfDay(new Date());
                break;
            default:
                return returnCallback(false, null, "参数错误");
        }

        PageInfo pageInfo = runnyLogService.listTimeRank(userId, num, pageSize, begin, end);
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
    public Callback saveRunnyLog(String token, @RequestBody RunnyLog runnyLog) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (runnyLog == null) {
            return returnCallback(false, null, "您还未跑步!");
        }

        saveLog(runnyLog, userId);

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

    @RequestMapping(value = "/getRunnyLog", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getRunnyLog(String token, Integer logId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (logId == null) {
            return returnCallback(false, null, "未指定要获取的跑步记录");
        }
        RunnyLog runnyLog = runnyLogService.getRunnyLog(logId);
        return returnCallback(true, runnyLog, null);
    }

    @RequestMapping(value = "/getRunnyAltitudeList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getRunnyAltitudeList(String token, Integer logId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (logId == null) {
            return returnCallback(false, null, "未指定要获取的跑步记录");
        }
        List<JSONArray> runnyAltitudeList = runnyAltitudeService.getRunnyAltitudeList(logId);
        return returnCallback(true, runnyAltitudeList, null);
    }

    @RequestMapping(value = "/getRunnyTrackList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback getRunnyTrackList(String token, Integer logId) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (logId == null) {
            return returnCallback(false, null, "未指定要获取的跑步记录");
        }
        List<JSONArray> runnyTracks = runnyTrackService.getRunnyTracks(logId);
        return returnCallback(true, runnyTracks, null);
    }

    @RequestMapping(value = "saveRunnyLogList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback saveRunnyLogList(String token, List<RunnyLog> runnyLogs) {
        Integer userId = AppSessionHelper.getAppUserId(token);
        if (userId == null) {
            return returnCallback(false, null, "您还未登录，请您先登录!");
        }
        if (runnyLogs == null) {
            return returnCallback(false, null, "参数错误");
        }
        runnyLogs.forEach(runnyLog -> saveLog(runnyLog, userId));
        return returnCallback(true, "之前未上传成功的跑步记录已成功上传！", null);

    }

    private void saveLog(RunnyLog runnyLog, Integer userId) {
        runnyLog.setCreateTime(new Date());
        runnyLog.setUserId(userId);
        int logId = runnyLogService.saveRunnyLog(runnyLog);

        List<JSONArray> altitudesList = runnyLog.getAltitudeLists();
        if (altitudesList != null) {
            for (int i = 0; i < altitudesList.size(); i++) {
                RunnyAltitude runnyAltitude = new RunnyAltitude();
                runnyAltitude.setLogId(logId);
                runnyAltitude.setAltitudes(altitudesList.get(i).toJSONString());
                runnyAltitude.setGmtCreate(new Date());
                runnyAltitude.setSerial(i);
                runnyAltitudeService.saveRunnyAltitude(runnyAltitude);
            }
        }

        List<JSONArray> tracks = runnyLog.getTracks();
        if (tracks != null) {
            for (int i = 0; i < tracks.size(); i++) {
                RunnyTrack runnyTrack = new RunnyTrack();
                runnyTrack.setLogId(logId);
                runnyTrack.setTrack(tracks.get(i).toJSONString());
                runnyTrack.setGmtCreate(new Date());
                runnyTrack.setSerial(i);
                runnyTrackService.saveRunnyTrack(runnyTrack);
            }
        }
    }
}
