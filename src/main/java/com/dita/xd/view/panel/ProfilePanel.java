package com.dita.xd.view.panel;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.repository.UserRepository;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfilePanel extends JPanel implements LocaleChangeListener{
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private final UserRepository repository;
    private final FeedController controller;


    private FeedPanel feedPanel;

    private JLabel lblHeaderImg;
    private JLabel lblProfileImg;
    private JLabel lblNickName;

    private JLabel lblFollower;
    private JLabel lblFollowing;
    private JLabel lblFeed;
    private JLabel lblNumOfFollower;
    private JLabel lblNumOfFollowing;
    private JLabel lblNumOfFeed;

    private JLabel lblUserId;
    private JLabel lblCreatedAt;
    private JLabel lblBirth;
    private JLabel lblGender;
    private JLabel lblLink;
    private JLabel lblRegion;
    private JLabel lblCreatedAtText;
    private JLabel lblBirthText;
    private JLabel lblGenderText;
    private JLabel lblLinkText;
    private JLabel lblRegionText;

    private JTextArea txaIntroduce;

    private JButton btnEditProfile;
    private JButton btnToFeed;
    private JButton btnToLike;
    private JButton btnToBookMark;


    public ProfilePanel(Locale locale){
        localeBundle = ResourceBundle.getBundle("Language", locale);
        repository = UserRepository.getInstance();
        controller = new FeedController();

        //feedPanel = new FeedPanel(locale,controller.getFeeds(repository.getUserId()).firstElement());

        initialize();

        onLocaleChanged(locale);
    }

    private void initialize(){
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();

        JPanel mainPane = new JPanel();
        JPanel subProfilePane = new JPanel(); /* 생일 - 가입시각까지 묶기 위한 Panel 선언 */
        JPanel birthPane = new JPanel();
        JPanel genderPane = new JPanel();
        JPanel linkPane = new JPanel();
        JPanel regionPane = new JPanel();
        JPanel createdAtPane = new JPanel();

        JPanel followerPane = new JPanel();
        JPanel followingPane = new JPanel();
        JPanel feedCountPane = new JPanel();

        JPanel headPane = new JPanel();
        JPanel headSubPane = new JPanel();
        JPanel userInfoPane = new JPanel();
        JPanel userInfoSubPane = new JPanel();
        JPanel btnBoxPane = new JPanel();
        JPanel feedPane = new JPanel();

        lblHeaderImg = new JLabel();
        lblProfileImg = new JLabel();
        lblNickName = new JLabel();

        lblFollower = new JLabel();
        lblFollowing = new JLabel();
        lblFeed = new JLabel();
        lblNumOfFollower = new JLabel();
        lblNumOfFollowing = new JLabel();
        lblNumOfFeed = new JLabel();

        lblUserId = new JLabel();
        lblCreatedAt = new JLabel();
        lblBirth = new JLabel();
        lblGender = new JLabel();
        lblLink = new JLabel();
        lblRegion = new JLabel();
        lblCreatedAtText = new JLabel();
        lblBirthText = new JLabel();
        lblGenderText = new JLabel();
        lblLinkText = new JLabel();
        lblRegionText = new JLabel();

        txaIntroduce = new JTextArea();

        btnEditProfile = new JButton();
        btnToFeed = new JButton();
        btnToLike = new JButton();
        btnToBookMark = new JButton();

        /* 서브 패널 추가 */
        headPane.add(headSubPane);

        userInfoPane.add(userInfoSubPane);

        subProfilePane.add(birthPane);
        subProfilePane.add(genderPane);
        subProfilePane.add(linkPane);
        subProfilePane.add(regionPane);
        subProfilePane.add(createdAtPane);

        /* 패널 크기 및 레이아웃 조정 */
        mainPane.setLayout(new BorderLayout());
        subProfilePane.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
        userInfoPane.setLayout(new BoxLayout(userInfoPane, BoxLayout.Y_AXIS));
        headPane.setLayout(new BorderLayout());
        headSubPane.setLayout(new BoxLayout(headSubPane, BoxLayout.X_AXIS));
        followerPane.setLayout(new BoxLayout(followerPane,BoxLayout.Y_AXIS));
        followingPane.setLayout(new BoxLayout(followingPane,BoxLayout.Y_AXIS));
        feedCountPane.setLayout(new BoxLayout(feedCountPane,BoxLayout.Y_AXIS));
        btnBoxPane.setLayout(new BoxLayout(btnBoxPane,BoxLayout.X_AXIS));

        subProfilePane.setPreferredSize(new Dimension(400, 50));
        userInfoPane.setPreferredSize(new Dimension(430,100));
        /* 컴포넌트 크기 및 세부 조정 */
        lblHeaderImg.setPreferredSize(new Dimension(450, 165));
        lblHeaderImg.setMaximumSize(new Dimension(450, 165));
        lblHeaderImg.setBackground(Color.RED);
        lblHeaderImg.setOpaque(true);

        lblProfileImg.setBackground(Color.BLUE);
        lblProfileImg.setOpaque(true);

        txaIntroduce.setMaximumSize(new Dimension(400,60));

        lblNickName.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblUserId.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNickName.setHorizontalAlignment(JLabel.LEFT);

        loadText();

        /* 여백 공간 추가 */
        followerPane.add(lblFollower);
        followerPane.add(Box.createRigidArea(new Dimension(0, 10)));
        followerPane.add(lblNumOfFollower);

        followingPane.add(lblFollowing);
        followingPane.add(Box.createRigidArea(new Dimension(0, 10)));
        followingPane.add(lblNumOfFollowing);

        feedCountPane.add(lblFeed);
        feedCountPane.add(Box.createRigidArea(new Dimension(0, 10)));
        feedCountPane.add(lblNumOfFeed);

        headSubPane.add(Box.createRigidArea(new Dimension(200, 0)));
        headSubPane.add(followerPane);
        headSubPane.add(Box.createRigidArea(new Dimension(40, 0)));
        headSubPane.add(followingPane);
        headSubPane.add(Box.createRigidArea(new Dimension(40, 0)));
        headSubPane.add(feedCountPane);

        headPane.add(Box.createRigidArea(new Dimension(0, 10)));

        birthPane.add(lblBirth);
        birthPane.add(lblBirthText);
        genderPane.add(lblGender);
        genderPane.add(lblGenderText);
        linkPane.add(lblLink);
        linkPane.add(lblLinkText);
        regionPane.add(lblRegion);
        regionPane.add(lblRegionText);
        createdAtPane.add(lblCreatedAt);
        createdAtPane.add(lblCreatedAtText);

        headPane.add(lblHeaderImg, BorderLayout.NORTH);
        headPane.add(headSubPane, BorderLayout.SOUTH);

        userInfoPane.add(lblNickName);
        userInfoPane.add(lblUserId);
        userInfoPane.add(txaIntroduce);
        userInfoPane.add(subProfilePane);
        userInfoPane.add(btnBoxPane);

        btnBoxPane.add(btnToFeed);
        btnBoxPane.add(btnToLike);
        btnBoxPane.add(btnToBookMark);

        mainPane.add(headPane, BorderLayout.NORTH);
        mainPane.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.WEST);
        mainPane.add(userInfoPane);
        mainPane.add(feedPane, BorderLayout.SOUTH);

        if (feedPanel != null) {
//            scrollPane.add(feedPanel,BorderLayout.SOUTH);
        }

        //feedPane.add(feedPanel);
        scrollPane.add(mainPane);
        scrollPane.add(feedPane);
        scrollPane.setViewportView(mainPane);

        add(scrollPane);

    }

    private void loadText(){
        btnEditProfile.setText("수정버튼");

        lblNickName.setText(repository.getUserNickname());
        lblNickName.setFont(lblNickName.getFont().deriveFont(20f).deriveFont(Font.BOLD));
        lblFollower.setText("팔로워");
        lblFollowing.setText("팔로잉");
        lblFeed.setText("게시글");
        lblNumOfFollower.setText("1M");
        lblNumOfFollowing.setText("1B");
        lblNumOfFeed.setText("1T");
        lblUserId.setText("@" + repository.getUserId());
        lblCreatedAt.setText("가입시각");
        lblBirth.setText("생일");
        lblGender.setText("성별");
        lblLink.setText("링크");
        lblRegion.setText("지역");
        lblCreatedAtText.setText(repository.getCreatedAt());
        lblBirthText.setText("8. 30");
        lblGenderText.setText("남자");
        lblLinkText.setText("google.co.kr");
        lblRegionText.setText("미국, LA");

        txaIntroduce.setText("로렘입숨 - 모든 국민은 통신의 비밀을 침해받지 아니한다. 대통령의 임기가 만료되는 " +
                "때에는 임기만료 70일 내지 40일전에 후임자를 선거한다.");

        btnToFeed.setText("게시글");
        btnToLike.setText("좋아요");
        btnToBookMark.setText("북마크");
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
//        loadText();
    }
}
