package com.dita.xd.view.panel;

import com.dita.xd.controller.MessageController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.ChatMessageBean;
import com.dita.xd.model.ChatroomBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.dialog.MessageDialog;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

public class ChatroomPanel extends JPanel implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private final MessageController controller;

    private final UserRepository repository;

    private ChatroomBean bean;

    private JButton btnConnect;
    private JButton btnExit;
    private JLabel lblMessage;

    public ChatroomPanel(Locale locale, ChatroomBean bean) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new MessageController();
        repository = UserRepository.getInstance();

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

    private void initialize() {
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel();
        JPanel bottomPane = new JPanel();
        JPanel buttonPane = new JPanel();
        JPanel mainPane = new JPanel();
        JPanel messagePane = new JPanel();
        JPanel subPane = new JPanel();
        JPanel topPane = new JPanel();
        JRoundedImageView rivProfile = new JRoundedImageView();
        ImageIcon icon = new ImageIcon("resources/images/anonymous.jpg");

        btnConnect = new JButton();
        btnExit = new JButton();
        lblMessage = new JLabel();

        bottomPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPane.setLayout(new GridLayout(2, 1));
        mainPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        messagePane.setLayout(new BorderLayout());
        subPane.setLayout(new BoxLayout(subPane, BoxLayout.Y_AXIS));
        topPane.setLayout(new FlowLayout(FlowLayout.LEFT));

        lblMessage.setOpaque(true);
        lblMessage.setBackground(Color.PINK);

        lblTitle.setText(bean.getName());
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD).deriveFont(16f));

        rivProfile.setMaximumSize(new Dimension(60, 60));
        rivProfile.setPreferredSize(new Dimension(60, 60));
        rivProfile.setIcon(icon);

        bottomPane.add(lblMessage);
        buttonPane.add(btnConnect);
        buttonPane.add(btnExit);
        mainPane.add(Box.createHorizontalStrut(4));
        mainPane.add(rivProfile);
        mainPane.add(messagePane);
        messagePane.add(bottomPane, BorderLayout.SOUTH);
        messagePane.add(topPane, BorderLayout.NORTH);
        topPane.add(lblTitle);
        subPane.add(buttonPane);

        loadText();

        add(mainPane, BorderLayout.WEST);
        add(subPane, BorderLayout.EAST);

        btnConnect.addActionListener(e -> {
            MessageDialog dialog = new MessageDialog(currentLocale, null, null, bean, repository.getUserId());
            dialog.setVisible(true);
        });
    }

    private void loadText() {
        Vector<ChatMessageBean> dummy = new Vector<>();
        dummy.addElement(new ChatMessageBean(-1, "No message", -1, null,
                null, null, null, null));

        btnConnect.setText("접속");
        btnExit.setText("나가기");
        lblMessage.setText(Optional.ofNullable(controller.getMessages(bean.getChatroomId()))
                .orElse(dummy).lastElement().getContent());
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        loadText();
    }
}
