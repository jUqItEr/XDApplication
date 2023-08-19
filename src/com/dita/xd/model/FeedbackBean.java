package com.dita.xd.model;

import java.time.LocalDateTime;

public class FeedbackBean {
    private int id;
    private int feedTblId;
    private String userTblId;
    private LocalDateTime createdAt;

    public FeedbackBean(int id, int feedTblId, String userTblId, LocalDateTime createdAt) {
        this.id = id;
        this.feedTblId = feedTblId;
        this.userTblId = userTblId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeedTblId() {
        return feedTblId;
    }

    public void setFeedTblId(int feedTblId) {
        this.feedTblId = feedTblId;
    }

    public String getUserTblId() {
        return userTblId;
    }

    public void setUserTblId(String userTblId) {
        this.userTblId = userTblId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
