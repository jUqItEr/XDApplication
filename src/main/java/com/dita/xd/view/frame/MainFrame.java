package com.dita.xd.view.frame;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.dialog.ProfileDialog;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFrame extends JFrame implements LocaleChangeListener {
    private Locale currentLocale;
    private ResourceBundle localeBundle;
    private final UserRepository repository;

    public MainFrame(Locale locale) {
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        repository = UserRepository.getInstance();

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setSize(new Dimension(750, 600));
        setLocationRelativeTo(null);
        setResizable(false);

        if (repository.getUserNickname() == null) {
            ProfileDialog dialog = new ProfileDialog(currentLocale);

            if (!dialog.showDialog()) {
                dispose();
            }
        }
    }

    private void loadText() {

    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        LocaleChangeListener.broadcastLocaleChanged(newLocale, MainFrame.this);
    }
}
