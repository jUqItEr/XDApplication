package com.dita.xd.view.panel;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JXdTextPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class FeedPanel extends JPanel implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;
    private final FeedController controller;
    private final UserRepository repository;

    private FeedBean feedBean;

    private JButton btnProfileImg;

    private JLabel lblUserId;
    private JLabel lblNickName;
    private JLabel lblCreatedAt;

    private JXdTextPane txaFeedContent;

    private JButton btnFeedComment;
    private JButton btnFeedBack;
    private JButton btnFeedLike;
    private JButton btnViewer;
    private int nameLength;
    private int idLength;
    private int createdLength;
    private long createdTime;
    private Calendar localTime;
    private Calendar feedTime;

//    private JComboBox cbxTemp;

    public FeedPanel(Locale locale, FeedBean bean){
        localeBundle = ResourceBundle.getBundle("language", locale);
        repository = UserRepository.getInstance();

        controller = new FeedController();

        feedBean = bean;

        initialize();

        onLocaleChanged(locale);
    }

    private void initialize(){
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        /* Variables declaration */
        JPanel mainPane = new JPanel();

        JPanel profilePane = new JPanel();
        JPanel profileSubPane = new JPanel();
        JPanel boxPane = new JPanel(); /* profilePane을 제외한 Panel 묶기 위해 선언 */
        JPanel userInfoPane = new JPanel();
        JPanel contentPane = new JPanel();
        JPanel mediaPane = new JPanel();
        JPanel feedInfoPane = new JPanel();

        lblUserId = new JLabel();
        lblNickName = new JLabel();
        lblCreatedAt = new JLabel();

        btnProfileImg = new JButton();
        btnFeedComment = new JButton();
        btnFeedBack = new JButton();
        btnFeedLike = new JButton();
        btnViewer = new JButton();

        txaFeedContent = new JXdTextPane();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BorderLayout());
        profilePane.setLayout(new BoxLayout(profilePane, BoxLayout.Y_AXIS));
        profileSubPane.setLayout(new BoxLayout(profileSubPane, BoxLayout.X_AXIS));
        boxPane.setLayout(new BorderLayout());
        userInfoPane.setLayout(new BorderLayout());
        feedInfoPane.setLayout(new BorderLayout());

        boxPane.add(userInfoPane, BorderLayout.NORTH);
        boxPane.add(contentPane);

        boxPane.add(feedInfoPane, BorderLayout.SOUTH);

        feedTime = Calendar.getInstance();
        localTime = Calendar.getInstance();
        feedTime.setTime(feedBean.getCreatedAt());

        /* 피드의 생성 시각과 현재 시각값의 차이를 구하고 ms단위를 s로 조정 */
        createdTime = (
                localTime.getTimeInMillis() - feedTime.getTimeInMillis()) / 1000;

        loadText();

        nameLength = 12 * lblNickName.getText().length();
        idLength = 12 * lblUserId.getText().length();
        createdLength = 12 * lblCreatedAt.getText().length();

        /* Set the properties of sub panels */
        mainPane.setPreferredSize(new Dimension(450, 150));
        userInfoPane.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
        feedInfoPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        userInfoPane.setPreferredSize(new Dimension(
                nameLength + idLength + createdLength + 10, 20));

        /* Set the properties of components */
        btnProfileImg.setPreferredSize(new Dimension(50, 50));
        btnProfileImg.setMaximumSize(new Dimension(50, 50));
        txaFeedContent.setBounds(80, 50, feedBean.getContent().length()*20
                ,feedBean.getContent().length() <= 30 ?
                20 : feedBean.getContent().length() / 30 * 20);
        txaFeedContent.setEditable(false);

        lblNickName.setPreferredSize(new Dimension(nameLength,20));
        lblUserId.setPreferredSize(new Dimension(idLength, 20));
        lblCreatedAt.setPreferredSize(new Dimension(createdLength,20));

        // Box Vertical Glue
        profileSubPane.add(Box.createRigidArea(new Dimension(10, 0)));
        profileSubPane.add(btnProfileImg);
        profileSubPane.add(Box.createRigidArea(new Dimension(10,0)));

        mainPane.add(Box.createRigidArea(new Dimension(
                0,10)),BorderLayout.NORTH);

        /* Add components to panel */
        contentPane.add(txaFeedContent);

        userInfoPane.add(lblNickName);
        userInfoPane.add(lblUserId);
        userInfoPane.add(lblCreatedAt);

        profilePane.add(profileSubPane);

        mainPane.add(profilePane, BorderLayout.WEST);
        mainPane.add(boxPane);

        this.add(mainPane);
    }

    private void loadText(){
        lblNickName.setText(feedBean.getUserNickname());
        lblNickName.setFont(lblNickName.getFont().deriveFont(16f).deriveFont(Font.BOLD));
        lblUserId.setText("@" + feedBean.getUserId());
        lblUserId.setForeground(Color.gray);

        if(createdTime < 60) {
            lblCreatedAt.setText(createdTime + "초 전");
        } else if (createdTime < 60 * 60){
            lblCreatedAt.setText(createdTime / 60 + "분 전");
        } else if (createdTime < 24 * 60 * 60){
            lblCreatedAt.setText(createdTime / 3600 + "시간 전");

        } else if (createdTime < 7 * 24 * 60 * 60){
            lblCreatedAt.setText(createdTime / 86400 + "일 전");
        } else {
            LocalDate localDate = feedBean.getCreatedAt().toLocalDateTime().toLocalDate();
            lblCreatedAt.setText(localDate.toString());
        }

        txaFeedContent.setText("");

        try {
            txaFeedContent.getStyledDocument().insertString(0, feedBean.getContent(), null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
