package com.dita.xd.service.implementation;

import com.dita.xd.service.PasswordHashService;
import com.dita.xd.service.RegisterService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterServiceImpl implements RegisterService {
    private DBConnectionServiceImpl pool = null;
    private PasswordHashService svc = null;

    public RegisterServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
        svc = new PasswordHashServiceImpl();
    }

    @Override
    public boolean hasEmail(String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(email) FROM user_tbl WHERE email = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            flag = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return flag;
    }

    @Override
    public boolean hasId(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(id) FROM user_tbl WHERE id = ?";
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            /*
             * If the 'flag' has true, the user id that wants to register is already exists on database.
             * */
            flag = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return flag;
    }   // -- End of function (hasId)

    @Override
    public boolean changePassword(String id, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE user_tbl SET password = ? WHERE id = ?";
        String securedPwd = svc.generatePassword(pwd);
        boolean flag = false;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, securedPwd);
            pstmt.setString(2, id);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }

    @Override
    public boolean register(String id, String pwd, String email) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;
        String securedPwd = svc.generatePassword(pwd);

        try {
            conn = pool.getConnection();
            sql = "INSERT INTO user_tbl (id, password, email, created_at) VALUES (?, ?, ?, NOW())";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, securedPwd);
            pstmt.setString(3, email);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        return flag;
    }
}
