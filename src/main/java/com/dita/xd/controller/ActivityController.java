package com.dita.xd.controller;

import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.MediaBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.service.implementation.ActivityServiceImpl;

import java.text.NumberFormat;
import java.util.Vector;
import java.util.stream.Collectors;

public class ActivityController {
    private final ActivityServiceImpl svc;

    public ActivityController() {
        svc = new ActivityServiceImpl();
    }

    public boolean addBookmark(UserBean userBean, FeedBean feedBean) {
        return svc.addBookmark(userBean, feedBean);
    }

    public boolean addComment(UserBean userBean, FeedBean fromBean, FeedBean toBean,
                              String content, Vector<MediaBean> medium) {
        return svc.addComment(userBean, fromBean, toBean, content, medium);
    }

    public boolean addFeed(UserBean userBean, String content, Vector<MediaBean> medium) {
        return svc.addFeed(userBean, content, medium);
    }

    public boolean addFeedback(UserBean userBean, FeedBean feedBean) {
        return svc.addFeedback(userBean, feedBean);
    }

    public boolean addLike(UserBean userBean, FeedBean feedBean) {
        return svc.addLike(userBean, feedBean);
    }

    public boolean addUserBlock(UserBean fromUserBean, UserBean toUserBean) {
        return svc.addUserBlock(fromUserBean, toUserBean);
    }

    public boolean addUserFollow(UserBean fromUserBean, UserBean toUserBean) {
        return svc.addUserFollow(fromUserBean, toUserBean);
    }

    public boolean exitChatroom(ChatroomBean chatroomBean, UserBean userBean) {
        return svc.exitChatroom(chatroomBean, userBean);
    }

    public boolean inviteChatroom(ChatroomBean chatroomBean, UserBean userBean) {
        return svc.inviteChatroom(chatroomBean, userBean);
    }

    public boolean setProfile(UserBean userBean) {
        return svc.setProfile(userBean);
    }

    public boolean removeBookmark(UserBean userBean, FeedBean feedBean) {
        return svc.removeBookmark(userBean, feedBean);
    }

    public boolean removeFeed(UserBean userBean, FeedBean feedBean) {
        return svc.removeFeed(userBean, feedBean);
    }

    public boolean removeComment(UserBean userBean, FeedBean fromBean, FeedBean toBean) {
        return svc.removeComment(userBean, fromBean, toBean);
    }

    public boolean removeFeedback(UserBean userBean, FeedBean feedBean) {
        return svc.removeFeedback(userBean, feedBean);
    }

    public boolean removeUserBlock(UserBean fromUserBean, UserBean toUserBean) {
        return svc.removeUserBlock(fromUserBean, toUserBean);
    }

    public boolean removeUserFollow(UserBean fromUserBean, UserBean toUserBean) {
        return svc.removeUserFollow(fromUserBean, toUserBean);
    }

    public boolean uploadHeaderImage(UserBean userBean, String address) {
        return svc.uploadHeaderImage(userBean, address);
    }

    public boolean uploadProfileImage(UserBean userBean, String address) {
        return svc.uploadProfileImage(userBean, address);
    }

    public boolean updateViewer(FeedBean feedBean) {
        return svc.updateViewer(feedBean);
    }

    public ChatroomBean createChatroom(String name) {
        return svc.createChatroom(name);
    }

    public ChatroomBean getChatroom(int chatroomId) {
        return svc.getChatroom(chatroomId);
    }

    public int getFollowerCount(String userId) {
        return svc.getFollowerCount(userId);
    }

    public int getFollowingCount(String userId) {
        return svc.getFollowingCount(userId);
    }

    public Vector<Object> search(String searchContent) {
        Vector<Object> result = null;

        if (!searchContent.isEmpty()) {
            char delimiter = searchContent.charAt(0);

            switch (delimiter) {
                case '@' -> result = svc.searchUser(searchContent.substring(1))
                        .stream().map(bean -> (Object) bean).collect(Collectors.toCollection(Vector::new));
                case '#' -> result = svc.searchFeedHashtag(searchContent.substring(1))
                        .stream().map(bean -> (Object) bean).collect(Collectors.toCollection(Vector::new));
                default -> result = svc.searchFeed(searchContent)
                        .stream().map(bean -> (Object) bean).collect(Collectors.toCollection(Vector::new));
            }
        }
        return result;
    }
//    public Vector<UserBean> getChatroomUsers(int chatroomId);
}
