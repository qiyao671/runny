package com.wyq.study.dao;

import com.wyq.study.pojo.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    void deleteComment(Comment commentDO);

    List<Comment> listCommentsByMomentId(Integer id);
}