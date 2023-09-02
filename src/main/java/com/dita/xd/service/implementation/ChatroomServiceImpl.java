package com.dita.xd.service.implementation;

import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.service.ChatroomService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import static com.dita.xd.util.helper.ResultSetExtractHelper.extractChatroomBean;

public class ChatroomServiceImpl implements ChatroomService {
    private final DBConnectionServiceImpl pool;

    public ChatroomServiceImpl() {
        pool = DBConnectionServiceImpl.getInstance();
    }

    @Override
    public Vector<ChatroomBean> getChatroom(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select ct.* from chatroom_tbl ct join chat_user_tbl cut on ct.id = cut.chatroom_tbl_id " +
                "where cut.user_tbl_id = ?";
        Vector<ChatroomBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                beans.addElement(extractChatroomBean(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<ChatroomBean> getChatroom(UserBean bean) {
        return getChatroom(bean.getUserId());
    }

    @Override
    public Vector<UserBean> getUsers(int chatroomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from chatroom_join_view where chatroom_id = ?";
        Vector<UserBean> beans = new Vector<>();

        try {
            conn = pool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, chatroomId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserBean bean = new UserBean();
                String id = rs.getString("id");
                String nickname = rs.getString("nickname");
                String profile_image = rs.getString("profile_image");

                bean.setUserId(id);
                bean.setNickname(nickname);
                bean.setProfileImage(profile_image);

                beans.addElement(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return beans;
    }

    @Override
    public Vector<UserBean> getUsers(ChatroomBean bean) {
        return getUsers(bean.getChatroomId());
    }
}
