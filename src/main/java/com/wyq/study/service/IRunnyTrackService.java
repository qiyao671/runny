package com.wyq.study.service;

import com.alibaba.fastjson.JSONArray;
import com.wyq.study.pojo.RunnyTrack;

import java.util.List;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-04-08 下午1:28
 * 系统版本：1.0.0
 **/
public interface IRunnyTrackService {
    int saveRunnyTrack(RunnyTrack track);

    List<JSONArray> getRunnyTracks(int logId);
}
