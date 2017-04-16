package com.wyq.study.dao;

import com.wyq.study.pojo.Friend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by qiyao671 on 2017/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class FriendMapperTest {
    @Resource
    private FriendMapper friendMapper;
    @Test
    public void updateByPrimaryKey() throws Exception {
        Friend friend = new Friend();
        friend.setId(10);
        friendMapper.updateByPrimaryKey(friend);
    }

}