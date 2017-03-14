package com.wyq.study.service;

import com.wyq.study.pojo.Approve;
import com.wyq.study.pojo.User;

import java.util.List;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-12 下午1:41
 * 系统版本：1.0.0
 **/
public interface IApproveService {

    int saveApprove(Approve approve);

    void deleteApprove(Integer userId, Integer momentId);

    List<User> listApproveUser(Integer momentId);

}
