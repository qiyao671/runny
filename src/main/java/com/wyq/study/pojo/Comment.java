package com.wyq.study.pojo;

public class Comment {
    private Integer id;

    private Integer momentId;

    private String commentContent;

    private Integer userId;

    private Integer repliedUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMomentId() {
        return momentId;
    }

    public void setMomentId(Integer momentId) {
        this.momentId = momentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRepliedUserId() {
        return repliedUserId;
    }

    public void setRepliedUserId(Integer repliedUserId) {
        this.repliedUserId = repliedUserId;
    }
}