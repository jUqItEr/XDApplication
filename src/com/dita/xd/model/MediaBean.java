package com.dita.xd.model;

import java.security.Timestamp;

public class MediaBean {

    private int id;

    private String userId;

    private char contentType;

    private String contentAddress;

    private char contentCensoredType;

    private Timestamp createdAt;

    public MediaBean(int id, String userId, char contentType, String contentAddress, char contentCensoredType, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.contentType = contentType;
        this.contentAddress = contentAddress;
        this.contentCensoredType = contentCensoredType;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public char getContentType() {
        return contentType;
    }

    public String getContentAddress() {
        return contentAddress;
    }

    public char getContentCensoredType() {
        return contentCensoredType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContentType(char contentType) {
        this.contentType = contentType;
    }

    public void setContentAddress(String contentAddress) {
        this.contentAddress = contentAddress;
    }

    public void setContentCensoredType(char contentCensoredType) {
        this.contentCensoredType = contentCensoredType;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
