package com.dita.xd.model;

import java.sql.Timestamp;

public class LikeBean {
    private int likeId;
    private int feedId;
    private String userId;
    private Timestamp createdAt;

    public LikeBean() {
    }

    public LikeBean(int likeId, int feedId, String userId, Timestamp createdAt) {
        this.likeId = likeId;
        this.feedId = feedId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
