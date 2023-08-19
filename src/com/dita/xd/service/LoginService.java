package com.dita.xd.service;

import com.dita.xd.manager.DatabaseConnectionMgr;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.implementation.LoginServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService implements LoginServiceImpl {
    DatabaseConnectionMgr pool = null;
    PasswordHashService hashSvc = null;

    public LoginService() {
        pool = DatabaseConnectionMgr.getInstance();
        hashSvc = new PasswordHashService();
    }

    @Override
    public UserBean login(String id, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        UserBean bean = null;

        try {
            conn = pool.getConnection();
            sql = "SELECT * FROM user_tbl WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String beanId = rs.getString("id");
                String beanPwd = rs.getString("password");
                String beanEmail = rs.getString("email");

                if (hashSvc.isValidPassword(pwd, beanPwd)) {
                    bean = new UserBean(beanId, beanPwd, beanEmail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return bean;
    }

    @Override
    public boolean logout(UserRepository repository) {
        repository.setLogout();
        return !repository.hasLogin();
    }
}
