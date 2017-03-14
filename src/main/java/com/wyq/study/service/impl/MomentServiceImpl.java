package com.wyq.study.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyq.study.dao.MomentMapper;
import com.wyq.study.pojo.Moment;
import com.wyq.study.service.IMomentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        return momentMapper.selectByPrimaryKey(momentId);
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
    public Moment getNewestMoment(Integer userId) {
        return momentMapper.getNewestMoment(userId);
    }

    @Override
    public List<Moment> listNewestMoments(Moment moment) {
        return momentMapper.listNewestMoments(moment);
    }

    @Override
    public PageInfo listPageMoments(Moment momentQry) {
        PageHelper.startPage(momentQry.getNum(), momentQry.getPageSize());
        List<Moment> momentList = momentMapper.listPageMoments(momentQry);
        PageInfo pageInfo = new PageInfo(momentList);
        return pageInfo;
    }
}