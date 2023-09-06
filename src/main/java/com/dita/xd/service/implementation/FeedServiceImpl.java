package com.dita.xd.service.implementation;

import com.dita.xd.model.*;
import com.dita.xd.service.FeedService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

import static com.dita.xd.util.helper.ResultSetExtractHelper.*;

public class FeedServiceImpl implements FeedService {
    private DBConnectionServiceImpl pool = null;

    public FeedServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
    }

    @Override
    public boolean create(String userId, String content) {
        return create(userId, content, Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public boolean create(String userId, String content, Timestamp createdAt) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO feed_tbl VALUES (NULL, ?, ?, ?, 0)";
        boolean flag = false;

        try {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, content);
            pstmt.setTimestamp(3, createdAt, cal);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean create(String userId, String content, Vector<MediaBean> medium,
                          Vector<HashtagBean> hashtags, Vector<FeedUserTaggingBean> userTags, Timestamp createAt) {
        return false;
    }

    @Override
    public FeedBean getFeed(int feedId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM feed_tbl WHERE id = ?";

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return null;
    }

    @Override
    public Vector<UserBean> getBookmarks(FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_bookmark_view where feed_id = ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, feedBean.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("bookmark_user_id");
                String userNickname = rs.getString("bookmark_user_nickname");
                UserBean bean = new UserBean();

                bean.setUserId(userId);
                bean.setNickname(userNickname);
                beans.addElement(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<FeedBean> getBookmarks(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_bookmark_view where bookmark_user_id = ?";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FeedBean bean = extractBookmarkBean(rs);
                bean.setFeedbackBeans(getFeedbacks(bean));
                bean.setFeedCommentBeans(getComments(bean));
                bean.setLikeBeans(getLikes(bean));
                bean.setMediaBeans(getMedium(bean));
                beans.addElement(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<FeedBean> getComments(FeedBean bean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_comment_view where feed_original_id = ?";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bean.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FeedBean newBean = new FeedBean();
                newBean.setFeedbackBeans(getFeedbacks(newBean));
                newBean.setFeedCommentBeans(getComments(newBean));
                newBean.setLikeBeans(getLikes(newBean));
                newBean.setMediaBeans(getMedium(newBean));
                beans.addElement(newBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<UserBean> getFeedbacks(FeedBean bean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_feedback_view where feed_id = ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bean.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.addElement(extractFeedbackUserBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<UserBean> getLikes(FeedBean bean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_like_view where feed_id = ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bean.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.add(extractLikeUserBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<MediaBean> getMedium(FeedBean bean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_media_view where feed_id = ?";
        Vector<MediaBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bean.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.addElement(extractMediaBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<FeedBean> getFeeds(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_user_view where user_id = ?";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FeedBean bean = extractFeedBean(rs);

                if (bean.getUserId() != null) {
                    bean.setFeedbackBeans(getFeedbacks(bean));
                    bean.setFeedCommentBeans(getComments(bean));
                    bean.setLikeBeans(getLikes(bean));
                    bean.setMediaBeans(getMedium(bean));
                    beans.addElement(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    @Deprecated
    public Vector<FeedBean> getFeeds(String userId, Timestamp targetAt) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM feed_tbl WHERE user_tbl_id = ? AND DATE(created_at) = DATE(?) ORDER BY id DESC";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setTimestamp(2, targetAt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.addElement(extractFeedBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    @Deprecated
    public Vector<FeedBean> getFeeds(String userId, Timestamp startAt, Timestamp endAt) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM feed_tbl WHERE user_tbl_id = ? AND " +
                "DATE(created_at) BETWEEN DATE(?) AND DATE(?) ORDER BY id DESC";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setTimestamp(2, startAt);
            pstmt.setTimestamp(3, endAt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.addElement(extractFeedBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    @Deprecated
    public Vector<FeedBean> search(String includedContent) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM feed_tbl WHERE content LIKE ? ORDER BY id DESC";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, '%' + includedContent + '%');
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.addElement(extractFeedBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }
}
