package com.dita.xd.controller;

import com.dita.xd.model.ChatMessageBean;
import com.dita.xd.service.implementation.MessageServiceImpl;

import java.util.Vector;

public class MessageController {
    private final MessageServiceImpl svc;

    public MessageController() {
        svc = new MessageServiceImpl();
    }
    public boolean appendMessage(ChatMessageBean bean) {
        return svc.appendMessage(bean);
    }

    public ChatMessageBean getMessage(int recordId) {
        return svc.getMessage(recordId);
    }

    public Vector<ChatMessageBean> getMessages(int chatroomId) {
        Vector<ChatMessageBean> beans = svc.getMessages(chatroomId);

        return beans.size() > 0 ? beans : null;
    }

    public Vector<ChatMessageBean> getMessages(int chatroomId, String userId) {
        return svc.getMessages(chatroomId, userId);
    }

    public Vector<ChatMessageBean> getMessages(int chatroomId, String userId, String targetAt) {
        return svc.getMessages(chatroomId, userId, targetAt);
    }
}
