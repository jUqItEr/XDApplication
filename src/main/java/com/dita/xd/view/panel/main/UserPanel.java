package com.dita.xd.view.panel.main;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;
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

public class UserPanel extends JPanel implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;
    private final FeedController controller;
    private final UserRepository repository;

    private UserBean userBean;

    private JButton btnProfileImg;
    private JLabel lblUserId;
    private JLabel lblNickName;
    private JLabel lblCreatedAt;
    private JTextArea txaIntroduce;

    private int nameLength;
    private int idLength;
    private int createdLength;


    public UserPanel(Locale locale, UserBean bean){
        localeBundle = ResourceBundle.getBundle("language", locale);
        repository = UserRepository.getInstance();

        controller = new FeedController();

        userBean = bean ;

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

        lblUserId = new JLabel();
        lblNickName = new JLabel();
        lblCreatedAt = new JLabel();

        btnProfileImg = new JButton();

        txaIntroduce = new JTextArea();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BorderLayout());
        profilePane.setLayout(new BoxLayout(profilePane, BoxLayout.Y_AXIS));
        profileSubPane.setLayout(new BoxLayout(profileSubPane, BoxLayout.X_AXIS));

        boxPane.setLayout(new BorderLayout());


        profilePane.add(profileSubPane);

        boxPane.add(userInfoPane, BorderLayout.NORTH);
        boxPane.add(Box.createGlue());
        boxPane.add(txaIntroduce, BorderLayout.SOUTH);

        loadText();

        nameLength = 12 * lblNickName.getText().length();
        idLength = 12 * lblUserId.getText().length();
        createdLength = 12 * lblCreatedAt.getText().length();

        /* Set the properties of sub panels */
        userInfoPane.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
        userInfoPane.setPreferredSize(new Dimension(
                nameLength + idLength + createdLength + 10, 20));

        /* Set the properties of components */
        txaIntroduce.setPreferredSize( /* txaFeedContent의 높이는 줄바꿈의 개수만큼 기본 높이에 추가하여 설정 */
                new Dimension(370, 20 * countEnter(txaIntroduce.getText())));
        txaIntroduce.setEditable(false);

        JRoundedImageView rivProfile = new JRoundedImageView();
        ImageIcon icon = new ImageIcon("resources/images/anonymous.jpg");
        rivProfile.setMaximumSize(new Dimension(70, 70));
        rivProfile.setIcon(icon);

        btnProfileImg.add(rivProfile);

        btnProfileImg.setPreferredSize(new Dimension(50, 50));
        btnProfileImg.setMaximumSize(new Dimension(50, 50));

        lblNickName.setPreferredSize(new Dimension(nameLength,20));
        lblUserId.setPreferredSize(new Dimension(idLength, 20));
        lblCreatedAt.setPreferredSize(new Dimension(createdLength,20));

        // Box Vertical Glue
        profileSubPane.add(Box.createRigidArea(new Dimension(20, 0)));
        profileSubPane.add(btnProfileImg);
        profileSubPane.add(Box.createRigidArea(new Dimension(10,0)));

        mainPane.add(Box.createRigidArea(new Dimension(
                0,15)),BorderLayout.NORTH);

        userInfoPane.add(lblNickName);
        userInfoPane.add(lblUserId);
        userInfoPane.add(lblCreatedAt);


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
        lblNickName.setText(userBean.getNickname());
        lblNickName.setFont(lblNickName.getFont().deriveFont(16f).deriveFont(Font.BOLD));
        lblUserId.setText("@" + userBean.getUserId());
        lblUserId.setForeground(Color.gray);

        txaIntroduce.setText(userBean.getIntroduce());
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }


}
