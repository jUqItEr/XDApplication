package com.dita.xd.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

public class FeedBean {
    private int id;
    private String userId;
    private String userNickname;
    private String userProfileImage;
    private String content;
    private Timestamp createdAt;
    private int viewer;
    private Vector<MediaBean> mediaBeans;
    private Vector<FeedCommentBean> feedCommentBeans;
    private Vector<FeedbackBean> feedbackBeans;
    private Vector<LikeBean> likeBeans;

    public FeedBean() {
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

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
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

    public Vector<MediaBean> getMediaBeans() {
        return mediaBeans;
    }

    public void setMediaBeans(Vector<MediaBean> mediaBeans) {
        this.mediaBeans = mediaBeans;
    }

    public Vector<FeedCommentBean> getFeedCommentBeans() {
        return feedCommentBeans;
    }

    public void setFeedCommentBeans(Vector<FeedCommentBean> feedCommentBeans) {
        this.feedCommentBeans = feedCommentBeans;
    }

    public Vector<FeedbackBean> getFeedbackBeans() {
        return feedbackBeans;
    }

    public void setFeedbackBeans(Vector<FeedbackBean> feedbackBeans) {
        this.feedbackBeans = feedbackBeans;
    }

    public Vector<LikeBean> getLikeBeans() {
        return likeBeans;
    }

    public void setLikeBeans(Vector<LikeBean> likeBeans) {
        this.likeBeans = likeBeans;
    }

    @Override
    public String toString() {
        String fmt = "%5d  %15s(@%-15s)  %-30s  %30s  %d";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format(fmt, id, userNickname, userId, content, sdf.format(createdAt), viewer);
    }
}
