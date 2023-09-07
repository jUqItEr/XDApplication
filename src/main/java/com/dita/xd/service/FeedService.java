package com.dita.xd.service;

import com.dita.xd.model.*;

import javax.print.attribute.standard.Media;
import java.sql.Timestamp;
import java.util.Vector;

public interface  FeedService {
    int create(String userId, String content);
    int create(String userId, String content, Timestamp createdAt);
    boolean create(String userId, String content, Vector<MediaBean> medium, Vector<HashtagBean> hashtags,
                    Vector<FeedUserTaggingBean> userTags, Timestamp createAt);
    FeedBean getFeed(int feedId);

    Vector<UserBean> getBookmarks(FeedBean feedBean);
    Vector<FeedBean> getBookmarks(String userId);
    Vector<FeedBean> getComments(FeedBean bean);
    Vector<UserBean> getFeedbacks(FeedBean bean);
    Vector<UserBean> getLikes(FeedBean bean);
    Vector<MediaBean> getMedium(FeedBean bean);

    Vector<FeedBean> getFeeds(String userId);
    Vector<FeedBean> getFeeds(String userId, Timestamp targetAt);
    Vector<FeedBean> getFeeds(String userId, Timestamp startAt, Timestamp endAt);

    Vector<FeedBean> search(String includedContent);
}
