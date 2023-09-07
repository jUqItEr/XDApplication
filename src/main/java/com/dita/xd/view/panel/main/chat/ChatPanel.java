package com.dita.xd.view.panel.main.chat;

import com.dita.xd.controller.ChatroomController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.dialog.ChatroomDialog;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class ChatPanel extends JPanel implements LocaleChangeListener {
    private static final Dimension PANE_SIZE = new Dimension(0, 88);

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private HashMap<Integer, ChatListPanel> chatroom;
    private final ChatroomController controller;
    private final UserRepository repository;

    private JPanel chatroomPane;

    private JButton btnConnect;

    private Timer alarm;

    public ChatPanel(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        chatroom = new HashMap<>();

        controller = new ChatroomController();
        repository = UserRepository.getInstance();

        chatroomPane = new JPanel();
        chatroomPane.setLayout(new GridLayout(0, 1, 4, 4));

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JButton btnAddChatroom = new JButton(new ImageIcon("resources/icons/chat.png"));
        JButton btnInviteFriends = new JButton(new ImageIcon("resources/icons/invite.png"));
        JButton btnRefresh = new JButton(new ImageIcon("resources/icons/refresh.png"));

        JPanel buttonPane = new JPanel();
        JPanel buttonMainPane = new JPanel();
        JPanel buttonTopPane = new JPanel();
        JPanel mainPane = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPane);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        btnConnect = new JButton();

        buttonPane.setLayout(new BorderLayout());
        buttonMainPane.setLayout(new BoxLayout(buttonMainPane, BoxLayout.Y_AXIS));
        buttonTopPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mainPane.setLayout(new BorderLayout());

        buttonPane.add(btnConnect, BorderLayout.CENTER);
        buttonMainPane.add(Box.createVerticalStrut(20));
        buttonMainPane.add(buttonPane);
        buttonMainPane.add(Box.createVerticalStrut(30));

        buttonTopPane.add(btnRefresh);
        buttonTopPane.add(btnAddChatroom);
        buttonTopPane.add(btnInviteFriends);

        mainPane.add(chatroomPane, BorderLayout.NORTH);
        mainPane.add(Box.createGlue(), BorderLayout.CENTER);

        loadText();

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);

        scrollPane.setVerticalScrollBar(scrollBar);

        add(buttonTopPane, BorderLayout.NORTH);
        add(scrollPane);

        controller.getChatroom(repository.getUserId())
                .forEach(this::appendChatroom);

        /* Alarm control */
        alarm = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (int key : getChatroom().keySet()) {
                    getChatroom().get(key).loadRecentMessage();
                    revalidate();
                    repaint();
                }
            }
        };
        alarm.schedule(task, 0, 5000);

        /* Event listeners */
        btnAddChatroom.addActionListener(e -> {
            ChatroomDialog dialog = new ChatroomDialog(currentLocale);

            dialog.toggleComboBox(false);

            if (dialog.showDialog()) {
                controller.getChatroom(repository.getUserId()).stream()
                        .filter(v -> !chatroom.containsKey(v.getChatroomId()))
                        .forEach(this::appendChatroom);
            }
        });

        btnInviteFriends.addActionListener(e -> {
            ChatroomDialog dialog = new ChatroomDialog(currentLocale);

            dialog.toggleTextField(false);

            if (dialog.showDialog()) {

            }
        });

        btnRefresh.addActionListener(e -> {
            alarm.cancel();

            remove(0);
            chatroom = new HashMap<>();
            chatroomPane = new JPanel();
            chatroomPane.setLayout(new GridLayout(0, 1, 4, 4));
            initialize();
        });
    }

    public HashMap<Integer, ChatListPanel> getChatroom() {
        return chatroom;
    }

    private void appendChatroom(ChatroomBean bean) {
        ChatListPanel pane = new ChatListPanel(currentLocale, bean);
        pane.setPreferredSize(PANE_SIZE);
        chatroomPane.add(pane);
        chatroom.put(bean.getChatroomId(), pane);
        revalidate();
        repaint();
    }

    public void deleteChatListPane(Component chatList) {
        if (chatList instanceof ChatListPanel) {
            ChatListPanel pane = (ChatListPanel) chatList;
            int chatroomId = pane.getBean().getChatroomId();

            chatroom.remove(chatroomId);
            chatroomPane.remove(chatList);
            revalidate();
            repaint();
        }
    }

    private void loadText() {
        btnConnect.setText("채팅방 접속");
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        loadText();
    }
}
