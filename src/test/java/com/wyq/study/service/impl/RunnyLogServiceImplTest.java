package com.wyq.study.service.impl;

import com.wyq.study.service.IRunnyLogService;
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
 * 创建时间： 2017-03-08 下午9:00
 * 系统版本：1.0.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class RunnyLogServiceImplTest {
    @Resource
    private IRunnyLogService runnyLogService;

    @Test
    public void getTotalLogInfo() throws Exception {

    }

    @Test
    public void getBestLogInfo() throws Exception {

    }

    @Test
    public void getPersonalLogInfo() throws Exception {

    }

    @Test
    public void getFarthestLogInfo() throws Exception {

    }

    @Test
    public void getLongestLogInfo() throws Exception {

    }

    @Test
    public void getFastLogInfo() throws Exception {

    }

    @Test
    public void listTotalRank() throws Exception {

    }

    @Test
    public void listMonthRank() throws Exception {
        int num = 1;
        int pageSize = 10;
//        PageInfo pageInfo = runnyLogService.listTimeRank(num, pageSize);
    }

}