package com.wyq.study.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-19 下午7:42
 * 系统版本：1.0.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class CommentServiceImplTest {
    @Test
    public void saveComment() throws Exception {

    }

    @Test
    public void getCommentById() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {

    }

    @Test
    public void deleteComment() throws Exception {

    }

    @Test
    public void listCommentsByMomentId() throws Exception {

    }

}