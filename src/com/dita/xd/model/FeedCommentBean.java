package com.dita.xd.model;

import java.sql.Timestamp;

public class FeedCommentBean {
    private int commentId;
    private int originalFeedId;
    private int replyFeedId;
    private Timestamp createdAt;

    public FeedCommentBean() {

    }

    public FeedCommentBean(int commentId, int originalFeedId, int replyFeedId, Timestamp createdAt) {
        this.commentId = commentId;
        this.originalFeedId = originalFeedId;
        this.replyFeedId = replyFeedId;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getOriginalFeedId() {
        return originalFeedId;
    }

    public void setOriginalFeedId(int originalFeedId) {
        this.originalFeedId = originalFeedId;
    }

    public int getReplyFeedId() {
        return replyFeedId;
    }

    public void setReplyFeedId(int replyFeedId) {
        this.replyFeedId = replyFeedId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
