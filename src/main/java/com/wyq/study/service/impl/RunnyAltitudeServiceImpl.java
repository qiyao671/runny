package com.wyq.study.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wyq.study.dao.RunnyAltitudeMapper;
import com.wyq.study.pojo.RunnyAltitude;
import com.wyq.study.service.IRunnyAltitudeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiyao671 on 2017/4/22.
 */
@Service
public class RunnyAltitudeServiceImpl implements IRunnyAltitudeService {
    @Resource
    private RunnyAltitudeMapper mapper;

    @Override
    public int saveRunnyAltitude(RunnyAltitude runnyAltitude) {
        return mapper.insert(runnyAltitude);
    }

    @Override
    public List<JSONArray> getRunnyAltitudeList(int logId) {
        List<RunnyAltitude> runnyAltitudes = mapper.selectByLogId(logId);
        List<JSONArray> runnyAltitudeList = new ArrayList<JSONArray>();
        for (RunnyAltitude runnyAltitude : runnyAltitudes) {
            runnyAltitudeList.add(JSONArray.parseArray(runnyAltitude.getAltitudes()));
        }
        return runnyAltitudeList;
    }
}
