package com.wyq.study.service;

import com.wyq.study.pojo.Comment;

import java.util.List;

/**
 * ${DESCRIPTION}
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-12 下午1:40
 * 系统版本：1.0.0
 **/
public interface ICommentService {
    int saveComment(Comment comment);

    Comment getCommentById(Integer commentId);

    void deleteById(Integer commentId);

    void deleteComment(Integer userId, Integer momentId);

    List<Comment> listCommentsByMomentId(Integer id);

    void replyComment(Comment comment);

    void deleteReplyComment(Integer commentId);
}
