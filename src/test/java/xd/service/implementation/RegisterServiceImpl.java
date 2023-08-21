package xd.service.implementation;

import xd.manager.DatabaseConnectionMgr;
import xd.service.RegisterService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RegisterServiceImpl implements RegisterService {
    DatabaseConnectionMgr pool = null;

    public RegisterServiceImpl() {
        pool = DatabaseConnectionMgr.getInstance();
    }

    @Override
    public boolean register(String id, String pwd, String email, String nickname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        boolean flag = false;

        try {
            conn = pool.getConnection();
            sql = "INSERT INTO user_tbl VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            pstmt.setString(3, email);

            flag = pstmt.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt);
        }
        if (flag) {
            try {
                Timestamp stampNow = Timestamp.valueOf(LocalDateTime.now());
                conn = pool.getConnection();
                sql = "INSERT INTO profile_tbl VALUES (?, ?, null, null, null, null, null, null, null, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, nickname);
                pstmt.setTimestamp(3, stampNow);
                flag = pstmt.executeUpdate() == 1;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pool.freeConnection(conn, pstmt);
            }
        }
        return flag;
    }
}
