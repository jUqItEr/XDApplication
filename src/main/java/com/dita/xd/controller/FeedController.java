package com.dita.xd.controller;

import com.dita.xd.model.FeedBean;
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

    public Vector<FeedBean> getFeeds(String userId) {
        return feedSvc.getFeeds(userId);
    }

    public Vector<FeedBean> search() {
        return null;
    }
}
