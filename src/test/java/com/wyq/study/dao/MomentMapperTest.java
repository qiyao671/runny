package com.wyq.study.dao;

import com.wyq.study.pojo.Moment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by qiyao on 2017/4/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class MomentMapperTest {
    @Resource
    private MomentMapper momentMapper;
    @Test
    public void deleteByPrimaryKey() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void insertSelective() throws Exception {
    }

    @Test
    public void selectByPrimaryKey() throws Exception {
    }

    @Test
    public void selectMomentByPrimaryKey() throws Exception {
        Moment moment = momentMapper.selectMomentByPrimaryKey(2);
        assertNotNull(moment);
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {
    }

    @Test
    public void updateByPrimaryKeyWithBLOBs() throws Exception {
    }

    @Test
    public void updateByPrimaryKey() throws Exception {
    }

    @Test
    public void getFriendsNewestMoment() throws Exception {
    }

    @Test
    public void listNewestMoments() throws Exception {
    }

    @Test
    public void deleteMoment() throws Exception {
    }

    @Test
    public void listMoreMoments() throws Exception {
    }

    @Test
    public void listUserMomentByUserId() throws Exception {
    }

    @Test
    public void listUserMoments() throws Exception {
    }

    @Test
    public void listFriendsMoment() {
        List<Moment> moments = momentMapper.listFriendsMoment(1, null, 4, 2);
        assertNotNull(moments);
    }

}