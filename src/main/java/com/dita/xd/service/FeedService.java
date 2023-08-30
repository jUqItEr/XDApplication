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
    boolean create(String userId, String content);
    boolean create(String userId, String content, Timestamp createdAt);
    boolean create(String userId, String content, Vector<MediaBean> medium, Vector<HashtagBean> hashtags,
                    Vector<FeedUserTaggingBean> userTags, Timestamp createAt);
    FeedBean getFeed(int feedId);

    Vector<FeedBean> getFeeds(String userId);
    Vector<FeedBean> getFeeds(String userId, Timestamp targetAt);
    Vector<FeedBean> getFeeds(String userId, Timestamp startAt, Timestamp endAt);

    Vector<FeedBean> search(String includedContent);
}
