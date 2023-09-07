package com.dita.xd.service;

import com.dita.xd.model.*;

import java.util.Vector;

public interface ActivityService extends Service {
    boolean addBookmark(UserBean userBean, FeedBean feedBean);
    boolean addComment(UserBean userBean, FeedBean fromBean, FeedBean toBean, String content, Vector<MediaBean> medium);
    int addFeed(UserBean userBean, String content, Vector<MediaBean> medium);
    boolean addFeedback(UserBean userBean, FeedBean feedBean);
    boolean addFeedMedia(UserBean userBean, FeedBean feedBean, int mediaId);
    boolean addFeedMedium(UserBean userBean, FeedBean feedBean, Vector<Integer> mediaIds);
    int addMedia(UserBean userBean, MediaBean mediaBean);
    Vector<Integer> addMedium(UserBean userBean, Vector<MediaBean> mediaBeans);
    boolean addLike(UserBean userBean, FeedBean feedBean);
    boolean addUserBlock(UserBean fromUserBean, UserBean toUserBean);
    boolean addUserFollow(UserBean fromUserBean, UserBean toUserBean);
    boolean exitChatroom(ChatroomBean chatroomBean, UserBean userBean);
    boolean inviteChatroom(ChatroomBean chatroomBean, UserBean userBean);
    boolean isBlocked(UserBean fromBean, UserBean toBean);
    boolean isCheckedBookmark(UserBean userBean, FeedBean feedBean);
    boolean isCheckedFeedback(UserBean userBean, FeedBean feedBean);
    boolean isCheckedLike(UserBean userBean, FeedBean feedBean);
    boolean isFollowed(UserBean fromBean, UserBean toBean);
    boolean setProfile(UserBean userBean);
    boolean removeBookmark(UserBean userBean, FeedBean feedBean);
    boolean removeFeed(UserBean userBean, FeedBean feedBean);
    boolean removeComment(UserBean userBean, FeedBean fromBean, FeedBean toBean);
    boolean removeFeedback(UserBean userBean, FeedBean feedBean);
    boolean removeLike(UserBean userBean, FeedBean feedBean);
    boolean removeUserBlock(UserBean fromUserBean, UserBean toUserBean);
    boolean removeUserFollow(UserBean fromUserBean, UserBean toUserBean);
    boolean uploadHeaderImage(UserBean userBean, String address);
    boolean uploadProfileImage(UserBean userBean, String address);
    boolean updateViewer(FeedBean feedBean);
    
    int getFollowerCount(String userId);
    int getFollowingCount(String userId);

    ChatroomBean createChatroom(String name);
    ChatroomBean getChatroom(int chatroomId);

    Vector<UserBean> getFollowerList(UserBean userBean);
    Vector<UserBean> getFollowingList(UserBean userBean);

    Vector<FeedBean> searchFeed(String includedContent);
    Vector<HashtagBean> searchHashtag(String includedHashtag);
    Vector<FeedBean> searchFeedHashtag(String includedHashtag);
    Vector<UserBean> searchUser(String includedUserInfo);

    Vector<UserBean> getChatroomUsers(int chatroomId);
}
