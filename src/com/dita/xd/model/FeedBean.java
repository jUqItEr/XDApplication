package com.dita.xd.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FeedBean {
    private int id;
    private String userTblId;
    private String content;
    private Timestamp createdAt;
    private int viewer;

    public FeedBean(int id, String userTblId, String content, Timestamp createdAt, int viewer) {
        this.id = id;
        this.userTblId = userTblId;
        this.content = content;
        this.createdAt = createdAt;
        this.viewer = viewer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserTblId() {
        return userTblId;
    }

    public void setUserTblId(String userTblId) {
        this.userTblId = userTblId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
    }
}
