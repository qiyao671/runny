package com.wyq.study.service.impl;

import com.wyq.study.dao.TotalRunMapper;
import com.wyq.study.pojo.TotalRun;
import com.wyq.study.service.ITotalRunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-05 下午2:35
 * 系统版本：1.0.0
 **/
@Service
public class TotalRunServiceImpl implements ITotalRunService {
    @Resource
    private TotalRunMapper totalRunMapper;

    @Override
    public void insert(TotalRun totalRun) {
        if (totalRun != null) {
            totalRunMapper.insert(totalRun);
        }
    }
}
