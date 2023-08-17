package com.dita.xd.model;

import java.time.LocalDateTime;

public class ChatMessageBean {
    private int id;
    private String content;
    private int chatroomTblId;
    private String userTblId;
    private LocalDateTime createdAt;
    private char readState;

    public ChatMessageBean(int id, String content, int chatroomTblId, String userTblId, LocalDateTime createdAt, char readState) {
        this.id = id;
        this.content = content;
        this.chatroomTblId = chatroomTblId;
        this.userTblId = userTblId;
        this.createdAt = createdAt;
        this.readState = readState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public char getReadState() {
        return readState;
    }

    public void setReadState(char readState) {
        this.readState = readState;
    }
}
