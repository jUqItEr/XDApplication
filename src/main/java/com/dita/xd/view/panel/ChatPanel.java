package com.dita.xd.view.panel;

import com.dita.xd.controller.ChatroomController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class ChatPanel extends JPanel implements LocaleChangeListener {
    private static final Dimension PANE_SIZE = new Dimension(0, 88);

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private final HashMap<Integer, ChatroomPanel> chatroom;
    private final ChatroomController controller;
    private final UserRepository repository;

    private final JPanel chatroomPane;

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

        JPanel buttonPane = new JPanel();
        JPanel buttonMainPane = new JPanel();
        JPanel mainPane = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPane);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        btnConnect = new JButton();

        buttonPane.setLayout(new BorderLayout());
        buttonMainPane.setLayout(new BoxLayout(buttonMainPane, BoxLayout.Y_AXIS));
        mainPane.setLayout(new BorderLayout());

        buttonPane.add(btnConnect, BorderLayout.CENTER);
        buttonMainPane.add(Box.createVerticalStrut(20));
        buttonMainPane.add(buttonPane);
        buttonMainPane.add(Box.createVerticalStrut(30));
        mainPane.add(chatroomPane, BorderLayout.NORTH);
        mainPane.add(Box.createGlue(), BorderLayout.CENTER);

        loadText();

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);

        scrollPane.setVerticalScrollBar(scrollBar);

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

        revalidate();
        repaint();
    }

    public HashMap<Integer, ChatroomPanel> getChatroom() {
        return chatroom;
    }

    private void appendChatroom(ChatroomBean bean) {
        ChatroomPanel pane = new ChatroomPanel(currentLocale, bean);
        pane.setPreferredSize(PANE_SIZE);
        chatroomPane.add(pane);
        chatroom.put(bean.getChatroomId(), pane);
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
