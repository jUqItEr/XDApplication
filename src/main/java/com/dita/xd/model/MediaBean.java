package com.dita.xd.model;

import java.sql.Timestamp;

public class MediaBean {
    private int id;
    private String userId;
    private String contentType;
    private String contentAddress;
    private String contentCensoredType;
    private Timestamp createdAt;

    public MediaBean() {
    }

    public MediaBean(int id, String userId, String contentType, String contentAddress,
                     String contentCensoredType, Timestamp createdAt) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentAddress() {
        return contentAddress;
    }

    public void setContentAddress(String contentAddress) {
        this.contentAddress = contentAddress;
    }

    public String getContentCensoredType() {
        return contentCensoredType;
    }

    public void setContentCensoredType(String contentCensoredType) {
        this.contentCensoredType = contentCensoredType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
