package com.dita.xd.service;

import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.MediaBean;
import com.dita.xd.model.UserBean;

import java.util.Vector;

public interface ActivityService {
    boolean addBookmark(UserBean userBean, FeedBean feedBean);
    boolean addComment(UserBean userBean, FeedBean feedBean, String content, Vector<MediaBean> medium);
    boolean addFeed(UserBean userBean, String content, Vector<MediaBean> medium);
    boolean addFeedback(UserBean userBean, FeedBean feedBean);
    boolean addLike(UserBean userBean, FeedBean feedBean);
    boolean addUserBlock(UserBean fromUserBean, UserBean toUserBean);
    boolean addUserFollow(UserBean fromUserBean, UserBean toUserBean);
    boolean exitChatroom(ChatroomBean chatroomBean, UserBean userBean);
    boolean inviteChatroom(ChatroomBean chatroomBean, UserBean userBean);
    boolean setProfile(UserBean userBean);
    boolean search(String searchContent);
    boolean removeBookmark(UserBean userBean, FeedBean feedBean);
    boolean removeFeed(UserBean userBean, FeedBean feedBean);
    boolean removeComment(UserBean userBean, FeedBean feedBean);
    boolean removeFeedback(UserBean userBean, FeedBean feedBean);
    boolean removeUserBlock(UserBean fromUserBean, UserBean toUserBean);
    boolean removeUserFollow(UserBean fromUserBean, UserBean toUserBean);
    boolean uploadHeaderImage(UserBean userBean, String address);
    boolean uploadProfileImage(UserBean userBean, String address);
    boolean updateViewer(FeedBean feedBean);
    ChatroomBean createChatroom(String name);
}
