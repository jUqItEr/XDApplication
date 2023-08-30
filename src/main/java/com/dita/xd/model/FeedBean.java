package com.dita.xd.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

public class FeedBean {
    private int id;
    private String userId;
    private String content;
    private Timestamp createdAt;
    private int viewer;

    public FeedBean() {
    }

    public FeedBean(int id, String userId, String content, Timestamp createdAt, int viewer) {
        this.id = id;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        String fmt = "%5d  %-15s  %-30s  %30s  %d";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format(fmt, id, userId, content, sdf.format(createdAt), viewer);
    }
}
