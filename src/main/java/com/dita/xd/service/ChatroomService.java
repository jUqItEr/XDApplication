package com.dita.xd.service;

import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.UserBean;

import java.util.Vector;

public interface ChatroomService extends Service {
    Vector<ChatroomBean> getChatroom(String userId);
    Vector<ChatroomBean> getChatroom(UserBean bean);
    Vector<UserBean> getUsers(int chatroomId);
    Vector<UserBean> getUsers(ChatroomBean bean);
}
