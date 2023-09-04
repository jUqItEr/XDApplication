package com.dita.xd.view.panel;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.base.JXdTextPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private JLabel lblFeedComment;
    private JLabel lblFeedBack;
    private JLabel lblFeedLike;
    private JLabel lblViewr;
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

        feedBean = bean ;

        initialize();

        onLocaleChanged(locale);
    }

    private void initialize(){
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        /* Variables declaration */
        JPanel mainPane = new JPanel();

        JPanel profilePane = new JPanel(); /* 피드 좌측  */
        JPanel profileSubPane = new JPanel();

        JPanel boxPane = new JPanel(); /* profilePane을 제외한 Panel 묶기 위해 선언 */
        JPanel userInfoPane = new JPanel();
        JPanel contentPane = new JPanel();
        JPanel mediaPane = new JPanel();

        JPanel feedInfoPane = new JPanel(); /* 피드 하단의 상태를 나타내는 Panel들의 묶음 */
        JPanel feedInfoSubPane = new JPanel();
        JPanel feedInfoCommentPane = new JPanel();
        JPanel feedInfoFeedBackPane = new JPanel();
        JPanel feedInfoLikePane = new JPanel();
        JPanel feedInfoViewerPane = new JPanel();

        lblUserId = new JLabel();
        lblNickName = new JLabel();
        lblCreatedAt = new JLabel();
        lblFeedComment = new JLabel();
        lblFeedBack = new JLabel();
        lblFeedLike = new JLabel();
        lblViewr = new JLabel();

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
        feedInfoPane.setLayout(new BorderLayout());
        feedInfoCommentPane.setLayout(new BorderLayout());
        feedInfoFeedBackPane.setLayout(new BorderLayout());
        feedInfoLikePane.setLayout(new BorderLayout());
        feedInfoViewerPane.setLayout(new BorderLayout());

        profilePane.add(profileSubPane);

        boxPane.add(userInfoPane, BorderLayout.NORTH);
        boxPane.add(contentPane);
        boxPane.add(feedInfoPane, BorderLayout.SOUTH);

        feedInfoPane.add(feedInfoSubPane);

        feedInfoSubPane.add(feedInfoCommentPane);
        feedInfoSubPane.add(feedInfoFeedBackPane);
        feedInfoSubPane.add(feedInfoLikePane);
        feedInfoSubPane.add(feedInfoViewerPane);


        /* 현재 시각과 피드의 작성시각의 차이를 구하기 위해 선언 */
        feedTime = Calendar.getInstance();
        localTime = Calendar.getInstance();
        feedTime.setTime(feedBean.getCreatedAt());

        createdTime = ( /* 피드의 생성 시각과 현재 시각값의 차이를 구하고 ms단위를 s로 조정 */
                localTime.getTimeInMillis() - feedTime.getTimeInMillis()) / 1000;

        loadText();

        nameLength = 12 * lblNickName.getText().length();
        idLength = 12 * lblUserId.getText().length();
        createdLength = 12 * lblCreatedAt.getText().length();

        /* Set the properties of sub panels */
        userInfoPane.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
        feedInfoPane.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        userInfoPane.setPreferredSize(new Dimension(
                nameLength + idLength + createdLength + 10, 20));

        /* Set the properties of components */
        txaFeedContent.setPreferredSize( /* txaFeedContent의 높이는 줄바꿈의 개수만큼 기본 높이에 추가하여 설정 */
                new Dimension(370, 20 * countEnter(txaFeedContent.getText())));
        txaFeedContent.setEditable(false);

        JRoundedImageView rivProfile = new JRoundedImageView();
        ImageIcon icon = new ImageIcon("resources/images/anonymous.jpg");
        rivProfile.setMaximumSize(new Dimension(70, 70));
        rivProfile.setIcon(icon);

        btnProfileImg.add(rivProfile);

        btnProfileImg.setPreferredSize(new Dimension(50, 50));
        btnProfileImg.setMaximumSize(new Dimension(50, 50));
        btnFeedComment.setPreferredSize(new Dimension(20,20));
        btnFeedComment.setMaximumSize(new Dimension(20,20));
        btnFeedBack.setPreferredSize(new Dimension(20,20));
        btnFeedBack.setMaximumSize(new Dimension(20,20));
        btnFeedLike.setPreferredSize(new Dimension(20,20));
        btnFeedLike.setMaximumSize(new Dimension(20,20));
        btnViewer.setPreferredSize(new Dimension(20,20));
        btnViewer.setMaximumSize(new Dimension(20,20));

        lblNickName.setPreferredSize(new Dimension(nameLength,20));
        lblUserId.setPreferredSize(new Dimension(idLength, 20));
        lblCreatedAt.setPreferredSize(new Dimension(createdLength,20));
        lblFeedComment.setPreferredSize(new Dimension( 50,20));
        lblFeedBack.setPreferredSize(new Dimension( 50, 20));
        lblFeedLike.setPreferredSize(new Dimension(50,20));
        lblViewr.setPreferredSize(new Dimension(50,20));

        // Box Vertical Glue
        profileSubPane.add(Box.createRigidArea(new Dimension(20, 0)));
        profileSubPane.add(btnProfileImg);
        profileSubPane.add(Box.createRigidArea(new Dimension(10,0)));

        mainPane.add(Box.createRigidArea(new Dimension(
                0,15)),BorderLayout.NORTH);

        feedInfoCommentPane.add(Box.createRigidArea(new Dimension(10,0)));
        feedInfoFeedBackPane.add(Box.createRigidArea(new Dimension(10, 0)));
        feedInfoLikePane.add(Box.createRigidArea(new Dimension(10, 0)));
        feedInfoViewerPane.add(Box.createRigidArea(new Dimension(10, 0)));

        feedInfoPane.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        feedInfoPane.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
        /* Add components to panel */
        contentPane.add(txaFeedContent);

        userInfoPane.add(lblNickName);
        userInfoPane.add(lblUserId);
        userInfoPane.add(lblCreatedAt);

        feedInfoCommentPane.add(btnFeedComment, BorderLayout.WEST);
        feedInfoCommentPane.add(lblFeedComment, BorderLayout.EAST);
        feedInfoFeedBackPane.add(btnFeedBack, BorderLayout.WEST);
        feedInfoFeedBackPane.add(lblFeedBack, BorderLayout.EAST);
        feedInfoLikePane.add(btnFeedLike, BorderLayout.WEST);
        feedInfoLikePane.add(lblFeedLike, BorderLayout.EAST);
        feedInfoViewerPane.add(btnViewer, BorderLayout.WEST);
        feedInfoViewerPane.add(lblViewr, BorderLayout.EAST);

        mainPane.add(profilePane, BorderLayout.WEST);
        mainPane.add(boxPane);

        this.add(mainPane);
    }

    /* feedBean.getContent() 내의 줄바꿈 문자의 개수를 감지 후 반환 */
    private static int countEnter(String text){
        Pattern pattern = Pattern.compile("\n");
        Matcher matcher = pattern.matcher(text);
        int count = 1;
        while (matcher.find()){
            count++;
        }
        return count;
    }

    private void loadText(){
        lblNickName.setText(feedBean.getUserNickname());
        lblNickName.setFont(lblNickName.getFont().deriveFont(16f).deriveFont(Font.BOLD));
        lblUserId.setText("@" + feedBean.getUserId());
        lblUserId.setForeground(Color.gray);

        lblFeedComment.setText("");
        lblFeedBack.setText("10");
        lblFeedLike.setText("10");
        lblViewr.setText("10");

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
