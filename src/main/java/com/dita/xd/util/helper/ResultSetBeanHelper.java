package com.dita.xd.util.helper;

import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ResultSetBeanHelper {
    public static FeedBean extractFeedBean(ResultSet rs) throws SQLException {
        Timestamp beanCreatedAt = rs.getTimestamp("created_at");
        String beanUserId = rs.getString("user_tbl_id");
        String beanContent = rs.getString("content");
        int beanId = rs.getInt("id");
        int beanViewer = rs.getInt("viewer");

        return new FeedBean(beanId, beanUserId, beanContent, beanCreatedAt, beanViewer);
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
