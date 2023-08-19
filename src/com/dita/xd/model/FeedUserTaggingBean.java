package com.dita.xd.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FeedUserTaggingBean {
    private  int feedTblId;
    private String userTblId;
    private char taggingType;
    private Timestamp taggedAt;

    public FeedUserTaggingBean(int feedTblId, String userTblId, char taggingType, Timestamp taggedAt) {
        this.feedTblId = feedTblId;
        this.userTblId = userTblId;
        this.taggingType = taggingType;
        this.taggedAt = taggedAt;
    }

    public int getFeedTblId() {
        return feedTblId;
    }

    public void setFeedTblId(int feedTblId) {
        this.feedTblId = feedTblId;
    }

    public String getUserTblId() {
        return userTblId;
    }

    public void setUserTblId(String userTblId) {
        this.userTblId = userTblId;
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
