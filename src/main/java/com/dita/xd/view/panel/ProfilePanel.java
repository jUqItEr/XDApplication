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

    private JLabel lblIntroduce;
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

        JScrollPane mainPane = new JScrollPane();

        /* 생일 - 가입시각까지 묶기 위한 Panel 선언 */
        JPanel subProfilePane = new JPanel();
        JPanel birthPane = new JPanel();
        JPanel genderPane = new JPanel();
        JPanel linkPane = new JPanel();
        JPanel regionPane = new JPanel();
        JPanel createdAtPane = new JPanel();

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

        lblIntroduce = new JLabel();
        txaIntroduce = new JTextArea();

        btnEditProfile = new JButton();
        btnToFeed = new JButton();
        btnToLike = new JButton();
        btnToBookMark = new JButton();

        mainPane.setLayout(null);
        subProfilePane.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
        subProfilePane.setBounds(30, 340, 390,70);
        btnEditProfile.setBounds(340,10,80,25);

        lblHeaderImg.setBounds(0, 0 , 450, 165);
        lblProfileImg.setBounds(25, 100, 120, 120);

        lblFollower.setBounds(175, 175,50, 25);
        lblFollowing.setBounds(255, 175, 50, 25);
        lblFeed.setBounds(340,175,50,25);
        lblNumOfFollower.setBounds(175,200, 50,25);
        lblNumOfFollowing.setBounds(255,200,50,25);
        lblNumOfFeed.setBounds(340,200,50,25);

        lblNickName.setBounds(30, 225,80,25);
        lblUserId.setBounds(30,250,50,20);

        lblBirth.setBounds(30,340,50,20);
        lblGender.setBounds(160,340,50,20);
        lblLink.setBounds(280,340,80,20);
        lblRegion.setBounds(30,370,80,20);
        lblCreatedAt.setBounds(160,370,80,20);

        lblBirth.setPreferredSize(new Dimension(30,20));
        lblGender.setPreferredSize(new Dimension(30,20));
        lblLink.setPreferredSize(new Dimension(30,20));
        lblRegion.setPreferredSize(new Dimension(30,20));
        lblCreatedAt.setPreferredSize(new Dimension(60,20));

        txaIntroduce.setBounds(30,275,400,60);

        btnToFeed.setBounds(6, 410, 142, 30);
        btnToLike.setBounds(154,410,142,30);
        btnToBookMark.setBounds(302,410,142,30);

        lblHeaderImg.setBackground(Color.RED);
        lblHeaderImg.setOpaque(true);

        lblProfileImg.setBackground(Color.BLUE);
        lblProfileImg.setOpaque(true);

        if (feedPanel != null) {
            feedPanel.setBounds(0,440,450,150);
            feedPanel.setBackground(Color.BLUE);
        }

        loadText();

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

        subProfilePane.add(birthPane);
        subProfilePane.add(genderPane);
        subProfilePane.add(linkPane);
        subProfilePane.add(regionPane);
        subProfilePane.add(createdAtPane);

        mainPane.add(lblProfileImg);
        mainPane.add(btnEditProfile);
        mainPane.add(lblNickName);
        mainPane.add(lblFollower);
        mainPane.add(lblFollowing);
        mainPane.add(lblFeed);
        mainPane.add(lblNumOfFollower);
        mainPane.add(lblNumOfFollowing);
        mainPane.add(lblNumOfFeed);
        mainPane.add(lblHeaderImg);
        mainPane.add(lblUserId);
        mainPane.add(subProfilePane);
        mainPane.add(lblIntroduce);
        mainPane.add(txaIntroduce);
        mainPane.add(btnToFeed);
        mainPane.add(btnToLike);
        mainPane.add(btnToBookMark);

        if (feedPanel != null) {
            mainPane.add(feedPanel);
        }

        this.add(mainPane);


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

        lblIntroduce.setText("자기소개");
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
