package com.wyq.study.service.impl;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by wangyiqiang on 16/5/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class ArticleServiceImplTest {
//    @Resource
//    private IArticleService articleService;
//
//    @Test
//    public void testSelectByTitle() throws Exception {
//        Article article = articleService.selectByTitle("yewentao");
//    }
}