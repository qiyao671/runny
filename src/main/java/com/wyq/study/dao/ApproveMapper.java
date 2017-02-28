package com.wyq.study.dao;

import com.wyq.study.pojo.Approve;

public interface ApproveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Approve record);

    int insertSelective(Approve record);

    Approve selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Approve record);

    int updateByPrimaryKey(Approve record);
}