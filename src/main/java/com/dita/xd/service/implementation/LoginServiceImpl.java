package com.dita.xd.service.implementation;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.LoginService;

import java.sql.*;

public class LoginServiceImpl implements LoginService {
    DBConnectionServiceImpl pool = null;
    PasswordHashServiceImpl hashSvc = null;

    public LoginServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
        hashSvc = new PasswordHashServiceImpl();
    }

    @Override
    public UserBean getUser(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user_tbl WHERE id = ?";
        UserBean bean = null;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
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

                bean = new UserBean(beanId, beanPwd, beanEmail, beanNickname, beanProfileImage, beanHeaderImage,
                        beanAddress, beanGender, beanWebsite, beanBirthday, beanIntroduce, beanCreatedAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return bean;
    }

    @Override
    public boolean checkEmail(UserBean bean, String email) {
        return bean != null && bean.getEmail().equals(email);
    }

    @Override
    public UserBean login(String id, String pwd) {
        UserBean bean = getUser(id);

        if (bean != null) {
            String beanPwd = bean.getPassword();

            if (!hashSvc.isValidPassword(pwd, beanPwd)) {
                bean = null;
            }
        }
        return bean;
    }

    @Override
    public boolean logout(UserRepository repository) {
        repository.setLogout();
        return !repository.hasLogin();
    }
}
