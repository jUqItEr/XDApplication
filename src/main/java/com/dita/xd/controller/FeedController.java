package com.dita.xd.controller;

import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.implementation.FeedServiceImpl;

public class FeedController {
    private FeedServiceImpl feedSvc = null;

    private UserRepository repository = null;

    public FeedController() {
        feedSvc = new FeedServiceImpl();
        repository = UserRepository.getInstance();
    }

    public void search() {

    }
}
