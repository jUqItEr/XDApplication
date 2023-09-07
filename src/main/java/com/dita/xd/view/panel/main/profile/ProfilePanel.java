package com.dita.xd.view.panel.main.profile;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.controller.FeedController;
import com.dita.xd.controller.LoginController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.base.JVerticalScrollPane;
import com.dita.xd.view.base.JXdTextPane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProfilePanel extends JPanel implements LocaleChangeListener {
    private static final String[] EN_MONTH = new String[] {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    private static final String[] ES_MONTH = new String[] {
            "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private final ActivityController activityController;
    private final FeedController feedController;
    private final LoginController loginController;
    private final UserRepository repository;

    private UserBean currentUser;

    private JButton btnBookmark;
    private JButton btnFeed;
    private JButton btnLike;
    private JButton btnEdit;
    private JToggleButton btnFollowed;


    private JLabel lblFeedCount;
    private JLabel lblFeedCountTitle;
    private JLabel lblFollowerCount;
    private JLabel lblFollowerCountTitle;
    private JLabel lblFollowingCount;
    private JLabel lblFollowingCountTitle;

    private JLabel lblUserNickname;
    private JLabel lblUserId;

    private JLabel lblBirthday;
    private JLabel lblBirthdayTitle;
    private JLabel lblCreatedAt;
    private JLabel lblCreatedAtTitle;
    private JLabel lblGender;
    private JLabel lblGenderTitle;
    private JLabel lblAddress;
    private JLabel lblAddressTitle;
    private JLabel lblWebsite;
    private JLabel lblWebsiteTitle;

    private JXdTextPane xtpIntroduce;

    protected JPanel mainPane;


    public ProfilePanel(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        activityController = new ActivityController();
        feedController = new FeedController();
        loginController = new LoginController();
        repository = UserRepository.getInstance();

        // Set userBean as login user.
        currentUser = repository.getUserAccount();

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        final ImageIcon icoBirthday = new ImageIcon("resources/icons/birthday.png");
        final ImageIcon icoCalendar = new ImageIcon("resources/icons/calendar.png");
        final ImageIcon icoGender = new ImageIcon("resources/icons/gender.png");
        final ImageIcon icoLink = new ImageIcon("resources/icons/link.png");
        final ImageIcon icoPin = new ImageIcon("resources/icons/pin.png");

        setLayout(new BorderLayout());

        GridLayout gridProfileHeader = new GridLayout(2, 3, 4, 4);
        GridBagConstraints paneGbc = new GridBagConstraints();
        GridBagConstraints profileGbc = new GridBagConstraints();
        GridBagConstraints introGbc = new GridBagConstraints();
        paneGbc.weightx = 1.0;
        paneGbc.gridwidth = GridBagConstraints.REMAINDER;
        paneGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblHeaderImage = new JLabel();

        JLayeredPane headerMainPane = new JLayeredPane();

        JPanel buttonPane = new JPanel();
        JPanel headerBottomPane = new JPanel();        // 내부 프로필
        JPanel headerTopPane = new JPanel();
        JPanel headerLeftPane = new JPanel();
        JPanel headerRightPane = new JPanel();
        JPanel introducePane = new JPanel();
        JPanel profileHeaderPane = new JPanel();
        JPanel userInfoPane = new JPanel();
        JPanel userInfoSubPane = new JPanel();
        JPanel wrapperPane = new JPanel();

        btnBookmark = new JButton();
        btnFeed = new JButton();
        btnLike = new JButton();
        btnEdit = new JButton();
        btnFollowed = new JToggleButton();

        lblFeedCount = new JLabel();
        lblFeedCountTitle = new JLabel();
        lblFollowerCount = new JLabel();
        lblFollowerCountTitle = new JLabel();
        lblFollowingCount = new JLabel();
        lblFollowingCountTitle = new JLabel();

        lblAddress = new JLabel();
        lblBirthday = new JLabel();
        lblCreatedAt = new JLabel();
        lblGender = new JLabel();
        lblWebsite = new JLabel();
        lblAddressTitle = new JLabel(icoPin);
        lblBirthdayTitle = new JLabel(icoBirthday);
        lblCreatedAtTitle = new JLabel(icoCalendar);
        lblGenderTitle = new JLabel(icoGender);
        lblWebsiteTitle = new JLabel(icoLink);

        lblUserId = new JLabel();
        lblUserNickname = new JLabel();

        mainPane = new JPanel();

        xtpIntroduce = new JXdTextPane();

        JRoundedImageView rivProfileImage = new JRoundedImageView();

        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(mainPane));
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();



        buttonPane.setLayout(new GridLayout(1, 3));
        headerMainPane.setLayout(new OverlayLayout(headerMainPane));
        headerBottomPane.setLayout(new GridBagLayout());
        headerTopPane.setLayout(new BorderLayout());
        headerLeftPane.setLayout(new BorderLayout());
        headerRightPane.setLayout(new BoxLayout(headerRightPane, BoxLayout.Y_AXIS));
        introducePane.setLayout(new GridBagLayout());
        profileHeaderPane.setLayout(gridProfileHeader);
        userInfoPane.setLayout(new BoxLayout(userInfoPane, BoxLayout.Y_AXIS));
        userInfoSubPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        wrapperPane.setLayout(new GridBagLayout());

        mainPane.setLayout(new GridBagLayout());

        try {
            String headerUrl = currentUser.getHeaderImage();

            if (headerUrl != null) {
                ImageIcon icon = new ImageIcon(new URL(headerUrl));
                icon = new ImageIcon(icon.getImage()
                        .getScaledInstance(495, 190, Image.SCALE_SMOOTH));
                lblHeaderImage.setIcon(icon);
            } else {
                throw new MalformedURLException("No valid URL");
            }
        } catch (MalformedURLException e) {
            lblHeaderImage.setBackground(Color.DARK_GRAY);
            lblHeaderImage.setOpaque(true);
        }
        try {
            String profileUrl = currentUser.getProfileImage();

            if (profileUrl != null) {
                rivProfileImage.setIcon(new ImageIcon(new URL(profileUrl)));
            } else {
                throw new MalformedURLException("No valid URL");
            }
        } catch (MalformedURLException e) {
            rivProfileImage.setIcon(new ImageIcon("resources/images/anonymous.jpg"));
        }

        lblUserNickname.setFont(lblUserNickname.getFont().deriveFont(22f).deriveFont(Font.BOLD));

        profileHeaderPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        profileHeaderPane.setMaximumSize(new Dimension(200, 60));
        profileHeaderPane.setPreferredSize(new Dimension(200, 60));

        rivProfileImage.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
        rivProfileImage.setMaximumSize(new Dimension(140, 140));
        rivProfileImage.setPreferredSize(new Dimension(140, 140));

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);

        scrollPane.setVerticalScrollBar(scrollBar);

        headerMainPane.setMaximumSize(new Dimension(0, 255));
        headerMainPane.setPreferredSize(new Dimension(0, 255));

        headerBottomPane.setOpaque(false);
        headerLeftPane.setOpaque(false);
        headerRightPane.setOpaque(false);
        profileHeaderPane.setOpaque(false);

        headerRightPane.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        headerRightPane.setMaximumSize(new Dimension(240, 140));
        headerRightPane.setPreferredSize(new Dimension(240, 140));

        lblHeaderImage.setMaximumSize(new Dimension(0, 190));
        lblHeaderImage.setPreferredSize(new Dimension(0, 190));


        lblUserNickname.setMaximumSize(new Dimension(400, 20));
        lblUserNickname.setPreferredSize(new Dimension(400, 20));

        userInfoPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 0));
        userInfoSubPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 35, 15));

        xtpIntroduce.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        xtpIntroduce.setEditable(false);
        xtpIntroduce.setFocusable(false);
        xtpIntroduce.setOpaque(false);

        loadText();

        final JButton[] btns = new JButton[] {
                btnFeed, btnLike, btnBookmark
        };

        for (JButton btn : btns) {
            JPanel tmpBorderPane = new JPanel();
            tmpBorderPane.setLayout(new BorderLayout());
            tmpBorderPane.add(btn);
            buttonPane.add(tmpBorderPane);
        }

        headerMainPane.add(headerBottomPane, 1, 1);
        headerMainPane.add(headerTopPane, 0, 0);
        headerLeftPane.add(rivProfileImage, BorderLayout.SOUTH);
        headerRightPane.add(Box.createVerticalGlue());
        headerRightPane.add(profileHeaderPane);

        profileGbc.ipady = 110;
        profileGbc.weightx = 0.1;
        headerBottomPane.add(headerLeftPane, profileGbc);
        profileGbc.weightx = 0.3;
        headerBottomPane.add(headerRightPane, profileGbc);
        headerTopPane.add(lblHeaderImage, BorderLayout.NORTH);

        profileHeaderPane.add(lblFollowingCountTitle);
        profileHeaderPane.add(lblFollowerCountTitle);
        profileHeaderPane.add(lblFeedCountTitle);
        profileHeaderPane.add(lblFollowingCount);
        profileHeaderPane.add(lblFollowerCount);
        profileHeaderPane.add(lblFeedCount);

        userInfoPane.add(lblUserNickname);
        userInfoPane.add(lblUserId);

        JComponent tmpButton = null;

        // 본인이라면
        if (currentUser.getUserId().equals(repository.getUserId())) {
            // 수정하기 버튼을 추가
            btnEdit.setText("수정");
            tmpButton = btnEdit;
        } else {
            // 팔로우 여부 확인
            if (activityController.isCheckFollowed(currentUser, repository.getUserAccount())) {
                btnFollowed.doClick();
                btnFollowed.setText("팔로우 중");
            } else {
                btnFollowed.setText("팔로우하기");
            }
            tmpButton = btnFollowed;
        }

        String introduce = currentUser.getIntroduce();
        introGbc.weightx = 1.0;
        introGbc.gridwidth = GridBagConstraints.REMAINDER;
        introGbc.fill = GridBagConstraints.HORIZONTAL;

        try {
            if (introduce != null) {
                xtpIntroduce.getStyledDocument().insertString(0, introduce, null);
            }
        } catch (BadLocationException e) {
            System.err.println("문자열 배치 에러");
        }
        introducePane.add(xtpIntroduce, introGbc);

        final JLabel[] labelTitles = new JLabel[] {
                lblAddressTitle, lblWebsiteTitle, lblBirthdayTitle, lblGenderTitle, lblCreatedAtTitle
        };
        final JLabel[] labels = new JLabel[] {
                lblAddress, lblWebsite, lblBirthday, lblGender, lblCreatedAt
        };
        // 자동 추가 구문

        for (int i = 0; i < labels.length; ++i) {
            if (!labels[i].getText().isEmpty()) {
                JPanel boxTmpPane = new JPanel();
                boxTmpPane.setLayout(new BoxLayout(boxTmpPane, BoxLayout.X_AXIS));

                boxTmpPane.add(labelTitles[i]);
                boxTmpPane.add(labels[i]);
                labels[i].setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 22));

                userInfoSubPane.add(boxTmpPane);
            }
        }
        userInfoSubPane.add(Box.createVerticalGlue());
        userInfoSubPane.add(tmpButton);
        userInfoSubPane.setMaximumSize(new Dimension(-1, 80));
        userInfoSubPane.setPreferredSize(new Dimension(-1, 80));

        wrapperPane.add(headerMainPane, paneGbc);
        wrapperPane.add(userInfoPane, paneGbc);
        wrapperPane.add(introducePane, paneGbc);
        wrapperPane.add(userInfoSubPane, paneGbc);
        wrapperPane.add(buttonPane, paneGbc);
        mainPane.add(wrapperPane, paneGbc);

        paneGbc.fill = GridBagConstraints.BOTH;
        paneGbc.weighty = 1.0;

        mainPane.add(new JLabel(), paneGbc);



        add(scrollPane);
    }

    protected void createThumb() {
        GridBagConstraints paneGbc = new GridBagConstraints();
        paneGbc.weightx = 1.0;
        paneGbc.gridwidth = GridBagConstraints.REMAINDER;
        paneGbc.fill = GridBagConstraints.HORIZONTAL;
        JButton thumb = new JButton("Thumb");
        thumb.setPreferredSize(new Dimension(150, 120));
        mainPane.add(thumb, paneGbc);
        revalidate();
        repaint();
    }

    private void clear() {
        remove(0);
    }

    private void loadText() {
        final NumberFormat fmt = NumberFormat.getInstance();
        final LinkedHashMap<String, String> subInfo = createUserSubInfo(currentUser);

        lblFeedCount.setText(fmt.format(feedController.getFeeds(currentUser.getUserId()).size()));
        lblFeedCountTitle.setText("피드수");
        lblFollowerCount.setText(fmt.format(activityController.getFollowerCount(currentUser.getUserId())));
        lblFollowerCountTitle.setText("팔로워");
        lblFollowingCount.setText(fmt.format(activityController.getFollowingCount(currentUser.getUserId())));
        lblFollowingCountTitle.setText("팔로잉");

        lblUserNickname.setText(currentUser.getNickname());
        lblUserId.setText('@' + currentUser.getUserId());

        String address = subInfo.get("address");
        String birthday = subInfo.get("birthday");
        String createdAt = subInfo.get("createdAt");
        String gender = subInfo.get("gender");
        String website = subInfo.get("website");

        if (address != null) {
            lblAddress.setText(address);
        }
        if (birthday != null) {
            lblBirthday.setText(birthday);
        }
        if (createdAt != null) {
            String[] token = createdAt.split("-");
            String lang = localeToTargetString(currentLocale);
            int year = Integer.parseInt(token[0]);
            int month = Integer.parseInt(token[1]);

            switch (lang) {
                case "en" ->
                    lblCreatedAt.setText(String.format(localeBundle
                            .getString("profile.panel.created"), EN_MONTH[month - 1], year));
                case "es" ->
                        lblCreatedAt.setText(String.format(localeBundle
                                .getString("profile.panel.created"), ES_MONTH[month - 1], year));
                default -> {
                    lblCreatedAt.setText(String.format(localeBundle
                            .getString("profile.panel.created"), year, month));
                }
            }
        }
        if (gender != null) {
            switch (gender) {
                case "M" -> {
                    lblGender.setText("남");
                }
                case "F" -> {
                    lblGender.setText("여");
                }
            }
        }
        if (website != null) {
            lblWebsite.setText(website);
        }
    }

    public void setCurrentUser(UserBean currentUser) {
        this.currentUser = loginController.getUser(currentUser.getUserId());
        clear();
        initialize();
        revalidate();
        repaint();
    }

    private LinkedHashMap<String, String> createUserSubInfo(UserBean bean) {
        final LinkedHashMap<String, String> hash = new LinkedHashMap<>();
        final SimpleDateFormat sdfBirthday = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat sdfCreatedAt = new SimpleDateFormat("yyyy-MM");

        Date birthday = bean.getBirthday();
        String fmtString = null;

        if (birthday != null) {
            fmtString = sdfBirthday.format(birthday);
        }
        hash.put("birthday", fmtString);
        hash.put("createdAt", sdfCreatedAt.format(bean.getCreatedAt()));
        hash.put("gender", bean.getGender());
        hash.put("address", bean.getAddress());
        hash.put("website", bean.getWebsite());

        return hash;
    }

    public String localeToTargetString(Locale locale) {
        return locale.getLanguage();
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}

//package com.dita.xd.view.panel.main.profile;
//
//import com.dita.xd.controller.FeedController;
//import com.dita.xd.listener.LocaleChangeListener;
//import com.dita.xd.repository.UserRepository;
//import com.dita.xd.view.panel.main.FeedPanel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//public class ProfilePanel extends JPanel implements LocaleChangeListener{
//    private ResourceBundle localeBundle;
//    private Locale currentLocale;
//    private final UserRepository repository;
//    private final FeedController controller;
//
//
//    private FeedPanel feedPanel;
//
//    private JLabel lblHeaderImg;
//    private JLabel lblProfileImg;
//    private JLabel lblNickName;
//
//    private JLabel lblFollower;
//    private JLabel lblFollowing;
//    private JLabel lblFeed;
//    private JLabel lblNumOfFollower;
//    private JLabel lblNumOfFollowing;
//    private JLabel lblNumOfFeed;
//
//    private JLabel lblUserId;
//    private JLabel lblCreatedAt;
//    private JLabel lblBirth;
//    private JLabel lblGender;
//    private JLabel lblLink;
//    private JLabel lblRegion;
//    private JLabel lblCreatedAtText;
//    private JLabel lblBirthText;
//    private JLabel lblGenderText;
//    private JLabel lblLinkText;
//    private JLabel lblRegionText;
//
//    private JTextArea txaIntroduce;
//
//    private JButton btnEditProfile;
//    private JButton btnToFeed;
//    private JButton btnToLike;
//    private JButton btnToBookMark;
//
//
//    public ProfilePanel(Locale locale){
//        localeBundle = ResourceBundle.getBundle("Language", locale);
//        repository = UserRepository.getInstance();
//        controller = new FeedController();
//
//        //feedPanel = new FeedPanel(locale,controller.getFeeds(repository.getUserId()).firstElement());
//
//        initialize();
//
//        onLocaleChanged(locale);
//    }
//
//    private void initialize(){
//        setLayout(new BorderLayout());
//
//        JScrollPane scrollPane = new JScrollPane();
//
//        JPanel mainPane = new JPanel();
//        JPanel subProfilePane = new JPanel(); /* 생일 - 가입시각까지 묶기 위한 Panel 선언 */
//        JPanel birthPane = new JPanel();
//        JPanel genderPane = new JPanel();
//        JPanel linkPane = new JPanel();
//        JPanel regionPane = new JPanel();
//        JPanel createdAtPane = new JPanel();
//
//        JPanel followerPane = new JPanel();
//        JPanel followingPane = new JPanel();
//        JPanel feedCountPane = new JPanel();
//
//        JPanel headPane = new JPanel();
//        JPanel headSubPane = new JPanel();
//        JPanel userInfoPane = new JPanel();
//        JPanel userInfoSubPane = new JPanel();
//        JPanel btnBoxPane = new JPanel();
//        JPanel feedPane = new JPanel();
//
//        lblHeaderImg = new JLabel();
//        lblProfileImg = new JLabel();
//        lblNickName = new JLabel();
//
//        lblFollower = new JLabel();
//        lblFollowing = new JLabel();
//        lblFeed = new JLabel();
//        lblNumOfFollower = new JLabel();
//        lblNumOfFollowing = new JLabel();
//        lblNumOfFeed = new JLabel();
//
//        lblUserId = new JLabel();
//        lblCreatedAt = new JLabel();
//        lblBirth = new JLabel();
//        lblGender = new JLabel();
//        lblLink = new JLabel();
//        lblRegion = new JLabel();
//        lblCreatedAtText = new JLabel();
//        lblBirthText = new JLabel();
//        lblGenderText = new JLabel();
//        lblLinkText = new JLabel();
//        lblRegionText = new JLabel();
//
//        txaIntroduce = new JTextArea();
//
//        btnEditProfile = new JButton();
//        btnToFeed = new JButton();
//        btnToLike = new JButton();
//        btnToBookMark = new JButton();
//
//        /* 서브 패널 추가 */
//        headPane.add(headSubPane);
//
//        userInfoPane.add(userInfoSubPane);
//
//        subProfilePane.add(birthPane);
//        subProfilePane.add(genderPane);
//        subProfilePane.add(linkPane);
//        subProfilePane.add(regionPane);
//        subProfilePane.add(createdAtPane);
//
//        /* 패널 크기 및 레이아웃 조정 */
//        mainPane.setLayout(new BorderLayout());
//        subProfilePane.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));
//        userInfoPane.setLayout(new BoxLayout(userInfoPane, BoxLayout.Y_AXIS));
//        headPane.setLayout(new BorderLayout());
//        headSubPane.setLayout(new BoxLayout(headSubPane, BoxLayout.X_AXIS));
//        followerPane.setLayout(new BoxLayout(followerPane,BoxLayout.Y_AXIS));
//        followingPane.setLayout(new BoxLayout(followingPane,BoxLayout.Y_AXIS));
//        feedCountPane.setLayout(new BoxLayout(feedCountPane,BoxLayout.Y_AXIS));
//        btnBoxPane.setLayout(new BoxLayout(btnBoxPane,BoxLayout.X_AXIS));
//
//        subProfilePane.setPreferredSize(new Dimension(400, 50));
//        userInfoPane.setPreferredSize(new Dimension(430,100));
//        /* 컴포넌트 크기 및 세부 조정 */
//        lblHeaderImg.setPreferredSize(new Dimension(450, 165));
//        lblHeaderImg.setMaximumSize(new Dimension(450, 165));
//        lblHeaderImg.setBackground(Color.RED);
//        lblHeaderImg.setOpaque(true);
//
//        lblProfileImg.setBackground(Color.BLUE);
//        lblProfileImg.setOpaque(true);
//
//        txaIntroduce.setMaximumSize(new Dimension(400,60));
//
//        lblNickName.setAlignmentX(Component.LEFT_ALIGNMENT);
//        lblUserId.setAlignmentX(Component.LEFT_ALIGNMENT);
//        lblNickName.setHorizontalAlignment(JLabel.LEFT);
//
//        loadText();
//
//        /* 여백 공간 추가 */
//        followerPane.add(lblFollower);
//        followerPane.add(Box.createRigidArea(new Dimension(0, 10)));
//        followerPane.add(lblNumOfFollower);
//
//        followingPane.add(lblFollowing);
//        followingPane.add(Box.createRigidArea(new Dimension(0, 10)));
//        followingPane.add(lblNumOfFollowing);
//
//        feedCountPane.add(lblFeed);
//        feedCountPane.add(Box.createRigidArea(new Dimension(0, 10)));
//        feedCountPane.add(lblNumOfFeed);
//
//        headSubPane.add(Box.createRigidArea(new Dimension(200, 0)));
//        headSubPane.add(followerPane);
//        headSubPane.add(Box.createRigidArea(new Dimension(40, 0)));
//        headSubPane.add(followingPane);
//        headSubPane.add(Box.createRigidArea(new Dimension(40, 0)));
//        headSubPane.add(feedCountPane);
//
//        headPane.add(Box.createRigidArea(new Dimension(0, 10)));
//
//        birthPane.add(lblBirth);
//        birthPane.add(lblBirthText);
//        genderPane.add(lblGender);
//        genderPane.add(lblGenderText);
//        linkPane.add(lblLink);
//        linkPane.add(lblLinkText);
//        regionPane.add(lblRegion);
//        regionPane.add(lblRegionText);
//        createdAtPane.add(lblCreatedAt);
//        createdAtPane.add(lblCreatedAtText);
//
//        headPane.add(lblHeaderImg, BorderLayout.NORTH);
//        headPane.add(headSubPane, BorderLayout.SOUTH);
//
//        userInfoPane.add(lblNickName);
//        userInfoPane.add(lblUserId);
//        userInfoPane.add(txaIntroduce);
//        userInfoPane.add(subProfilePane);
//        userInfoPane.add(btnBoxPane);
//
//        btnBoxPane.add(btnToFeed);
//        btnBoxPane.add(btnToLike);
//        btnBoxPane.add(btnToBookMark);
//
//        mainPane.add(headPane, BorderLayout.NORTH);
//        mainPane.add(Box.createRigidArea(new Dimension(20,0)),BorderLayout.WEST);
//        mainPane.add(userInfoPane);
//        mainPane.add(feedPane, BorderLayout.SOUTH);
//
//        if (feedPanel != null) {
////            scrollPane.add(feedPanel,BorderLayout.SOUTH);
//        }
//
//        //feedPane.add(feedPanel);
//        scrollPane.add(mainPane);
//        scrollPane.add(feedPane);
//        scrollPane.setViewportView(mainPane);
//
//        add(scrollPane);
//
//    }
//
//    private void loadText(){
//        btnEditProfile.setText("수정버튼");
//
//        lblNickName.setText(repository.getUserNickname());
//        lblNickName.setFont(lblNickName.getFont().deriveFont(20f).deriveFont(Font.BOLD));
//        lblFollower.setText("팔로워");
//        lblFollowing.setText("팔로잉");
//        lblFeed.setText("게시글");
//        lblNumOfFollower.setText("1M");
//        lblNumOfFollowing.setText("1B");
//        lblNumOfFeed.setText("1T");
//        lblUserId.setText("@" + repository.getUserId());
//        lblCreatedAt.setText("가입시각");
//        lblBirth.setText("생일");
//        lblGender.setText("성별");
//        lblLink.setText("링크");
//        lblRegion.setText("지역");
//        lblCreatedAtText.setText(repository.getCreatedAt());
//        lblBirthText.setText("8. 30");
//        lblGenderText.setText("남자");
//        lblLinkText.setText("google.co.kr");
//        lblRegionText.setText("미국, LA");
//
//        txaIntroduce.setText("로렘입숨 - 모든 국민은 통신의 비밀을 침해받지 아니한다. 대통령의 임기가 만료되는 " +
//                "때에는 임기만료 70일 내지 40일전에 후임자를 선거한다.");
//
//        btnToFeed.setText("게시글");
//        btnToLike.setText("좋아요");
//        btnToBookMark.setText("북마크");
//    }
//
//    @Override
//    public void onLocaleChanged(Locale newLocale) {
//        currentLocale = newLocale;
//        localeBundle = ResourceBundle.getBundle("language", newLocale);
//        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
////        loadText();
//    }
//}