package com.wyq.study.service.impl;

import com.wyq.study.dao.ApproveMapper;
import com.wyq.study.pojo.Approve;
import com.wyq.study.pojo.User;
import com.wyq.study.service.IApproveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public void deleteApprove(Integer userId, Integer momentId) {
        Approve approveDO = new Approve();
        approveDO.setUserId(userId);
        approveDO.setMomentId(momentId);
        approveMapper.deleteApprove(approveDO);
    }

    @Override
    public List<User> listApproveUser(Integer momentId) {
        List<Approve> approveList = approveMapper.listApproveUser(momentId);
        List<User> userList = new ArrayList<User>();
        for (Approve approve : approveList) {
            userList.add(approve.getUser());
        }
        return userList;
    }

    @Override
    public List<Approve> listApprovesByMomentId(Integer id) {
        return approveMapper.listApprovesByMomentId(id);
    }

    @Override
    public Boolean isApprove(Approve approveQry) {
        Boolean isApprove = false;
        Approve approve = approveMapper.getApprove(approveQry);
        if (approve != null) {
            isApprove = true;
        }
        return isApprove;
    }
}
