package com.wyq.study.dao;

import com.wyq.study.pojo.Approve;

import java.util.List;

public interface ApproveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Approve record);

    int insertSelective(Approve record);

    Approve selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Approve record);

    int updateByPrimaryKey(Approve record);

    List<Approve> listApproveUser(Integer momentId);

    void deleteApprove(Approve approveDO);
}