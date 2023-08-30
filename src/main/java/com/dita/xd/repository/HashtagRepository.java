package com.dita.xd.repository;

import com.dita.xd.model.HashtagBean;

import java.util.Vector;

public class HashtagRepository {
    private static volatile HashtagRepository instance = null;
    private final Vector<HashtagBean> hashtags;

    private HashtagRepository(){
        hashtags = new Vector<>();
    }

    public static HashtagRepository getInstance() {
        if (instance == null) {
            synchronized (HashtagRepository.class) {
                if (instance == null) {
                    instance = new HashtagRepository();
                }
            }
        }
        return instance;
    }

    public void addHashtag(HashtagBean bean) {
        hashtags.addElement(bean);
    }

    public void removeHashTag(HashtagBean bean) {
        hashtags.removeElement(bean);
    }

    public HashtagBean getHashtag(int index) {
        HashtagBean bean = null;

        if (hashtags.size() > index) {
            bean = hashtags.get(index);
        }
        return bean;
    }

    public HashtagBean getHashtag(String content) {
        return hashtags.stream()
                .filter(hashtagBean -> hashtagBean.getContent().equals(content))
                .findFirst()
                .orElse(null);
    }
}
