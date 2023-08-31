package com.dita.xd.service;

import com.dita.xd.model.*;

import java.sql.Timestamp;
import java.util.Vector;

public interface  FeedService {
    boolean create(String userId, String content);
    boolean create(String userId, String content, Timestamp createdAt);
    boolean create(String userId, String content, Vector<MediaBean> medium, Vector<HashtagBean> hashtags,
                    Vector<FeedUserTaggingBean> userTags, Timestamp createAt);
    FeedBean getFeed(int feedId);

    Vector<FeedCommentBean> getComments(FeedBean bean);
    Vector<FeedbackBean> getFeedbacks(FeedBean bean);
    Vector<LikeBean> getLikes(FeedBean bean);

    Vector<FeedBean> getFeeds(String userId);
    Vector<FeedBean> getFeeds(String userId, Timestamp targetAt);
    Vector<FeedBean> getFeeds(String userId, Timestamp startAt, Timestamp endAt);

    Vector<FeedBean> search(String includedContent);
}
