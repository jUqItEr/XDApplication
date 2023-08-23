package com.dita.xd.view.frame;

import com.dita.xd.controller.LoginController;
import com.dita.xd.view.locale.LocaleChangeListener;
import com.dita.xd.view.panel.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFrame extends JFrame implements LocaleChangeListener {
    private ResourceBundle localeBundle;
    private String title;

    public LoginFrame() {
        this.localeBundle = ResourceBundle.getBundle("language", Locale.getDefault());
        this.title = localeBundle.getString("login.title");

        /* Initialize components */
        initialize();
    }

    private void initialize() {
        this.setBounds(100, 100, 450, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(title);
        this.getContentPane().setLayout(new BorderLayout());

        this.add(new LoginPanel());
    }

    @Override
    public void getLocaleString(Locale locale) {

    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        this.localeBundle = ResourceBundle.getBundle("language", newLocale);
        this.title = localeBundle.getString("login.title");
    }
}
