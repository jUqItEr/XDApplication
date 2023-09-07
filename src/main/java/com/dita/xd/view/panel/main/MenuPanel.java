package com.dita.xd.view.panel.main;

import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.manager.MainLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuPanel extends JPanel{
    //private final ?Controller controller;
    private final MainLayoutMgr mgr;
    private ResourceBundle localeBundle;
    private Locale currentLocale;

    private JLabel lblMenu;
    private MenubarPanel menubarPanel;
    private final MiniProfile miniProfile;
    private final JPanel objectPane = new JPanel(new GridLayout(0, 1, 2, 0));


    public MenuPanel(Locale locale){
        //controller = new ?Controller();
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        miniProfile = new MiniProfile(locale);

        mgr = MainLayoutMgr.getInstance();

        initialize();

    }

    private void initialize(){
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        JPanel headPane = new JPanel();
        JPanel mainPane = new JPanel();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BorderLayout());
        mainPane.setPreferredSize(new Dimension(250,100));
        /* Set the properties of components */
        createMenu("home");
        createMenu("search");
        createMenu("profile");
        createMenu("chat");

        mainPane.add(objectPane,BorderLayout.NORTH);
        mainPane.add(Box.createGlue(),BorderLayout.CENTER);
        mainPane.add(miniProfile, BorderLayout.SOUTH);

        this.add(mainPane);
    }

    private void loadText() {
    }

    private void createMenu(String menuBar) {
        menubarPanel = new MenubarPanel(currentLocale, menuBar);
        objectPane.add(menubarPanel);

        menubarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mgr.show(menuBar);
            }
        });
    }

    public static class MenubarPanel extends JPanel{
        private ResourceBundle localeBundle;
        private Locale currentLocale;
        private JLabel lblMenu;
        private String menuBar;

        public MenubarPanel(Locale locale, String menuBar){
            currentLocale = locale;
            localeBundle = ResourceBundle.getBundle("language",locale);

            this.menuBar = menuBar;

            initialize();
        }

        private void initialize(){
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(15,40,15,0));
            JPanel mainPane = new JPanel();

            lblMenu = new JLabel();



            loadText();

            JImageView imvImage = new JImageView();
            ImageIcon Icon = new ImageIcon("resources/images/" + menuBar + ".png");
            imvImage.setMaximumSize(new Dimension(30,30));
            imvImage.setPreferredSize(new Dimension(30,30));
            imvImage.setIcon(Icon);

            mainPane.add(imvImage);
            mainPane.add(Box.createRigidArea(new Dimension(10,0)));
            mainPane.add(lblMenu);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });

            add(mainPane, BorderLayout.WEST);
        }

        private void loadText(){
            lblMenu.setText(menuBar);
            lblMenu.setFont(lblMenu.getFont().deriveFont(20f)
                    .deriveFont(Font.BOLD));
        }
    }

    public class MiniProfile extends JPanel{
        private ResourceBundle localeBundle;
        private final UserRepository repository;

        private Locale currentLocale;
        private JLabel lblUserId;
        private JLabel lblUserNickname;
        public MiniProfile(Locale locale){
            localeBundle = ResourceBundle.getBundle("language",locale);
            currentLocale = locale;

            repository = UserRepository.getInstance();

            initialize();
        }

        private void initialize(){
            setLayout(new BorderLayout());

            JPanel mainPane = new JPanel();
            JPanel profilePane = new JPanel();
            JPanel userInfoPane = new JPanel();

            lblMenu = new JLabel();
            lblUserNickname = new JLabel();
            lblUserId = new JLabel();

            mainPane.setLayout(new BorderLayout());
            profilePane.setLayout(new BorderLayout());
            userInfoPane.setLayout(new BorderLayout());

            profilePane.setOpaque(false);
            userInfoPane.setOpaque(false);

            loadText();

            /* 패널 클릭 액션 */
            mainPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    mainPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    mainPane.setBackground(Color.lightGray);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mainPane.setCursor(Cursor.getDefaultCursor());
                    mainPane.setBackground(Color.WHITE);
                }
            });

            /* 메인패널에 들어갈 이미지 설정 */
            JRoundedImageView rivProfile = new JRoundedImageView();
            String profileUrl = repository.getUserAccount().getProfileImage();
            ImageIcon icon = null;

            try {
                if (profileUrl != null) {
                    icon = new ImageIcon(new URL(profileUrl));
                } else {
                    throw new MalformedURLException("No valid URL");
                }
            } catch (MalformedURLException e) {
                icon = new ImageIcon("resources/images/anonymous.jpg");

            }

            rivProfile.setMaximumSize(new Dimension(40, 40));
            rivProfile.setPreferredSize(new Dimension(40, 40));
            rivProfile.setIcon(icon);


            JImageView rivMore = new JImageView();
            ImageIcon moreIcon = new ImageIcon("resources/images/more.png");
            rivMore.setMaximumSize(new Dimension(20, 20));
            rivMore.setPreferredSize(new Dimension(20, 20));
            rivMore.setIcon(moreIcon);

            /* 여백 설정 */
            setBorder(BorderFactory.createEmptyBorder(10,40,20,15));

            profilePane.setBorder(BorderFactory.createEmptyBorder(
                    0,0,0,5));

            /* 서브 패널 및 컴포넌트 추가 */

            profilePane.add(rivProfile);

            userInfoPane.add(lblUserNickname, BorderLayout.NORTH);
            userInfoPane.add(lblUserId, BorderLayout.SOUTH);

            mainPane.add(rivMore, BorderLayout.EAST);
            mainPane.add(profilePane, BorderLayout.WEST);

            mainPane.add(userInfoPane);

            add(mainPane);
        }

        private void loadText(){
            lblUserNickname.setText(repository.getUserNickname());
            lblUserNickname.setFont(lblUserNickname.getFont().deriveFont(Font.BOLD));
            lblUserNickname.setForeground(Color.darkGray);


            lblUserId.setText("@" + repository.getUserId());
            lblUserId.setForeground(Color.gray);
        }
    }
}