package com.wyq.study.service.impl;

import com.wyq.study.pojo.RunnyTrack;
import com.wyq.study.service.IRunnyTrackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by wangyiqiang on 16/5/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class RunnyTrackServiceImplTest {
    @Resource
    private IRunnyTrackService runnyTrackService;

    @Test
    public void saveTrack() throws Exception {
        RunnyTrack runnyTrack = new RunnyTrack();
        runnyTrack.setSerial(0);
        runnyTrack.setGmtCreate(new Date());
        runnyTrack.setLogId(1);
        runnyTrack.setTrack("123123123");
        int id = runnyTrackService.saveRunnyTrack(runnyTrack);
    }
}