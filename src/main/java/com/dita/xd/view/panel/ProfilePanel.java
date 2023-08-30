package com.dita.xd.view.panel;

import com.dita.xd.repository.UserRepository;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfilePanel extends JPanel {
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private final UserRepository repository;


    public ProfilePanel(Locale locale){
        localeBundle = ResourceBundle.getBundle("Language", locale);
        repository = UserRepository.getInstance();

        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout());

        JScrollPane pnlMain = new JScrollPane();

        JPanel pnlSubProfile = new JPanel();
        JPanel pnlBirth = new JPanel();
        JPanel pnlGender = new JPanel();
        JPanel pnlLink = new JPanel();
        JPanel pnlRegion = new JPanel();
        JPanel pnlCreatedAt = new JPanel();

        JLabel lblHeaderImg = new JLabel();
        JLabel lblProfileImg = new JLabel();
        JLabel lblNickName = new JLabel();

        JLabel lblFollower = new JLabel();
        JLabel lblFollowing = new JLabel();
        JLabel lblFeed = new JLabel();
        JLabel lblNumOfFollower = new JLabel();
        JLabel lblNumOfFollowing = new JLabel();
        JLabel lblNumOfFeed = new JLabel();

        JLabel lblUserId = new JLabel();
        JLabel lblCreatedAt = new JLabel();
        JLabel lblBirth = new JLabel();
        JLabel lblGender = new JLabel();
        JLabel lblLink = new JLabel();
        JLabel lblRegion = new JLabel();
        JLabel lblCreatedAtText = new JLabel();
        JLabel lblBirthText = new JLabel();
        JLabel lblGenderText = new JLabel();
        JLabel lblLinkText = new JLabel();
        JLabel lblRegionText = new JLabel();

        JLabel lblIntroduce = new JLabel();
        JTextArea txaIntroduce = new JTextArea();

        JButton btnEditProfile = new JButton();
        JButton btnToFeed = new JButton();
        JButton btnToLike = new JButton();
        JButton btnToBookMark = new JButton();

        pnlMain.setLayout(null);
        pnlSubProfile.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
        pnlSubProfile.setBounds(30, 340, 390,70);
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

        btnToFeed.setBounds(6, 505, 142, 30);
        btnToLike.setBounds(154,505,142,30);
        btnToBookMark.setBounds(302,505,142,30);

        lblHeaderImg.setBackground(Color.RED);
        lblHeaderImg.setOpaque(true);

        lblProfileImg.setBackground(Color.BLUE);
        lblProfileImg.setOpaque(true);

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
        txaIntroduce.setText("이게 그 자기소개 글이라는 건데 생각해보니까 그냥 글만 넣으면 되지 위에 뭔가" +
                "달아 놓을 필요는 없네?");

        btnToFeed.setText("게시글");
        btnToLike.setText("좋아요");
        btnToBookMark.setText("북마크");

        pnlBirth.add(lblBirth);
        pnlBirth.add(lblBirthText);
        pnlGender.add(lblGender);
        pnlGender.add(lblGenderText);
        pnlLink.add(lblLink);
        pnlLink.add(lblLinkText);
        pnlRegion.add(lblRegion);
        pnlRegion.add(lblRegionText);
        pnlCreatedAt.add(lblCreatedAt);
        pnlCreatedAt.add(lblCreatedAtText);

        pnlSubProfile.add(pnlBirth);
        pnlSubProfile.add(pnlGender);
        pnlSubProfile.add(pnlLink);
        pnlSubProfile.add(pnlRegion);
        pnlSubProfile.add(pnlCreatedAt);

        pnlMain.add(lblProfileImg);
        pnlMain.add(btnEditProfile);
        pnlMain.add(lblNickName);
        pnlMain.add(lblFollower);
        pnlMain.add(lblFollowing);
        pnlMain.add(lblFeed);
        pnlMain.add(lblNumOfFollower);
        pnlMain.add(lblNumOfFollowing);
        pnlMain.add(lblNumOfFeed);
        pnlMain.add(lblHeaderImg);
        pnlMain.add(lblUserId);
        pnlMain.add(pnlSubProfile);
        pnlMain.add(lblIntroduce);
        pnlMain.add(txaIntroduce);
        pnlMain.add(btnToFeed);
        pnlMain.add(btnToLike);
        pnlMain.add(btnToBookMark);

        this.add(pnlMain);


    }

}
