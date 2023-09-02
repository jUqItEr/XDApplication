package com.dita.xd.service.implementation;

import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.MediaBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.service.ActivityService;
import com.dita.xd.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ActivityServiceImpl implements ActivityService {
    private final DBConnectionServiceImpl pool;
    private final UserService userSvc;

    public ActivityServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
        userSvc = new UserServiceImpl();
    }

    @Override
    public boolean addBookmark(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into bookmark_tbl values (null, ?, ?, now())";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userBean.getUserId());
            pstmt.setInt(2, feedBean.getId());
            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean addComment(UserBean userBean, FeedBean feedBean, String content, Vector<MediaBean> medium) {
        return false;
    }

    @Override
    public boolean addFeed(UserBean userBean, String content, Vector<MediaBean> medium) {
        return false;
    }

    @Override
    public boolean addFeedback(UserBean userBean, FeedBean feedBean) {
        return false;
    }

    @Override
    public boolean addLike(UserBean userBean, FeedBean feedBean) {
        return false;
    }

    @Override
    public boolean addUserBlock(UserBean fromUserBean, UserBean toUserBean) {
        return false;
    }

    @Override
    public boolean addUserFollow(UserBean fromUserBean, UserBean toUserBean) {
        return false;
    }

    @Override
    public boolean exitChatroom(ChatroomBean chatroomBean, UserBean userBean) {
        return false;
    }

    @Override
    public boolean inviteChatroom(ChatroomBean chatroomBean, UserBean userBean) {
        return false;
    }

    @Override
    public boolean setProfile(UserBean userBean) {
        return false;
    }

    @Override
    public boolean search(String searchContent) {
        char delimeter = searchContent.charAt(0);

        switch (delimeter) {
            case '#' -> {

            }
            case '@' -> {

            }
            default -> {

            }
        }
        return false;
    }

    @Override
    public boolean removeBookmark(UserBean userBean, FeedBean feedBean) {
        return false;
    }

    @Override
    public boolean removeFeed(UserBean userBean, FeedBean feedBean) {
        return false;
    }

    @Override
    public boolean removeComment(UserBean userBean, FeedBean feedBean) {
        return false;
    }

    @Override
    public boolean removeFeedback(UserBean userBean, FeedBean feedBean) {
        return false;
    }

    @Override
    public boolean removeUserBlock(UserBean fromUserBean, UserBean toUserBean) {
        return false;
    }

    @Override
    public boolean removeUserFollow(UserBean fromUserBean, UserBean toUserBean) {
        return false;
    }

    @Override
    public boolean uploadHeaderImage(UserBean userBean, String address) {
        return false;
    }

    @Override
    public boolean uploadProfileImage(UserBean userBean, String address) {
        return false;
    }

    @Override
    public boolean updateViewer(FeedBean feedBean) {
        return false;
    }

    @Override
    public ChatroomBean createChatroom(String name) {
        return null;
    }
}
