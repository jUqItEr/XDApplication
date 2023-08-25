package com.dita.xd.service;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.FeedUserTaggingBean;
import com.dita.xd.model.HashtagBean;
import com.dita.xd.model.MediaBean;

import java.sql.Timestamp;
import java.util.Vector;

public interface  FeedService {
    boolean comment(FeedBean targetBean, FeedBean commentBean);
    void feedback(FeedBean bean, String userId);
    void like(FeedBean bean, String userId);
    FeedBean create(String userId, String content);
    FeedBean create(String userId, String content, Timestamp createdAt);
    FeedBean create(String userId, String content, Vector<MediaBean> medium, Vector<HashtagBean> hashtags,
                    Vector<FeedUserTaggingBean> userTags, Timestamp createAt);
    FeedBean getFeed(int feedId);

    Vector<FeedBean> getFeeds(String userId);
    Vector<FeedBean> getFeeds(String userId, Timestamp targetAt);
    Vector<FeedBean> getFeeds(String userId, Timestamp startAt, Timestamp endAt);
}
