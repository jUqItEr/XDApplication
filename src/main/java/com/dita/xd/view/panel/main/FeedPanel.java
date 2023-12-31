package com.dita.xd.view.panel.main;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.controller.FeedController;
import com.dita.xd.controller.LoginController;
import com.dita.xd.controller.TranslationController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.base.JXdTextPane;
import com.dita.xd.view.manager.MainLayoutMgr;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedPanel extends JPanel implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;
    private final ActivityController activityController;
    private final FeedController feedController;
    private final TranslationController translationController;
    private final LoginController loginController;
    private final UserRepository repository;
    private final MainLayoutMgr mgr;
    private MediaPanel mediaPanel;

    private FeedBean feedBean;

    private JLabel lblUserId;
    private JLabel lblNickName;
    private JLabel lblCreatedAt;


    private JLabel lblFeedComment;
    private JLabel lblFeedBack;
    private JLabel lblFeedLike;
    private JLabel lblTranslation;

    private JXdTextPane txaFeedContent;
    private int nameLength;
    private int idLength;
    private int createdLength;
    private long createdTime;
    private Calendar localTime;
    private Calendar feedTime;


    public FeedPanel(Locale locale, FeedBean bean){
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);
        repository = UserRepository.getInstance();

        activityController = new ActivityController();
        feedController = new FeedController();
        translationController = new TranslationController();
        loginController = new LoginController();

        mediaPanel = new MediaPanel(bean);
        feedBean = bean ;

        mgr = MainLayoutMgr.getInstance();

        initialize();

        onLocaleChanged(locale);
    }

    private void initialize(){
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        /* Variables declaration */
        JPanel mainPane = new JPanel();

        JPanel profilePane = new JPanel(); /* 피드 좌측  */

        JPanel boxPane = new JPanel(); /* profilePane을 제외한 Panel 묶기 위해 선언 */
        JPanel boxTopPane = new JPanel();
        JPanel userInfoPane = new JPanel();
        JPanel userIfnoSubPane = new JPanel();
        JPanel contentPane = new JPanel();
        JPanel mediaPane = new JPanel();

        JPanel feedInfoPane = new JPanel(); /* 피드 하단의 상태를 나타내는 Panel들의 묶음 */
        JPanel feedInfoSubPane = new JPanel();
        JPanel feedInfoCommentPane = new JPanel();
        JPanel feedInfoFeedBackPane = new JPanel();
        JPanel feedInfoLikePane = new JPanel();

        lblUserId = new JLabel();
        lblNickName = new JLabel();
        lblCreatedAt = new JLabel();
        lblFeedComment = new JLabel();
        lblFeedBack = new JLabel();
        lblFeedLike = new JLabel();
        lblTranslation = new JLabel();

        txaFeedContent = new JXdTextPane();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BorderLayout());
        profilePane.setLayout(new BorderLayout());

        boxPane.setLayout(new BorderLayout());
        boxTopPane.setLayout(new BorderLayout());
        userIfnoSubPane.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        contentPane.setLayout(new BorderLayout());
        mediaPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        feedInfoPane.setLayout(new BorderLayout());
        feedInfoSubPane.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        feedInfoCommentPane.setLayout(new BorderLayout());
        feedInfoFeedBackPane.setLayout(new BorderLayout());
        feedInfoLikePane.setLayout(new BorderLayout());

        boxTopPane.add(userInfoPane, BorderLayout.WEST);

        boxPane.add(boxTopPane, BorderLayout.NORTH);
        boxPane.add(contentPane);
        boxPane.add(feedInfoPane, BorderLayout.SOUTH);

        feedInfoPane.add(feedInfoSubPane, BorderLayout.WEST);

        feedInfoSubPane.add(feedInfoCommentPane);
        feedInfoSubPane.add(feedInfoFeedBackPane);
        feedInfoSubPane.add(feedInfoLikePane);


        /* 현재 시각과 피드의 작성시각의 차이를 구하기 위해 선언 */
        feedTime = Calendar.getInstance();
        localTime = Calendar.getInstance();
        feedTime.setTime(feedBean.getCreatedAt());

        createdTime = ( /* 피드의 생성 시각과 현재 시각값의 차이를 구하고 ms단위를 s로 조정 */
                localTime.getTimeInMillis() - feedTime.getTimeInMillis()) / 1000;

        loadText();

        /* Set the properties of sub panels */
        userInfoPane.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        /* Set the properties of components */
        txaFeedContent.setPreferredSize( /* txaFeedContent의 높이는 줄바꿈의 개수만큼 기본 높이에 추가하여 설정 */
                new Dimension(370, 20 * countEnter(txaFeedContent.getText())));
        txaFeedContent.setEditable(false);
        txaFeedContent.setFocusable(false);

        JRoundedImageView rivProfile = new JRoundedImageView();

        try {
            String profileUrl = feedBean.getUserProfileImage();

            if (profileUrl != null) {
                rivProfile.setIcon(new ImageIcon(new URL(profileUrl)));
            } else {
                throw new MalformedURLException("No valid URL");
            }
        } catch (MalformedURLException e) {
            rivProfile.setIcon(new ImageIcon("resources/images/anonymous.jpg"));
        }
        rivProfile.setPreferredSize(new Dimension(50,50));
        rivProfile.setMaximumSize(new Dimension(50, 50));

        JImageView commentImageView = new JImageView();
        ImageIcon commentIcon = new ImageIcon("resources/images/comment.png");
        commentImageView.setMaximumSize(new Dimension(20,20));
        commentImageView.setPreferredSize(new Dimension(20,20));
        commentImageView.setIcon(commentIcon);

        JImageView feedbackImageView = new JImageView();
        ImageIcon feedbackIcon = new ImageIcon("resources/images/feedback.png");
        ImageIcon clickedFeedbackIcon = new ImageIcon("resources/images/clicked-feedback.png");
        feedbackImageView.setMaximumSize(new Dimension(20,20));
        feedbackImageView.setPreferredSize(new Dimension(20,20));

        JImageView likeImageView = new JImageView();
        ImageIcon likeIcon = new ImageIcon("resources/images/like.png");
        ImageIcon clickedLikeIcon = new ImageIcon("resources/images/clicked-like.png");
        likeImageView.setMaximumSize(new Dimension(20,20));
        likeImageView.setPreferredSize(new Dimension(20,20));

        JImageView bookmarkImageView = new JImageView();
        ImageIcon bookmarkIcon = new ImageIcon("resources/images/bookmark.png");
        ImageIcon clickedBookmarkIcon = new ImageIcon("resources/images/clicked-bookmark.png");
        bookmarkImageView.setMaximumSize(new Dimension(20,20));
        bookmarkImageView.setPreferredSize(new Dimension(20,20));
        bookmarkImageView.setIcon(bookmarkIcon);

        JImageView translationImageView = new JImageView();
        ImageIcon translationIcon = new ImageIcon("resources/images/translation.png");
        translationImageView.setMaximumSize(new Dimension(20,20));
        translationImageView.setPreferredSize(new Dimension(20,20));
        translationImageView.setIcon(translationIcon);

        lblFeedComment.setPreferredSize(new Dimension( 50,20));
        lblFeedBack.setPreferredSize(new Dimension( 50, 20));
        lblFeedLike.setPreferredSize(new Dimension(50,20));

        // Box Vertical Glue
        mainPane.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));

        profilePane.add(Box.createGlue(), BorderLayout.CENTER);
        profilePane.setBorder(BorderFactory.createEmptyBorder(5,15,0,10));

        boxTopPane.add(Box.createGlue(), BorderLayout.CENTER);
        boxTopPane.setBorder(BorderFactory.createEmptyBorder(3,0,3,50));

        contentPane.add(Box.createGlue(), BorderLayout.CENTER);

        mediaPane.setMaximumSize(new Dimension(200,200));

        feedInfoCommentPane.add(Box.createRigidArea(new Dimension(10,0)));
        feedInfoFeedBackPane.add(Box.createRigidArea(new Dimension(10, 0)));
        feedInfoLikePane.add(Box.createRigidArea(new Dimension(10, 0)));

        feedInfoPane.add(Box.createGlue(), BorderLayout.CENTER);
        feedInfoPane.setBorder(BorderFactory.createEmptyBorder(10,0,10,50));

        /* Add components to panel */
        profilePane.add(rivProfile, BorderLayout.NORTH);

        boxTopPane.add(translationImageView, BorderLayout.EAST);

        contentPane.add(txaFeedContent, BorderLayout.WEST);
        contentPane.add(mediaPane, BorderLayout.SOUTH);

        mediaPane.add(mediaPanel);


        userInfoPane.add(lblNickName);
        userInfoPane.add(userIfnoSubPane);
        userIfnoSubPane.add(lblUserId);
        userIfnoSubPane.add(lblCreatedAt);

        feedInfoPane.add(bookmarkImageView, BorderLayout.EAST);

        feedInfoCommentPane.add(commentImageView, BorderLayout.WEST);
        feedInfoCommentPane.add(lblFeedComment, BorderLayout.EAST);
        feedInfoFeedBackPane.add(feedbackImageView, BorderLayout.WEST);
        feedInfoFeedBackPane.add(lblFeedBack, BorderLayout.EAST);
        feedInfoLikePane.add(likeImageView, BorderLayout.WEST);
        feedInfoLikePane.add(lblFeedLike, BorderLayout.EAST);

        mainPane.add(profilePane, BorderLayout.WEST);
        mainPane.add(boxPane);

        /* 사용자가 해당 피드에 좋아요를 이미 하였는지 */
        if (activityController.isCheckedLike(repository.getUserAccount(), feedBean)) {
            likeImageView.setIcon(clickedLikeIcon);
        } else {
            likeImageView.setIcon(likeIcon);
        }

        /* 사용자가 해당 피드에 리트윗을 이미 하였는지 */
        if (activityController.isCheckedFeedback(repository.getUserAccount(),feedBean)) {
            feedbackImageView.setIcon(clickedFeedbackIcon);
        } else {
            feedbackImageView.setIcon(feedbackIcon);
        }

        if (activityController.isCheckedBookmark(repository.getUserAccount(),feedBean)){
            bookmarkImageView.setIcon(clickedBookmarkIcon);
        } else {
            bookmarkImageView.setIcon(bookmarkIcon);
        }

        rivProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mgr.changeProfileContext(loginController.getUser(feedBean.getUserId()));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                rivProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rivProfile.setCursor(Cursor.getDefaultCursor());
            }
        });

        commentImageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                commentImageView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                commentImageView.setCursor(Cursor.getDefaultCursor());
            }
        });
        likeImageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UserBean currentUser = repository.getUserAccount();
                boolean result = activityController.addLike(currentUser, feedBean);
                System.out.println("[" + currentUser.getNickname() + "]이 " + feedBean.getId() + "번 피드에 좋아요를 눌렀습니다.");

                if (result) {
                    System.out.println("좋아요 성공");
                    likeImageView.setIcon(clickedLikeIcon);
                } else {
                    System.out.println("이미 눌렀던 피드네요...");
                    likeImageView.setIcon(likeIcon);
                    result = activityController.removeLike(currentUser, feedBean);
                }
                if (result) {
                    lblFeedLike.setText(String.valueOf(feedController.getLikes(feedBean).size()));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                likeImageView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                likeImageView.setCursor(Cursor.getDefaultCursor());
            }
        });

        feedbackImageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UserBean currentUser = repository.getUserAccount();
                boolean result = activityController.addFeedback(currentUser, feedBean);

                if (result) {
                    System.out.println("피드백 성공");
                    feedbackImageView.setIcon(clickedFeedbackIcon);
                } else {
                    System.out.println("이미 피드백을 눌렀던 피드네요...");
                    feedbackImageView.setIcon(feedbackIcon);
                    result = activityController.removeFeedback(currentUser, feedBean);
                }
                if (result) {
                    lblFeedBack.setText(String.valueOf(feedController.getFeedbacks(feedBean).size()));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                feedbackImageView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                feedbackImageView.setCursor(Cursor.getDefaultCursor());
            }
        });

        bookmarkImageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UserBean currentUser = repository.getUserAccount();
                boolean result = activityController.addBookmark(currentUser, feedBean);

                if (result) {
                    System.out.println("북마크 성공");
                    bookmarkImageView.setIcon(clickedBookmarkIcon);
                } else {
                    System.out.println("이미 북마크를 눌렀던 피드네요...");

                    result = activityController.removeBookmark(currentUser, feedBean);

                    if (result) {
                        bookmarkImageView.setIcon(bookmarkIcon);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                bookmarkImageView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bookmarkImageView.setCursor(Cursor.getDefaultCursor());
            }
        });

        translationImageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = txaFeedContent.getText();
                txaFeedContent.setText("");
                try{
                    txaFeedContent.getStyledDocument().insertString(
                            0, translationController.translate(text, localeToTargetString(currentLocale)),null);
                }catch (BadLocationException e2){
                    e2.printStackTrace();
                }
                revalidate();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                translationImageView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                translationImageView.setCursor(Cursor.getDefaultCursor());
            }
        });

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

        lblFeedComment.setText(String.valueOf(feedBean.getFeedCommentBeans().size()));
        lblFeedBack.setText(String.valueOf(feedBean.getFeedbackBeans().size()));
        lblFeedLike.setText(String.valueOf(feedBean.getLikeBeans().size()));
        lblTranslation.setText("번역");

        if(createdTime < 60) {
            lblCreatedAt.setText(String.format(localeBundle.getString("home.panel.label.seconds"), createdTime));
        } else if (createdTime < 60 * 60){
            lblCreatedAt.setText(String.format(localeBundle.getString("home.panel.label.minutes"), createdTime / 60));
        } else if (createdTime < 24 * 60 * 60){
            lblCreatedAt.setText(String.format(localeBundle.getString("home.panel.label.hours"), createdTime / 3600));

        } else if (createdTime < 7 * 24 * 60 * 60){
            lblCreatedAt.setText(String.format(localeBundle.getString("home.panel.label.days"), createdTime / 86400));
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

    public String localeToTargetString(Locale locale) {
        return locale.getLanguage();
    }
}
