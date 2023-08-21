package xd.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
}
