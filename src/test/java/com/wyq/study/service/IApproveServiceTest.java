package com.wyq.study.service;

import com.wyq.study.pojo.Approve;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-19 下午7:43
 * 系统版本：1.0.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class IApproveServiceTest {
    @Resource
    private IApproveService approveService;

    @Test
    public void saveApprove() throws Exception {

    }

    @Test
    public void deleteApprove() throws Exception {

    }

    @Test
    public void listApproveUser() throws Exception {

    }

    @Test
    public void listApprovesByMomentId() throws Exception {
        List<Approve> approveList = approveService.listApprovesByMomentId(5);
    }

}