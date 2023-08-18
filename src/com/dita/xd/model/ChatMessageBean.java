package com.dita.xd.model;

import java.time.LocalDateTime;

public class ChatMessageBean {
    private int id;
    private String content;
    private int chatroomId;
    private String userId;
    private LocalDateTime createdAt;
    private char readState;

    public ChatMessageBean() {

    }

    public ChatMessageBean(int id, String content, int chatroomTblId,
                           String userTblId, LocalDateTime createdAt, char readState) {
        this.id = id;
        this.content = content;
        this.chatroomId = chatroomTblId;
        this.userId = userTblId;
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

    public int getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
