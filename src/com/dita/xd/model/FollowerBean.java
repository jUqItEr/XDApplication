package com.dita.xd.model;

import java.sql.Timestamp;

public class FollowerBean {
    private String userId;
    private String userFollowerId;

    private Timestamp createdAt;

    public FollowerBean() {

    }

    public FollowerBean(String userId, String userFollowerId, Timestamp createdAt) {
        this.userId = userId;
        this.userFollowerId = userFollowerId;
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFollowerId() {
        return userFollowerId;
    }

    public void setUserFollowerId(String userFollowerId) {
        this.userFollowerId = userFollowerId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
