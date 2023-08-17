package com.dita.xd.model;

public class FollowerBean {
    private String userId;
    private String userFollowerId;

    public FollowerBean(String userId, String userFollowerId) {
        this.userId = userId;
        this.userFollowerId = userFollowerId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFollowerId() {
        return userFollowerId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserFollowerId(String userFollowerId) {
        this.userFollowerId = userFollowerId;
    }
}
