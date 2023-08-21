package xd.service.implementation;

import xd.manager.DatabaseConnectionMgr;
import xd.model.UserBean;
import xd.repository.UserRepository;
import xd.service.LoginService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServiceImpl implements LoginService {
    DatabaseConnectionMgr pool = null;
    PasswordHashServiceImpl hashSvc = null;

    public LoginServiceImpl() {
        pool = DatabaseConnectionMgr.getInstance();
        hashSvc = new PasswordHashServiceImpl();
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