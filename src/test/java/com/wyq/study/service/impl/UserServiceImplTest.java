package com.wyq.study.service.impl;

import com.wyq.study.dao.UserMapper;
import com.wyq.study.pojo.User;
import com.wyq.study.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wangyiqiang on 2017/2/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}) //加载配置
@TransactionConfiguration(defaultRollback = false)
public class UserServiceImplTest {
    @Resource
    private IUserService userService;

    @Resource
    private UserMapper userMapper;

    @Test
    public void insert() throws Exception {
        User user = new User();
        user.setBestRunId(11);
        user.setBirthday(new Date());
        user.setCommunityId(12);
        user.setHeight(180.0);
        user.setLocation("浙江省宁波市鄞州区");
        user.setPassword("123456");
        user.setProfile("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4225565668,46745471&fm=80&w=179&h=119&img.JPEG");
        user.setBestRunId(13);
        user.setRank(2);
        user.setSex("女");
        user.setTotalRunId(3);
        user.setUsername("xiaohong");
        user.setWeight(98.5);
        userService.insert(user);
    }


    @Test
    public void listUsersByUserNameLikeTest() throws Exception {
//        User user = userService.selectByUserName("xiaohong");
        User userDO = new User();
        userDO.setUsername("xiao");
        List<User> userList = userMapper.listUsersByUserNameLike(userDO);
    }
}