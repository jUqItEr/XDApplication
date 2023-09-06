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

//    public boolean addBookmark(UserBean userBean, FeedBean feedBean);
//    public boolean addComment(UserBean userBean, FeedBean feedBean, String content, Vector<MediaBean> medium);
//    public boolean addFeed(UserBean userBean, String content, Vector<MediaBean> medium);
//    public boolean addFeedback(UserBean userBean, FeedBean feedBean);
//    public boolean addLike(UserBean userBean, FeedBean feedBean);
//    public boolean addUserBlock(UserBean fromUserBean, UserBean toUserBean);
//    public boolean addUserFollow(UserBean fromUserBean, UserBean toUserBean);
//    public boolean exitChatroom(ChatroomBean chatroomBean, UserBean userBean);
//    public boolean inviteChatroom(ChatroomBean chatroomBean, UserBean userBean);
//    public boolean setProfile(UserBean userBean);
//    public boolean removeBookmark(UserBean userBean, FeedBean feedBean);
//    public boolean removeFeed(UserBean userBean, FeedBean feedBean);
//    public boolean removeComment(UserBean userBean, FeedBean feedBean);
//    public boolean removeFeedback(UserBean userBean, FeedBean feedBean);
//    public boolean removeUserBlock(UserBean fromUserBean, UserBean toUserBean);
//    public boolean removeUserFollow(UserBean fromUserBean, UserBean toUserBean);
//    public boolean uploadHeaderImage(UserBean userBean, String address);
//    public boolean uploadProfileImage(UserBean userBean, String address);
//    public boolean updateViewer(FeedBean feedBean);
//    public ChatroomBean createChatroom(String name);
//    public ChatroomBean getChatroom(int chatroomId);

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
