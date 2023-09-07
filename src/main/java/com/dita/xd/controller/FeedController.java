package com.dita.xd.controller;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.implementation.FeedServiceImpl;

import java.util.Vector;

public class FeedController {
    private FeedServiceImpl feedSvc = null;

    private UserRepository repository = null;

    public FeedController() {
        feedSvc = new FeedServiceImpl();
        repository = UserRepository.getInstance();
    }

    public int create(String userId, String content){
        return feedSvc.create(userId, content);
    }

    public FeedBean getFeed(int feedId) {
        return feedSvc.getFeed(feedId);
    }

    public Vector<FeedBean> getFeeds(String userId) {
        return feedSvc.getFeeds(userId);
    }

    public Vector<FeedBean> getRecentFeeds() {
        return feedSvc.getRecentFeeds();
    }

    public Vector<UserBean> getBookmarks(FeedBean feedBean) {
        return feedSvc.getBookmarks(feedBean);
    }
    public Vector<FeedBean> getBookmarks(String userId) {
        return feedSvc.getBookmarks(userId);
    }

    public Vector<UserBean> getFeedbacks(FeedBean feedBean) {
        return feedSvc.getFeedbacks(feedBean);
    }

    public Vector<UserBean> getLikes(FeedBean feedBean) {
        return feedSvc.getLikes(feedBean);
    }
    public Vector<FeedBean> getLikes(String userId) {
        return feedSvc.getLikes(userId);
    }

    public Vector<FeedBean> getUserFeeds(String userId) {
        return feedSvc.getUserFeeds(userId);
    }
    public Vector<FeedBean> search() {
        return null;
    }
}
