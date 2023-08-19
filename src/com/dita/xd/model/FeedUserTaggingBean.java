package com.dita.xd.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FeedUserTaggingBean {
    private  int feedId;
    private String userId;
    private char taggingType;
    private Timestamp taggedAt;

    public FeedUserTaggingBean() {
    }

    public FeedUserTaggingBean(int feedId, String userId, char taggingType, Timestamp taggedAt) {
        this.feedId = feedId;
        this.userId = userId;
        this.taggingType = taggingType;
        this.taggedAt = taggedAt;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public char getTaggingType() {
        return taggingType;
    }

    public void setTaggingType(char taggingType) {
        this.taggingType = taggingType;
    }

    public Timestamp getTaggedAt() {
        return taggedAt;
    }

    public void setTaggedAt(Timestamp taggedAt) {
        this.taggedAt = taggedAt;
    }
}
