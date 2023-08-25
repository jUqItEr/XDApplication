package com.dita.xd.view.frame;

import com.dita.xd.listener.LocaleChangeListener;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFrame extends JFrame implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;

    public MainFrame(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        initialize();
        changeLocale(locale);
    }

    private void initialize() {
        setSize(new Dimension(750, 600));
        setLocationRelativeTo(null);
        setResizable(false);
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
        LocaleChangeListener.broadcastLocaleChanged(newLocale, MainFrame.this);
    }
}
