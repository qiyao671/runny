package com.wyq.study.service.impl;

import com.wyq.study.dao.ApproveMapper;
import com.wyq.study.pojo.Approve;
import com.wyq.study.service.IApproveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 点赞逻辑层
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-12 下午1:40
 * 系统版本：1.0.0
 **/
@Service
public class ApproveServiceImpl implements IApproveService {
    @Resource
    private ApproveMapper approveMapper;

    @Override
    public int saveApprove(Approve approve) {
        approveMapper.insert(approve);
        return approve.getId();
    }

    @Override
    public void deleteApprove(Integer momentId) {
        approveMapper.deleteByPrimaryKey(momentId);
    }
}
