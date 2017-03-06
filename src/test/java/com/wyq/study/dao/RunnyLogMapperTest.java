package com.wyq.study.dao;

import com.wyq.study.pojo.RunnyLog;
import com.wyq.study.utils.AppSessionHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-05 下午4:40
 * 系统版本：1.0.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class RunnyLogMapperTest {
    @Resource
    private RunnyLogMapper runnyLogMapper;

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void selectTotalLogInfo() throws Exception {
        System.out.println(AppSessionHelper.getUserSession(1));

        Integer userId = 1;
        RunnyLog runnyLog = runnyLogMapper.selectTotalLogInfo(userId);

    }

    @Test
    public void selectPersonalLogInfo() throws Exception {

        Integer userId = 1;
        Double maxDistance = 10.0d;
        Double minDistance = 5.0d;
        RunnyLog runnyLog = new RunnyLog();
        runnyLog.setMaxDistance(10.0d);
        runnyLog.setMinDistance(5.0d);
        RunnyLog runnylogVO = runnyLogMapper.selectPersonalLogInfo(runnyLog);

    }



}