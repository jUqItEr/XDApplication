package com.dita.xd.repository;

import com.dita.xd.model.ChatroomBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.util.server.ServerObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class ChatroomRepository {
    private static volatile ChatroomRepository instance = null;

    private final UserRepository repository;

    private final HashMap<String, ServerObject> chatroom;
    private final HashSet<String> selectedUser;

    private ChatroomRepository() {
        chatroom = new HashMap<>();
        selectedUser = new HashSet<>();
        repository = UserRepository.getInstance();
    }

    public static ChatroomRepository getInstance() {
        if (instance == null) {
            synchronized (ChatroomRepository.class) {
                if (instance == null) {
                    instance = new ChatroomRepository();
                }
            }
        }
        return instance;
    }

    public void addUser(ChatroomBean bean, ServerObject obj) {
        chatroom.put(makeKey(bean), obj);
    }

    public ServerObject getUser(ChatroomBean bean) {
        String key = makeKey(bean);
        return chatroom.getOrDefault(key, null);
    }

    public void removeUser(ChatroomBean bean) {
        String key = makeKey(bean);

        chatroom.remove(key);
    }

    public void addUser(UserBean bean) {
        selectedUser.add(bean.getUserId());
    }

    public void removeUser(UserBean bean) {
        selectedUser.remove(bean.getUserId());
    }

    private String makeKey(ChatroomBean bean) {
        return bean.getChatroomId() + '+' + repository.getUserId();
    }
}
