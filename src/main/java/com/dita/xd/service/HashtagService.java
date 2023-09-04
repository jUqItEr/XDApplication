package com.dita.xd.service;

import com.dita.xd.model.HashtagBean;

import java.util.Vector;

public interface HashtagService extends Service {
    int getQuotedHashtagCount(String hashtag);
    Vector<HashtagBean> getRecentHashtags(int limits);
    Vector<HashtagBean> getTrendHashtags(int limits);
    Vector<HashtagBean> search(String includedHashtag);
}
