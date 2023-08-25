package com.dita.xd.service.implementation;

import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.service.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        String sql = "SELECT id, password, email FROM user_tbl WHERE id = ?";
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

                bean = new UserBean(beanId, beanPwd, beanEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return bean;
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
