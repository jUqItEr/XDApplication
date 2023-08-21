package xd.model;

import java.sql.Timestamp;

public class FeedMediaBean {
    private int mediaId;
    private int feedId;
    private Timestamp uploadedAt;

    public FeedMediaBean() {
    }

    public FeedMediaBean(int mediaId, int feedId, Timestamp uploadedAt) {
        this.mediaId = mediaId;
        this.feedId = feedId;
        this.uploadedAt = uploadedAt;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
