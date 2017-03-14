package com.wyq.study.service.impl;

import com.wyq.study.dao.CommentMapper;
import com.wyq.study.pojo.Comment;
import com.wyq.study.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 朋友圈评论逻辑层
 * 模块名称：study
 * 功能说明：<br>
 * 功能描述：<br>
 * 开发人员：wangyiqiang
 * 创建时间： 2017-03-12 下午1:40
 * 系统版本：1.0.0
 **/
@Service
public class CommentServiceImpl implements ICommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public int saveComment(Comment comment) {
        commentMapper.insert(comment);
        return comment.getId();
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public void deleteById(Integer commentId) {
        commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public void deleteComment(Integer userId, Integer momentId) {
        Comment commentDO = new Comment();
        commentDO.setUserId(userId);
        commentDO.setMomentId(momentId);
        commentMapper.deleteComment(commentDO);
    }
}
