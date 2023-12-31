package com.dita.xd.view.panel.main.chat;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.controller.MessageController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatMessageBean;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.repository.ChatroomRepository;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.util.server.ServerObject;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.frame.MessageFrame;
import com.dita.xd.view.manager.MainLayoutMgr;

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

public class ChatListPanel extends JPanel implements LocaleChangeListener {
    private static final String HOST = "113.198.238.107";
    private static final int PORT = 8003;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private final ActivityController activityController;
    private final MessageController messageController;

    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;

    private final MainLayoutMgr mgr;

    private ChatroomBean bean;

    private JButton btnConnect;
    private JButton btnExit;
    private JLabel lblMessage;

    private String dummyContent;
    private String questionMessage;
    private String errorMessage1;
    private String errorMessage2;

    public ChatListPanel(Locale locale, ChatroomBean bean) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        activityController = new ActivityController();
        messageController = new MessageController();
        chatroomRepository = ChatroomRepository.getInstance();
        userRepository = UserRepository.getInstance();

        mgr = MainLayoutMgr.getInstance();

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
        lblMessage.setText(Optional.ofNullable(messageController.getMessages(bean.getChatroomId()))
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

                MessageFrame dialog =
                        new MessageFrame(currentLocale, bean, userRepository.getUserId());
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        errorMessage1,
                        localeBundle.getString("dialog.plain.title.warning"),
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        btnExit.addActionListener(e -> {
            if (chatroomRepository.getUser(bean) == null) {
                int result = JOptionPane.showConfirmDialog(getParent(),
                        questionMessage, "", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    activityController.exitChatroom(bean, userRepository.getUserAccount());
                    mgr.disposeChatListPanel(this);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        errorMessage2,
                        localeBundle.getString("dialog.plain.title.error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void loadText() {
        dummyContent = localeBundle.getString("chat.panel.dummy.text");

        btnConnect.setText(localeBundle.getString("chat.panel.button.connect"));
        btnExit.setText(localeBundle.getString("chat.panel.button.exit"));

        errorMessage1 = localeBundle.getString("chat.message.error1");
        errorMessage2 = localeBundle.getString("chat.message.error2");
        questionMessage = localeBundle.getString("chat.message.question");

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
