package com.dita.xd.util.helper;

import com.dita.xd.model.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ResultSetExtractHelper {
    public static FeedBean extractBookmarkBean(ResultSet rs) throws SQLException {
        Timestamp beanCreatedAt = rs.getTimestamp("created_at");
        String beanUserId = rs.getString("feed_user_id");
        String beanContent = rs.getString("content");
        int beanId = rs.getInt("feed_id");
        int beanViewer = rs.getInt("viewer");

        FeedBean bean = new FeedBean();

        bean.setId(beanId);
        bean.setUserId(beanUserId);
        bean.setContent(beanContent);
        bean.setCreatedAt(beanCreatedAt);
        bean.setViewer(beanViewer);

        return bean;
    }

    public static ChatMessageBean extractChatMessageBean(ResultSet rs) throws SQLException {
        Timestamp beanCreatedAt = rs.getTimestamp("created_at");
        String beanContent = rs.getString("content");
        String beanReadState = rs.getString("read_state");
        String beanUserId = rs.getString("user_id");
        String beanUserNickname = rs.getString("nickname");
        String beanProfileImage = rs.getString("profile_image");
        int beanId = rs.getInt("id");
        int beanChatRoomId = rs.getInt("chatroom_id");

        return new ChatMessageBean(beanId, beanContent, beanChatRoomId, beanUserId,
                beanUserNickname, beanProfileImage, beanCreatedAt, beanReadState);
    }

    public static ChatroomBean extractChatroomBean(ResultSet rs) throws SQLException {
        String createdAt = rs.getString("created_at");
        String deletedAt = rs.getString("deleted_at");
        String name = rs.getString("name");
        String status = rs.getString("status");
        String updatedAt = rs.getString("updated_at");
        int id = rs.getInt("id");

        return new ChatroomBean(id, name, status, createdAt, updatedAt, deletedAt);
    }

    public static FeedBean extractFeedBean(ResultSet rs) throws SQLException {
        FeedBean bean = new FeedBean();
        Timestamp beanCreatedAt = rs.getTimestamp("created_at");
        String beanUserId = rs.getString("user_id");
        String beanContent = rs.getString("content");
        String beanProfileImage = rs.getString("profile_image");
        String beanUserNickname = rs.getString("nickname");
        int beanId = rs.getInt("id");
        int beanViewer = rs.getInt("viewer");

        bean.setId(beanId);
        bean.setContent(beanContent);
        bean.setUserId(beanUserId);
        bean.setCreatedAt(beanCreatedAt);
        bean.setViewer(beanViewer);
        bean.setUserNickname(beanUserNickname);
        bean.setUserProfileImage(beanProfileImage);

        return bean;
    }

    public static UserBean extractFeedbackUserBean(ResultSet rs) throws SQLException {
        UserBean bean = new UserBean();

        String beanId = rs.getString("feedback_user_id");
        String beanNickname = rs.getString("feedback_user_nickname");
        String beanProfileImage = rs.getString("feedback_user_profile_image");

        bean.setUserId(beanId);
        bean.setNickname(beanNickname);
        bean.setProfileImage(beanProfileImage);

        return bean;
    }

    public static UserBean extractLikeUserBean(ResultSet rs) throws SQLException {
        UserBean bean = new UserBean();

        String beanId = rs.getString("like_user_id");
        String beanNickname = rs.getString("like_user_nickname");
        String beanProfileImage = rs.getString("like_user_profile_image");

        bean.setUserId(beanId);
        bean.setNickname(beanNickname);
        bean.setProfileImage(beanProfileImage);

        return bean;
    }

    public static MediaBean extractMediaBean(ResultSet rs) throws SQLException {
        int beanId = rs.getInt("id");
        String beanUserId = rs.getString("user_tbl_id");
        String beanContentType = rs.getString("content_type");
        String beanContentAddress = rs.getString("content_address");
        String beanContentCensored = rs.getString("content_censored_type");
        Timestamp beanCreatedAt = rs.getTimestamp("created_at");
        return new MediaBean(beanId, beanUserId, beanContentType, beanContentAddress, beanContentCensored, beanCreatedAt);
    }

    public static UserBean extractUserBean(ResultSet rs) throws SQLException {
        String beanId = rs.getString("id");
        String beanPwd = rs.getString("password");
        String beanEmail = rs.getString("email");
        String beanNickname = rs.getString("nickname");
        String beanProfileImage = rs.getString("profile_image");
        String beanHeaderImage = rs.getString("header_image");
        String beanAddress = rs.getString("address");
        String beanGender = rs.getString("gender");
        String beanWebsite = rs.getString("website");
        Date beanBirthday = rs.getDate("birthday");
        String beanIntroduce = rs.getString("introduce");
        Timestamp beanCreatedAt = rs.getTimestamp("created_at");

        return new UserBean(beanId, beanPwd, beanEmail, beanNickname,
                beanProfileImage,  beanHeaderImage, beanAddress, beanGender,
                beanWebsite, beanBirthday, beanIntroduce, beanCreatedAt);
    }
}
