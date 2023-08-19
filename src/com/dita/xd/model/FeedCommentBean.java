package com.dita.xd.model;

public class FeedCommentBean {
    private int commentId;
    private int originalFeedId;
    private int replyFeedId;

    public FeedCommentBean() {

    }

    public FeedCommentBean(int commentId, int originalFeedId, int replyFeedId) {
        this.commentId = commentId;
        this.originalFeedId = originalFeedId;
        this.replyFeedId = replyFeedId;
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
}
