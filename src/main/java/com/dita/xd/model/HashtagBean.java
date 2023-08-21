package com.dita.xd.model;

public class HashtagBean {
    private int hashtagId;
    private String content;

    public HashtagBean() {
    }

    public HashtagBean(int hashtagId, String content) {
        this.hashtagId = hashtagId;
        this.content = content;
    }

    public int getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(int hashtagId) {
        this.hashtagId = hashtagId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
