package com.dita.xd.model;

import java.sql.Timestamp;

public class FeedbackBean {
    private int id;
    private int feedId;
    private String userId;
    private Timestamp createdAt;

    public FeedbackBean(int id, int feedId, String userId, Timestamp createdAt) {
        this.id = id;
        this.feedId = feedId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
