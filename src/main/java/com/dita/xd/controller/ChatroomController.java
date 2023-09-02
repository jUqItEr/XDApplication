package com.dita.xd.controller;

import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.service.implementation.ChatroomServiceImpl;

import java.util.Vector;

public class ChatroomController {
    private ChatroomServiceImpl svc = null;

    public ChatroomController() {
        svc = new ChatroomServiceImpl();
    }

    public Vector<ChatroomBean> getChatroom(String userId) {
        return svc.getChatroom(userId);
    }

    public Vector<ChatroomBean> getChatroom(UserBean bean) {
        return getChatroom(bean.getUserId());
    }
}
