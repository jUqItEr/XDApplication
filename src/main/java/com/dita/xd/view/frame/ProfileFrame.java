package com.dita.xd.view.frame;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.base.JRoundedImageView;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfileFrame extends JFrame implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;

    CardLayout clMain;

    JPanel mainPane;

    public ProfileFrame(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        initialize();
        changeLocale(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(new Dimension(750, 600));

        /* Variables declaration */
        NicknamePanel nickNamePane = new NicknamePanel(currentLocale);
        clMain = new CardLayout();

        mainPane = new JPanel();

        /* Set the layout of sub-panels */
        mainPane.setLayout(clMain);

        /* Add the panes to main CardLayout */
        mainPane.add(nickNamePane, "nickname");

        this.add(mainPane);
    }

    private void loadText() {

    }

    @Override
    public void changeLocale(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);
        onLocaleChanged(locale);
        loadText();
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
    }

    // Inner Class
    class NicknamePanel extends JPanel implements LocaleChangeListener {

        private Locale currentLocale;
        private ResourceBundle localeBundle;

        public NicknamePanel(Locale locale) {
            currentLocale = locale;
            localeBundle = ResourceBundle.getBundle("language", locale);

            initialize();
            changeLocale(locale);
        }

        private void initialize() {
            setLayout(null);
            JRoundedImageView rivProfile = new JRoundedImageView("resources/images/logo.png", 300, 300);
            rivProfile.setBackground(Color.PINK);
            rivProfile.setLocation(10, 10);

            add(rivProfile);
        }

        private void loadText() {

        }

        @Override
        public void changeLocale(Locale locale) {
            currentLocale = locale;
            localeBundle = ResourceBundle.getBundle("language", locale);
            onLocaleChanged(locale);
            loadText();
        }

        @Override
        public void onLocaleChanged(Locale newLocale) {
            LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        }
    }

//    class NicknamePanel extends JPanel implements LocaleChangeListener {
//
//        private Locale currentLocale;
//        private ResourceBundle localeBundle;
//
//        public NicknamePanel(Locale locale) {
//            currentLocale = locale;
//            localeBundle = ResourceBundle.getBundle("language", locale);
//
//            initialize();
//            changeLocale(locale);
//        }
//
//        private void initialize() {
//        }
//
//        private void loadText() {
//
//        }
//
//        @Override
//        public void changeLocale(Locale locale) {
//            currentLocale = locale;
//            localeBundle = ResourceBundle.getBundle("language", locale);
//            onLocaleChanged(locale);
//            loadText();
//        }
//
//        @Override
//        public void onLocaleChanged(Locale newLocale) {
//            LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
//        }
//    }
}