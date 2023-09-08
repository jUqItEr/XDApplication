package com.dita.xd.view.dialog;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.view.base.JVerticalScrollPane;
import com.dita.xd.view.panel.main.FeedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class FeedDialog extends JDialog implements LocaleChangeListener {
    private final FeedController feedController;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private JPanel mainPane;

    private final UserBean currentUser;
    private final String mode;

    public FeedDialog(Locale locale, UserBean bean, String name) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        feedController = new FeedController();

        currentUser = bean;
        mode = name;

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(450, 800);
        setResizable(false);

        mainPane = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(mainPane));
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);
        scrollPane.setVerticalScrollBar(scrollBar);

        mainPane.setLayout(new GridBagLayout());

        switch (mode) {
            case "bookmark" -> {
                setTitle("["+ currentUser.getNickname() +"] 님의 북마크 보기");
                feedController.getBookmarks(currentUser.getUserId())
                        .forEach(bean -> mainPane.add(createFeed(bean), gbc));
            }
            case "feed" -> {
                setTitle("["+ currentUser.getNickname() +"] 님의 작성한 피드 보기");
                feedController.getUserFeeds(currentUser.getUserId())
                        .forEach(bean -> mainPane.add(createFeed(bean), gbc));
            }
            case "like" -> {
                setTitle("["+ currentUser.getNickname() +"] 님의 좋아요 목록 보기");
                feedController.getLikes(currentUser.getUserId())
                        .forEach(bean -> mainPane.add(createFeed(bean), gbc));
            }
        }

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPane.add(new JLabel(), gbc);

        add(scrollPane);
    }

    private FeedPanel createFeed(FeedBean bean) {
        return new FeedPanel(currentLocale, bean);
    }

    private void loadText() {

    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
