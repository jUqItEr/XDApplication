package com.dita.xd.model;

public class BlockBean {
    private String userId;
    private String userBlockId;

    public BlockBean() {

    }

    public BlockBean(String userId, String userBlockId) {
        this.userId = userId;
        this.userBlockId = userBlockId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserBlockId() {
        return userBlockId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserBlockId(String userBlockId) {
        this.userBlockId = userBlockId;
    }
}
