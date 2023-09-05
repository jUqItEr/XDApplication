package com.dita.xd.service.implementation;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.HashtagBean;
import com.dita.xd.service.HashtagService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class HashtagServiceImpl implements HashtagService {
    private final DBConnectionServiceImpl pool;

    public HashtagServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
    }

    @Override
    public boolean addFeedHashtag(FeedBean bean, String hashtag) {
        boolean result = false;

        if (bean != null) {
            result = addFeedHashtag(bean.getId(), hashtag);
        }
        return result;
    }

    @Override
    public boolean addFeedHashtag(int feedId, String hashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into feed_hashtag_tbl values (?, ?, now())";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);

            int cnt = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean addHashtag(String hashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into hashtag_tbl select null, ? from dual where not exists(" +
                "select * from hashtag_tbl where content = ?)";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hashtag);
            pstmt.setString(2, hashtag);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean removeFeedHashtag(FeedBean bean, String hashtag) {
        boolean result = false;

        if (bean != null) {
            result = removeFeedHashtag(bean.getId(), hashtag);
        }
        return result;
    }

    @Override
    public boolean removeFeedHashtag(int feedId, String hashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from feed_hashtag_table fht join hashtag_tbl ht on fht.hashtag_tbl_id = ht.id " +
                "where fht.feed_tbl_id = ? and ht.content = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, feedId);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean removeHashtag(String hashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from hashtag_tbl where content = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hashtag);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public int getQuotedHashtagCount(String hashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(ht.content) from hashtag_tbl ht join feed_hashtag_tbl fht " +
                "on ht.id = fht.hashtag_tbl_id where ht.content = ?";
        int result = -1;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hashtag);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return result;
    }

    @Override
    public Vector<HashtagBean> getRecentHashtags(int limits) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select ht.* from hashtag_tbl ht join feed_hashtag_tbl fht on ht.id = fht.hashtag_tbl_id " +
                "order by uploaded_at desc limit ?";
        Vector<HashtagBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limits);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String content = rs.getString("ht.content");
                int id = rs.getInt("ht.id");
                beans.addElement(new HashtagBean(id, content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<HashtagBean> getTrendHashtags(int limits) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select ht.content as content, count(*) as total_count " +
                "from hashtag_tbl ht join feed_hashtag_tbl fht on ht.id = fht.hashtag_tbl_id " +
                "where date(uploaded_at) = curdate() group by content order by count(*) desc limit ?";
        Vector<HashtagBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limits);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String content = rs.getString("content");
                int id = rs.getInt("total_count");        // This will not be id!!!
                beans.addElement(new HashtagBean(id, content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<HashtagBean> search(String includedHashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from hashtag_tbl where like ?";
        Vector<HashtagBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, '%' + includedHashtag + '%');
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String content = rs.getString("content");
                int id = rs.getInt("id");
                beans.addElement(new HashtagBean(id, content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }
}
