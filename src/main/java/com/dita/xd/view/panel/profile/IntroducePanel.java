package com.dita.xd.view.panel.profile;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.manager.ProfileLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class IntroducePanel extends JPanel implements LocaleChangeListener {
    private final ProfileLayoutMgr mgr;

    private ResourceBundle localeBundle;

    public IntroducePanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        mgr = ProfileLayoutMgr.getInstance();

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());
    }

    private void loadText() {

    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
