package com.dita.xd.service;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.HashtagBean;

import java.util.Vector;

public interface HashtagService extends Service {
    boolean addFeedHashtag(FeedBean bean, int hashtagId);
    boolean addFeedHashtag(int feedId, int hashtagId);
    int addHashtag(String hashtag);
    int getHashtagId(String content);
    boolean removeFeedHashtag(FeedBean bean, String hashtag);
    boolean removeFeedHashtag(int feedId, String hashtag);
    boolean removeHashtag(String hashtag);
    int getQuotedHashtagCount(String hashtag);
    Vector<HashtagBean> getRecentHashtags(int limits);
    Vector<HashtagBean> getTrendHashtags(int limits);
    Vector<HashtagBean> search(String includedHashtag);
}
