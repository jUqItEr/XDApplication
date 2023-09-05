package com.dita.xd.service;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.HashtagBean;

import java.util.Vector;

public interface HashtagService extends Service {
    boolean addFeedHashtag(FeedBean bean, String hashtag);
    boolean addFeedHashtag(int feedId, String hashtag);
    boolean addHashtag(String hashtag);
    boolean removeFeedHashtag(FeedBean bean, String hashtag);
    boolean removeFeedHashtag(int feedId, String hashtag);
    boolean removeHashtag(String hashtag);
    int getQuotedHashtagCount(String hashtag);
    Vector<HashtagBean> getRecentHashtags(int limits);
    Vector<HashtagBean> getTrendHashtags(int limits);
    Vector<HashtagBean> search(String includedHashtag);
}
