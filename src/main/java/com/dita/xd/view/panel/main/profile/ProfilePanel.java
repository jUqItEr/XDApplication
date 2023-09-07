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
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.font.TextAttribute;
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
        JPanel userStatePane = new JPanel();
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
        userStatePane.setLayout(new GridLayout(1, 3));
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
        lblUserId.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 22));

        JComponent tmpButton;

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
        if (activityController.isCheckFollowed(repository.getUserAccount(), currentUser)) {
            JPanel t = new JPanel(new BorderLayout());
            t.add(new JLabel("나 니 좋아한다"));
            userStatePane.add(t);
        }
        JPanel t = new JPanel(new BorderLayout());
        t.add(tmpButton);
        userStatePane.add(t);

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
        userInfoSubPane.setMaximumSize(new Dimension(-1, 80));
        userInfoSubPane.setPreferredSize(new Dimension(-1, 80));


        wrapperPane.add(headerMainPane, paneGbc);
        wrapperPane.add(userInfoPane, paneGbc);
        wrapperPane.add(introducePane, paneGbc);
        wrapperPane.add(userInfoSubPane, paneGbc);
        wrapperPane.add(userStatePane, paneGbc);
        wrapperPane.add(buttonPane, paneGbc);
        mainPane.add(wrapperPane, paneGbc);

        paneGbc.fill = GridBagConstraints.BOTH;
        paneGbc.weighty = 1.0;

        mainPane.add(new JLabel(), paneGbc);

        btnFollowed.addItemListener(e -> {
            final NumberFormat fmt = NumberFormat.getInstance();
            int changed = e.getStateChange();
            JToggleButton btn = (JToggleButton) e.getSource();

            if (changed == ItemEvent.DESELECTED) {
                activityController.removeUserFollow(repository.getUserAccount(), currentUser);
                btnFollowed.setText("팔로우 하기");
            } else if (changed == ItemEvent.SELECTED) {
                activityController.addUserFollow(repository.getUserAccount(), currentUser);
                btnFollowed.setText("팔로우 중");
            }
            lblFollowerCount.setText(fmt.format(activityController.getFollowerCount(currentUser.getUserId())));
            lblFollowingCount.setText(fmt.format(activityController.getFollowingCount(currentUser.getUserId())));
        });

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
