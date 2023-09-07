package com.dita.xd.service.implementation;

import com.dita.xd.model.*;
import com.dita.xd.service.ActivityService;
import com.dita.xd.service.UserService;

import java.sql.*;
import java.util.HashSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dita.xd.util.helper.ResultSetExtractHelper.*;

public class ActivityServiceImpl implements ActivityService {
    private final DBConnectionServiceImpl pool;
    private final FeedServiceImpl feedSvc;
    private final HashtagServiceImpl hashSvc;
    private final UserServiceImpl userSvc;

    public ActivityServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
        feedSvc = new FeedServiceImpl();
        hashSvc = new HashtagServiceImpl();
        userSvc = new UserServiceImpl();
    }

    @Override
    public boolean addBookmark(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into bookmark_tbl select null, ?, ?, now() from dual where not exists(" +
                "select null from bookmark_tbl where user_tbl_id = ? and feed_tbl_id = ?)";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userBean.getUserId());
            pstmt.setInt(2, feedBean.getId());
            pstmt.setString(3, userBean.getUserId());
            pstmt.setInt(4, feedBean.getId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean addComment(UserBean userBean, FeedBean fromBean, FeedBean toBean,
                              String content, Vector<MediaBean> medium) {
        if (medium != null && !medium.isEmpty()) {

        }
        return false;
    }

    @Override
    public int addFeed(UserBean userBean, String content, Vector<MediaBean> medium) {
        final String hashtag_regex = "(#[a-zA-Zㄱ-ㅎ가-힣0-9(_)]+)";
        final String user_regex = "(@[a-zA-Z0-9]{1,15})";

        final Vector<Integer> mediaIds = new Vector<>();
        final Vector<Integer> hashtagIds = new Vector<>();
        final HashSet<String> hashtags = new HashSet<>();
        final HashSet<String> userTags = new HashSet<>();

        // 중복 검사
        final Pattern ptnHashtag = Pattern.compile(hashtag_regex);
        final Pattern ptnUserTag = Pattern.compile(user_regex);
        final Matcher matcherHashtag = ptnHashtag.matcher(content);
        final Matcher matcherUserTag = ptnUserTag.matcher(content);

        while (matcherHashtag.find()) {
            hashtags.add(matcherHashtag.group());
        }
        while (matcherUserTag.find()) {
            userTags.add(matcherUserTag.group().substring(1));
        }
        // 해시태그 테이블에 추가
        hashtags.forEach(tag -> hashtagIds.add(hashSvc.addHashtag(tag)));

        // 미디어 등록
        if (medium != null && !medium.isEmpty()) {
            mediaIds.addAll(addMedium(userBean, medium));
        }
        // 피드 입력
        FeedBean feedBean = new FeedBean();
        int result = feedSvc.create(userBean.getUserId(), content);
        feedBean.setId(result);

        // 사용자가 어느 피드에서 호출되었는가?
        userTags.forEach(tag -> {
            UserBean taggedUser = new UserBean();
            taggedUser.setUserId(tag);
            userSvc.addTaggingUser(feedBean, taggedUser);
        });
        hashtagIds.forEach(id -> hashSvc.addFeedHashtag(result, id));
        addFeedMedium(userBean, feedBean, mediaIds);

        return result;
    }

    @Override
    public boolean addFeedback(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into feedback_tbl select null, ?, ?, now() from dual where not exists(" +
                "select null from feedback_tbl where feed_tbl_id = ? and user_tbl_id = ?)";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, feedBean.getId());
            pstmt.setString(2, userBean.getUserId());
            pstmt.setInt(3, feedBean.getId());
            pstmt.setString(4, userBean.getUserId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean addFeedMedia(UserBean userBean, FeedBean feedBean, int mediaId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into feed_media_tbl values (?, ?, now())";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mediaId);
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
    public boolean addFeedMedium(UserBean userBean, FeedBean feedBean, Vector<Integer> mediaIds) {
        boolean flag = true;

        for (int mediaId : mediaIds) {
            flag &= addFeedMedia(userBean, feedBean, mediaId);
        }
        return flag;
    }

    @Override
    public int addMedia(UserBean userBean, MediaBean mediaBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into media_tbl values (null, ?, ?, ?, ?, now())";
        int id = -1;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, userBean.getUserId());
            pstmt.setString(2, mediaBean.getContentType());
            pstmt.setString(3, mediaBean.getContentAddress());
            pstmt.setString(4, mediaBean.getContentCensoredType());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return id;
    }

    @Override
    public Vector<Integer>  addMedium(UserBean userBean, Vector<MediaBean> mediaBeans) {
        Vector<Integer> vec = new Vector<>();

        for (MediaBean bean : mediaBeans) {
            vec.addElement(addMedia(userBean, bean));
        }
        return vec;
    }

    @Override
    public boolean addLike(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into like_tbl select null, ?, ?, now() from dual where not exists(" +
                "select null from like_tbl where feed_tbl_id = ? and user_tbl_id = ?)";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, feedBean.getId());
            pstmt.setString(2, userBean.getUserId());
            pstmt.setInt(3, feedBean.getId());
            pstmt.setString(4, userBean.getUserId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean addUserBlock(UserBean fromUserBean, UserBean toUserBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert ignore into block_tbl values (?, ?, now())";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fromUserBean.getUserId());
            pstmt.setString(2, toUserBean.getUserId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean addUserFollow(UserBean fromUserBean, UserBean toUserBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert ignore into follower_tbl values (?, ?, now())";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fromUserBean.getUserId());
            pstmt.setString(2, toUserBean.getUserId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean exitChatroom(ChatroomBean chatroomBean, UserBean userBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from chat_user_tbl where chatroom_tbl_id = ? and user_tbl_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, chatroomBean.getChatroomId());
            pstmt.setString(2, userBean.getUserId());

            flag = pstmt.executeUpdate() == 1;

            if (flag) {
                Vector<UserBean> users = getChatroomUsers(chatroomBean.getChatroomId());

                if (users.size() == 0) {
                    sql = "update chatroom_tbl set deleted_at = now() where id = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, chatroomBean.getChatroomId());

                    flag = pstmt.executeUpdate() == 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean inviteChatroom(ChatroomBean chatroomBean, UserBean userBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert ignore into chat_user_tbl values (?, ?)";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, chatroomBean.getChatroomId());
            pstmt.setString(2, userBean.getUserId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean isBlocked(UserBean fromBean, UserBean toBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from block_tbl where user_tbl_id = ? and user_tbl_block_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fromBean.getUserId());
            pstmt.setString(2, toBean.getUserId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                flag = rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return flag;
    }

    @Override
    public boolean isCheckedBookmark(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from bookmark_tbl where user_tbl_id = ? and feed_tbl_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userBean.getUserId());
            pstmt.setInt(2, feedBean.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                flag = rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return flag;
    }

    @Override
    public boolean isCheckedFeedback(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from feedback_tbl where user_tbl_id = ? and feed_tbl_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userBean.getUserId());
            pstmt.setInt(2, feedBean.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                flag = rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return flag;
    }

    @Override
    public boolean isCheckedLike(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from like_tbl where user_tbl_id = ? and feed_tbl_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userBean.getUserId());
            pstmt.setInt(2, feedBean.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                flag = rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return flag;
    }

    @Override
    public boolean isFollowed(UserBean fromBean, UserBean toBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from follower_tbl where user_tbl_id = ? and user_tbl_follower_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, toBean.getUserId());
            pstmt.setString(2, fromBean.getUserId());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                flag = rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return flag;
    }

    @Override
    public boolean setProfile(UserBean userBean) {
        return false;
    }

    @Override
    public boolean removeBookmark(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from bookmark_tbl where user_tbl_id = ? and feed_tbl_id = ?";
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
    public boolean removeFeed(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from feed_tbl where id = ? and user_tbl_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, feedBean.getId());
            pstmt.setString(2, userBean.getUserId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean removeComment(UserBean userBean, FeedBean fromBean, FeedBean toBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from feed_comment_tbl where feed_tbl_original_id = ? and feed_tbl_reply_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, fromBean.getId());
            pstmt.setInt(2, toBean.getId());

            flag = pstmt.executeUpdate() == 1;
            flag |= removeFeed(userBean, toBean);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean removeFeedback(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from feedback_tbl where user_tbl_id = ? and feed_tbl_id = ?";
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
    public boolean removeLike(UserBean userBean, FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from like_tbl where user_tbl_id = ? and feed_tbl_id = ?";
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
    public boolean removeUserBlock(UserBean fromUserBean, UserBean toUserBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from block_tbl where user_tbl_id = ? and user_tbl_block_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean removeUserFollow(UserBean fromUserBean, UserBean toUserBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from follower_tbl where user_tbl_id = ? and user_tbl_follower_id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fromUserBean.getUserId());
            pstmt.setString(2, toUserBean.getUserId());

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean uploadHeaderImage(UserBean userBean, String address) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "update user_tbl set header_image = ? where id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address);
            pstmt.setString(2, address);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean uploadProfileImage(UserBean userBean, String address) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "update user_tbl set profile_image = ? where id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address);
            pstmt.setString(2, address);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean updateViewer(FeedBean feedBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "update feed_tbl set viewer = viewer + 1 where id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();

            try {
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, feedBean.getId());

                flag = pstmt.executeUpdate() == 1;

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public int getFollowerCount(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from follower_tbl where user_tbl_follower_id = ?";
        int result = -1;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
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
    public int getFollowingCount(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(*) from follower_tbl where user_tbl_id = ?";
        int result = -1;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
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
    public ChatroomBean createChatroom(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into chatroom_tbl values (null, ?, ?, now(), now(), null)";
        ChatroomBean bean = null;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, "A");

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    bean = getChatroom(id);
                } else {
                    throw new SQLException("Creating chatroom failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return bean;
    }

    @Override
    public ChatroomBean getChatroom(int chatroomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from chatroom_tbl where id = ?";
        ChatroomBean bean = new ChatroomBean();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, chatroomId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = extractChatroomBean(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return bean;
    }

    @Override
    public Vector<UserBean> getFollowerList(UserBean userBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select user_tbl_id from follower_tbl where user_tbl_follower_id = ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userBean.getUserId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserBean bean = new UserBean();
                bean.setUserId(rs.getString(1));
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
    public Vector<UserBean> getFollowingList(UserBean userBean) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select user_tbl_follower_id from follower_tbl where user_tbl_id = ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userBean.getUserId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserBean bean = new UserBean();
                bean.setUserId(rs.getString(1));
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
    public Vector<FeedBean> searchFeed(String includedContent) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_user_view where content like ?";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, '%' + includedContent + '%');
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FeedBean bean = extractFeedBean(rs);

                if (bean.getUserId() != null) {
                    bean.setFeedbackBeans(feedSvc.getFeedbacks(bean));
                    bean.setFeedCommentBeans(feedSvc.getComments(bean));
                    bean.setLikeBeans(feedSvc.getLikes(bean));
                    bean.setMediaBeans(feedSvc.getMedium(bean));
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
    public Vector<HashtagBean> searchHashtag(String includedHashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_tbl where content like ?";
        Vector<HashtagBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
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

    @Override
    public Vector<FeedBean> searchFeedHashtag(String includedHashtag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from feed_user_view where content like ?";
        Vector<FeedBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%#" + includedHashtag + '%');
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FeedBean bean = extractFeedBean(rs);

                if (bean.getUserId() != null) {
                    bean.setFeedbackBeans(feedSvc.getFeedbacks(bean));
                    bean.setFeedCommentBeans(feedSvc.getComments(bean));
                    bean.setLikeBeans(feedSvc.getLikes(bean));
                    bean.setMediaBeans(feedSvc.getMedium(bean));
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
    public Vector<UserBean> searchUser(String includedUserInfo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from user_tbl where id like ? or nickname like ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, '%' + includedUserInfo + '%');
            pstmt.setString(2, '%' + includedUserInfo + '%');
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.addElement(extractUserBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<UserBean> getChatroomUsers(int chatroomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from chat_user_tbl where chatroom_tbl_id = ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, chatroomId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserBean bean = new UserBean();
                String userId = rs.getString("user_tbl_id");
                bean.setUserId(userId);
                beans.addElement(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }
}
