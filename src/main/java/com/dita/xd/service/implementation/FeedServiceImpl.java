package com.dita.xd.service.implementation;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.FeedUserTaggingBean;
import com.dita.xd.model.HashtagBean;
import com.dita.xd.model.MediaBean;
import com.dita.xd.service.FeedService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

import static com.dita.xd.util.helper.ResultSetExtractHelper.extractFeedBean;

public class FeedServiceImpl implements FeedService {
    private DBConnectionServiceImpl pool = null;

    public FeedServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
    }

    @Override
    public boolean comment(FeedBean targetBean, FeedBean commentBean) {
        return false;
    }

    @Override
    public void feedback(FeedBean bean, String userId) {

    }

    @Override
    public void like(FeedBean bean, String userId) {

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
    public Vector<FeedBean> getFeeds(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT ut.id AS user_id, ut.nickname AS nickname, ut.profile_image AS profile_image, " +
                "ft.id AS id, ft.content AS content, ft.created_at AS created_at, ft.viewer AS viewer " +
                "FROM user_tbl ut JOIN feed_tbl ft ON ut.id = ft.user_tbl_id WHERE ut.id = ? ORDER BY ft.id DESC";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
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
