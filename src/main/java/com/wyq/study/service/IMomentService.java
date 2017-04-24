package com.wyq.study.service;

import com.github.pagehelper.PageInfo;
import com.wyq.study.pojo.Moment;

import java.util.List;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-12 下午1:39
 * 系统版本：1.0.0
 **/
public interface IMomentService {

    int saveMoment(Moment moment);

    Moment getMomentById(Integer momentId);

    void deleteMoment(Integer userId, Integer id);

    void updateMoment(Moment moment);

    Moment getFriendsNewestMoment(Integer userId);

    List<Moment> listNewestMoments(Moment moment);

    List<Moment> listMoreMoments(Moment momentQry);

    List<Moment> listFriendsMoment(Integer userId, Integer maxId, Integer minId, Integer pageSize);

    PageInfo<Moment> listUserMomentByUserId(Integer userId, Integer num, Integer pageSize);

    List<Moment> listUserMoments(Moment momentQry);
}
