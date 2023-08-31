package com.dita.xd.service.implementation;

import com.dita.xd.model.UserBean;
import com.dita.xd.service.UserService;
import com.dita.xd.util.helper.ResultSetExtractHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import static com.dita.xd.util.helper.ResultSetExtractHelper.extractUserBean;

public class UserServiceImpl implements UserService {
    private DBConnectionServiceImpl pool = null;

    public UserServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
    }

    @Override
    public UserBean getUser(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user_tbl WHERE id = ?";
        UserBean bean = null;

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = extractUserBean(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return bean;
    }

    @Override
    public Vector<UserBean> search(String includedUserInfo) {
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
}
