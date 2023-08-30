package com.dita.xd.view.panel;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.manager.MainLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuPanel extends JPanel{
    //private final ?Controller controller;
    private final MainLayoutMgr mgr;
    private ResourceBundle localeBundle;
    private Locale currentLocale;

    private JLabel lblHome;
    private JLabel lblProfile;
    private JLabel lblSearch;
    private JLabel lblChat;

    public MenuPanel(Locale locale){
        //controller = new ?Controller();
        localeBundle = ResourceBundle.getBundle("language", locale);

        mgr = MainLayoutMgr.getInstance();

        initialize();

    }

    private void initialize(){
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        JPanel pnlMain = new JPanel();


        /* Load to memory */
        lblHome = new JLabel();
        lblSearch = new JLabel();
        lblProfile = new JLabel();
        lblChat = new JLabel();


        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setPreferredSize(new Dimension(250,400));
        /* Set the properties of components */
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setPreferredSize(new Dimension(150, 30));
        lblHome.setMaximumSize(new Dimension(150,30));
        lblHome.setHorizontalAlignment(JLabel.LEFT);
        lblHome.setFont(lblHome.getFont().deriveFont(20f));

        lblSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSearch.setPreferredSize(new Dimension(150, 30));
        lblSearch.setMaximumSize(new Dimension(150,30));
        lblSearch.setHorizontalAlignment(JLabel.LEFT);
        lblSearch.setFont(lblSearch.getFont().deriveFont(20f));

        lblProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblProfile.setPreferredSize(new Dimension(150, 30));
        lblProfile.setMaximumSize(new Dimension(150,30));
        lblProfile.setHorizontalAlignment(JLabel.LEFT);
        lblProfile.setFont(lblProfile.getFont().deriveFont(20f));

        lblChat.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblChat.setPreferredSize(new Dimension(150, 30));
        lblChat.setMaximumSize(new Dimension(150,30));
        lblChat.setHorizontalAlignment(JLabel.LEFT);
        lblChat.setFont(lblChat.getFont().deriveFont(20f));

        lblHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mgr.show("home");
            }
        });

        lblSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mgr.show("search");
            }
        });

        lblProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mgr.show("profile");
            }
        });

        lblChat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mgr.show("chat");
            }
        });

        pnlMain.add(lblHome);
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(lblSearch);
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(lblProfile);
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(lblChat);
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(Box.createVerticalGlue());

        this.add(pnlMain);
    }

    private void loadText() {
        lblHome.setText("홈");
        lblChat.setText("채팅");
        lblProfile.setText("프로필");
        lblSearch.setText("검색");
    }
}