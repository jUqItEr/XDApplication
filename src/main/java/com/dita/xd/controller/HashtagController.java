package com.dita.xd.controller;

import com.dita.xd.model.HashtagBean;
import com.dita.xd.service.implementation.HashtagServiceImpl;

import java.util.Vector;

public class HashtagController {
    private final HashtagServiceImpl svc;

    public HashtagController() {
        svc = new HashtagServiceImpl();
    }

    // 피드 전체에서 인용된 건수를 가져옴
    public int getQuotedHashtagCount(String hashtag) {
        return svc.getQuotedHashtagCount(hashtag);
    }

    // 최신 트렌드 (개수 지정 가능) 순서대로 불러옴 (최근에 언급된 순서)
    public Vector<HashtagBean> getRecentHashtags(int limits) {
        return svc.getRecentHashtags(limits);
    }

    // 원하는 개수만큼 오늘 날짜로 올라온 해시태그 중, 가장 많은 숫자를 차지한 해시태그를 선출
    public Vector<HashtagBean> getTrendHashtags(int limits) {
        return svc.getTrendHashtags(limits);
    }

    public Vector<HashtagBean> search(String includedHashtag) {
        return svc.search(includedHashtag);
    }
}
