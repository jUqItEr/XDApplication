package com.dita.xd.view.panel;

import com.dita.xd.controller.MessageController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatMessageBean;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.repository.ChatroomRepository;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.util.server.ServerObject;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.dialog.MessageDialog;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

public class ChatroomPanel extends JPanel implements LocaleChangeListener {
    private static final String HOST = "hxlab.co.kr";
    private static final int PORT = 8003;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private final MessageController controller;

    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;

    private ChatroomBean bean;

    private JButton btnConnect;
    private JButton btnExit;
    private JLabel lblMessage;

    private String dummyContent;

    public ChatroomPanel(Locale locale, ChatroomBean bean) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new MessageController();
        chatroomRepository = ChatroomRepository.getInstance();
        userRepository = UserRepository.getInstance();

        this.bean = bean;

        initialize();
        onLocaleChanged(locale);
    }

    public ChatroomBean getBean() {
        return bean;
    }

    public void setBean(ChatroomBean bean) {
        this.bean = bean;
    }

    public void loadRecentMessage() {
        final Vector<ChatMessageBean> dummy = new Vector<>();

        dummy.addElement(new ChatMessageBean(-1, dummyContent, -1, null,
                null, null, null, null));
        lblMessage.setText(Optional.ofNullable(controller.getMessages(bean.getChatroomId()))
                .orElse(dummy).lastElement().getContent());
    }

    private void connect() {
        try {
            Socket sock = new Socket(HOST, PORT);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter outputStream = new PrintWriter(sock.getOutputStream(), true);
            ServerObject serverObject = new ServerObject(sock, inputStream, outputStream);
            chatroomRepository.addUser(bean, serverObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel();
        JPanel bottomPane = new JPanel();
        JPanel buttonPane = new JPanel();
        JPanel mainPane = new JPanel();
        JPanel messagePane = new JPanel();
        JPanel subPane = new JPanel();
        JPanel topPane = new JPanel();
        JRoundedImageView rivProfile = new JRoundedImageView();
        ImageIcon icon = new ImageIcon("resources/images/xd.png");

        btnConnect = new JButton();
        btnExit = new JButton();
        lblMessage = new JLabel();

        bottomPane.setLayout(new BorderLayout());
        buttonPane.setLayout(new GridLayout(2, 1));
        mainPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        messagePane.setLayout(new GridLayout(2, 1));
        subPane.setLayout(new BoxLayout(subPane, BoxLayout.Y_AXIS));
        topPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        lblTitle.setText(bean.getName());
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD).deriveFont(16f));

        rivProfile.setMaximumSize(new Dimension(60, 60));
        rivProfile.setPreferredSize(new Dimension(60, 60));
        rivProfile.setIcon(icon);

        bottomPane.add(lblMessage);
        buttonPane.add(btnConnect);
        buttonPane.add(btnExit);
        mainPane.add(rivProfile);
        mainPane.add(Box.createHorizontalStrut(1));
        messagePane.add(topPane);
        messagePane.add(bottomPane);
        topPane.add(lblTitle);
        subPane.add(buttonPane);

        loadText();

        add(mainPane, BorderLayout.WEST);
        add(messagePane);
        add(subPane, BorderLayout.EAST);

        btnConnect.addActionListener(e -> {
            if (chatroomRepository.getUser(bean) == null) {
                connect();

                MessageDialog dialog =
                        new MessageDialog(currentLocale, bean, userRepository.getUserId());
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "이미 접속 중입니다.",
                        localeBundle.getString("dialog.plain.title.warning"),
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        btnExit.addActionListener(e -> {
            if (chatroomRepository.getUser(bean) == null) {

            } else {
                JOptionPane.showMessageDialog(null,
                        "채팅방을 먼저 나가십시오.",
                        localeBundle.getString("dialog.plain.title.error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void loadText() {
        dummyContent = "No message";

        btnConnect.setText("접속");
        btnExit.setText("나가기");

        loadRecentMessage();
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        loadText();
    }
}
