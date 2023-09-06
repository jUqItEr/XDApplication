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

    public boolean create(String userId, String content){
        return feedSvc.create(userId, content);
    }

    public Vector<FeedBean> getFeeds(String userId) {
        return feedSvc.getFeeds(userId);
    }

    public Vector<UserBean> getBookmarks(FeedBean feedBean) {
        return feedSvc.getBookmarks(feedBean);
    }

    public Vector<UserBean> getFeedbacks(FeedBean feedBean) {
        return feedSvc.getFeedbacks(feedBean);
    }

    public Vector<UserBean> getLikes(FeedBean feedBean) {
        return feedSvc.getLikes(feedBean);
    }
    public Vector<FeedBean> search() {
        return null;
    }
}
