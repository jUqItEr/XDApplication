package com.dita.xd.model;

import java.time.LocalDateTime;

public class FeedMediaBean {
    private int mediaId;

    private int feedId;

    private LocalDateTime uploadedAt;

    public FeedMediaBean(int mediaId, int feedId, LocalDateTime uploadedAt) {
        this.mediaId = mediaId;
        this.feedId = feedId;
        this.uploadedAt = uploadedAt;
    }

    public int getMediaId() {
        return mediaId;
    }

    public int getFeedId() {
        return feedId;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
