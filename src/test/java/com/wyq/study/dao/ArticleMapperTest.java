package com.wyq.study.dao;

import org.junit.Test;
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
public class ArticleMapperTest {
//    @Resource
//    private ArticleMapper articleMapper;
//
//    @Test
//    public void testDeleteByPrimaryKey() throws Exception {
//
//    }
//
//    @Test
//    public void testInsert() throws Exception {
//        Article article = new Article();
//        article.setContent("相信哎");
//        article.setLid(1);
//        article.setTitle("yewentao");
//        articleMapper.insert(article);
//    }
//
//    @Test
//    public void testInsertSelective() throws Exception {
//
//    }
//
//    @Test
//    public void testSelectByPrimaryKey() throws Exception {
//        Article article = articleMapper.selectByPrimaryKey(1);
//    }

    @Test
    public void testUpdateByPrimaryKeySelective() throws Exception {

    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {

    }
}