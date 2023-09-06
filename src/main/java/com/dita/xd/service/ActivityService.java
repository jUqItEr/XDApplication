package com.dita.xd.service;

import com.dita.xd.model.*;

import java.util.Vector;

public interface ActivityService extends Service {
    boolean addBookmark(UserBean userBean, FeedBean feedBean);
    boolean addComment(UserBean userBean, FeedBean fromBean, FeedBean toBean, String content, Vector<MediaBean> medium);
    boolean addFeed(UserBean userBean, String content, Vector<MediaBean> medium);
    boolean addFeedback(UserBean userBean, FeedBean feedBean);
    boolean addMedium(Vector<MediaBean> mediaBeans);
    boolean addLike(UserBean userBean, FeedBean feedBean);
    boolean addUserBlock(UserBean fromUserBean, UserBean toUserBean);
    boolean addUserFollow(UserBean fromUserBean, UserBean toUserBean);
    boolean exitChatroom(ChatroomBean chatroomBean, UserBean userBean);
    boolean inviteChatroom(ChatroomBean chatroomBean, UserBean userBean);
    boolean setProfile(UserBean userBean);
    boolean removeBookmark(UserBean userBean, FeedBean feedBean);
    boolean removeFeed(UserBean userBean, FeedBean feedBean);
    boolean removeComment(UserBean userBean, FeedBean fromBean, FeedBean toBean);
    boolean removeFeedback(UserBean userBean, FeedBean feedBean);
    boolean removeUserBlock(UserBean fromUserBean, UserBean toUserBean);
    boolean removeUserFollow(UserBean fromUserBean, UserBean toUserBean);
    boolean uploadHeaderImage(UserBean userBean, String address);
    boolean uploadProfileImage(UserBean userBean, String address);
    boolean updateViewer(FeedBean feedBean);
    
    int getFollowerCount(String userId);
    int getFollowingCount(String userId);

    ChatroomBean createChatroom(String name);
    ChatroomBean getChatroom(int chatroomId);

    Vector<FeedBean> searchFeed(String includedContent);
    Vector<HashtagBean> searchHashtag(String includedHashtag);
    Vector<FeedBean> searchFeedHashtag(String includedHashtag);
    Vector<UserBean> searchUser(String includedUserInfo);

    Vector<UserBean> getChatroomUsers(int chatroomId);
}
