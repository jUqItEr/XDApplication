package com.dita.xd.util.helper;

import com.dita.xd.model.ChatMessageBean;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ResultSetExtractHelper {
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
