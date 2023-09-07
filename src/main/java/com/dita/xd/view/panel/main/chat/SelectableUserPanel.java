package com.dita.xd.view.panel.main.chat;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JRoundedImageView;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectableUserPanel extends JPanel implements LocaleChangeListener {
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
    private JCheckBox chbSelected;

    private int nameLength;
    private int idLength;
    private int createdLength;


    public SelectableUserPanel(Locale locale, UserBean bean){
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

        txaIntroduce = new JTextArea();

        chbSelected = new JCheckBox();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BorderLayout());
        profilePane.setLayout(new BorderLayout());
        profileSubPane.setLayout(new BorderLayout());

        boxPane.setLayout(new BorderLayout());

        profilePane.add(profileSubPane, BorderLayout.NORTH);

        loadText();

        nameLength = 12 * lblNickName.getText().length();
        idLength = 12 * lblUserId.getText().length();
        createdLength = 12 * lblCreatedAt.getText().length();

        /* Set the properties of sub panels */
        userInfoPane.setLayout(new FlowLayout(FlowLayout.LEFT,2,0));
        userInfoPane.setPreferredSize(new Dimension(
                nameLength + idLength + createdLength + 10, 20));

        /* Set the properties of components */
        txaIntroduce.setPreferredSize(new Dimension(300, 20));
        txaIntroduce.setMaximumSize(
                new Dimension(300, 20 * countEnter(txaIntroduce.getText())));
        txaIntroduce.setEditable(false);

        JRoundedImageView rivProfile = new JRoundedImageView();
        ImageIcon icon;
        try {
            String profileUrl = userBean.getProfileImage();
            if (profileUrl != null) {
                icon = new ImageIcon(new URL(profileUrl));
            } else {
                throw new MalformedURLException("No valid URL");
            }
        } catch (MalformedURLException e) {
            icon = new ImageIcon("resources/images/anonymous.jpg");
        }
        rivProfile.setPreferredSize(new Dimension(50, 50));
        rivProfile.setMaximumSize(new Dimension(50, 50));
        rivProfile.setIcon(icon);

        lblNickName.setPreferredSize(new Dimension(nameLength,20));
        lblUserId.setPreferredSize(new Dimension(idLength, 20));
        lblCreatedAt.setPreferredSize(new Dimension(createdLength,20));

        // Box Vertical Glue
        profilePane.add(Box.createGlue(), BorderLayout.CENTER);
        profileSubPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        mainPane.setBorder(BorderFactory.createEmptyBorder(10,0,20,10));
        boxPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));

        boxPane.add(userInfoPane, BorderLayout.NORTH);
        boxPane.add(Box.createRigidArea(new Dimension(0, 5)));
        boxPane.add(txaIntroduce, BorderLayout.SOUTH);

        profileSubPane.add(rivProfile, BorderLayout.NORTH);

        userInfoPane.add(lblNickName);
        userInfoPane.add(lblUserId);
        userInfoPane.add(lblCreatedAt);

        mainPane.add(chbSelected, BorderLayout.EAST);
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

    public boolean isChecked() {
        return chbSelected.isSelected();
    }

    public UserBean getUserBean() {
        return userBean;
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
