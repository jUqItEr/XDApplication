package com.dita.xd.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FeedHashtagBean {
    private int feedId;
    private int hashtagId;
    private Timestamp uploadedAt;

    public FeedHashtagBean() {
    }

    public FeedHashtagBean(int feedId, int hashtagId, Timestamp uploadedAt) {
        this.feedId = feedId;
        this.hashtagId = hashtagId;
        this.uploadedAt = uploadedAt;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public int getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(int hashtagId) {
        this.hashtagId = hashtagId;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
