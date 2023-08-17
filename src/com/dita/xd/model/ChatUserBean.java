package com.dita.xd.model;

public class ChatUserBean {
    private int chatroomTblId;
    private String userTblId;

    public ChatUserBean(int chatroomTblId, String userTblId) {
        this.chatroomTblId = chatroomTblId;
        this.userTblId = userTblId;
    }

    public int getChatroomTblId() {
        return chatroomTblId;
    }

    public void setChatroomTblId(int chatroomTblId) {
        this.chatroomTblId = chatroomTblId;
    }

    public String getUserTblId() {
        return userTblId;
    }

    public void setUserTblId(String userTblId) {
        this.userTblId = userTblId;
    }
}
