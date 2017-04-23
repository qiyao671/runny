package com.wyq.study.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wyq.study.dao.RunnyTrackMapper;
import com.wyq.study.pojo.RunnyAltitude;
import com.wyq.study.pojo.RunnyTrack;
import com.wyq.study.service.IRunnyTrackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-04-08 下午1:28
 * 系统版本：1.0.0
 **/
@Service
public class RunnyTrackServiceImpl implements IRunnyTrackService {
    @Resource
    private RunnyTrackMapper mapper;
    @Override
    public int saveRunnyTrack(RunnyTrack track) {
        return mapper.insert(track);
    }

    @Override
    public List<JSONArray> getRunnyTracks(int logId) {
        List<RunnyTrack> runnyTracks = mapper.selectByLogId(logId);
        List<JSONArray> runnyTrackList = new ArrayList<JSONArray>();
        for (RunnyTrack runnyTrack : runnyTracks) {
            runnyTrackList.add(JSONArray.parseArray(runnyTrack.getTrack()));
        }
        return runnyTrackList;
    }
}
