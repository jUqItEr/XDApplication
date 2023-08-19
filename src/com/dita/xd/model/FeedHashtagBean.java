package com.dita.xd.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FeedHashtagBean {
    private int feedTblId;
    private int hashtagTblId;
    private Timestamp uploadedAt;

    public FeedHashtagBean(int feedTblId, int hashtagTblId, Timestamp uploadedAt) {
        this.feedTblId = feedTblId;
        this.hashtagTblId = hashtagTblId;
        this.uploadedAt = uploadedAt;
    }

    public int getFeedTblId() {
        return feedTblId;
    }

    public void setFeedTblId(int feedTblId) {
        this.feedTblId = feedTblId;
    }

    public int getHashtagTblId() {
        return hashtagTblId;
    }

    public void setHashtagTblId(int hashtagTblId) {
        this.hashtagTblId = hashtagTblId;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
