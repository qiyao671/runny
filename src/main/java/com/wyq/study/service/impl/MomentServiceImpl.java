package com.wyq.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyq.study.dao.MomentMapper;
import com.wyq.study.pojo.Moment;
import com.wyq.study.pojo.RunnyLog;
import com.wyq.study.service.IMomentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 朋友圈动态
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-12 下午1:39
 * 系统版本：1.0.0
 **/
@Service
public class MomentServiceImpl implements IMomentService {
    @Resource
    private MomentMapper momentMapper;

    @Override
    public int saveMoment(Moment moment) {
        return momentMapper.insert(moment);
    }

    @Override
    public Moment getMomentById(Integer momentId) {
        return momentMapper.selectMomentByPrimaryKey(momentId);
    }

    @Override
    public void deleteMoment(Integer userId,Integer id) {
        Moment momentDO = new Moment();
        momentDO.setUserId(id);
        momentDO.setUserId(userId);
        momentMapper.deleteMoment(momentDO);
    }

    @Override
    public void updateMoment(Moment moment) {
        momentMapper.updateByPrimaryKeySelective(moment);
    }

    @Override
    public Moment getFriendsNewestMoment(Integer userId) {
        return momentMapper.getFriendsNewestMoment(userId);
    }

    @Override
    public List<Moment> listNewestMoments(Moment moment) {
        return momentMapper.listNewestMoments(moment);
    }

    @Override
    public List<Moment> listMoreMoments(Moment momentQry) {
        return momentMapper.listMoreMoments(momentQry);
    }


    @Override
    public List<Moment> listFriendsMoment(Integer userId, Integer maxId, Integer minId, Integer pageSize) {
        return momentMapper.listFriendsMoment(userId, maxId, minId, pageSize);
    }

    @Override
    public PageInfo<Moment> listUserMomentByUserId(Integer userId, Integer num, Integer pageSize) {
        PageHelper.startPage(num, pageSize);
        List<Moment> moments = momentMapper.listUserMomentByUserId(userId);
        if (moments == null || moments.size() == 0) {
            return new PageInfo<Moment>(new ArrayList<Moment>());
        }
        return new PageInfo<Moment>(moments);
    }

    @Override
    public List<Moment> listUserMoments(Moment momentQry) {
        return momentMapper.listUserMoments(momentQry);
    }
}
