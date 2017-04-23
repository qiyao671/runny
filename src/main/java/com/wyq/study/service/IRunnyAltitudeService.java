package com.wyq.study.service;

import com.alibaba.fastjson.JSONArray;
import com.wyq.study.dao.RunnyAltitudeMapper;
import com.wyq.study.pojo.RunnyAltitude;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by qiyao671 on 2017/4/22.
 */
public interface IRunnyAltitudeService {
    int saveRunnyAltitude(RunnyAltitude runnyAltitude);

    List<JSONArray> getRunnyAltitudeList(int logId);
}
