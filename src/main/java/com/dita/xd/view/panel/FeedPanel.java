package com.dita.xd.view.panel;

import com.dita.xd.listener.LocaleChangeListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class FeedPanel extends JFrame implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;
    private JLabel lblUserId;
    private JLabel lblNickName;
    private JLabel lblCreatedAt;
    private JTextArea txaFeedText;
    private JButton btnFeedComment;
    private JButton btnFeedBack;
    private JButton btnFeedLike;
    private JButton btnViewer;


    public FeedPanel(Locale locale){
        localeBundle = ResourceBundle.getBundle("Language", locale);

        initialize();

        onLocaleChanged(locale);
    }

    private void initialize(){
        setLayout(new BorderLayout());

        JPanel mainPane = new JPanel();


    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
//        loadText();
    }
}
