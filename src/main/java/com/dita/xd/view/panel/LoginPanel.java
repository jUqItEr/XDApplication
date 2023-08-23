package com.dita.xd.view.panel;

import com.dita.xd.controller.LoginController;
import com.dita.xd.util.filter.IDFilter;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.listener.LocaleChangeListener;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginPanel extends JPanel implements LocaleChangeListener {
    private final LoginController controller;
    private ResourceBundle localeBundle;

    public LoginPanel() {
        /* Change the current locale. */
        Locale currentLocale = Locale.CHINA;
        getLocaleString(currentLocale);

        controller = new LoginController();

        initialize();
    }

    private void initialize() {
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        /* Variables declaration */
        JButton btnLogin = new JButton();
        JButton btnRegister = new JButton();

        JHintPasswordField hpfPassword = new JHintPasswordField();
        JHintTextField htfId = new JHintTextField();

        JLabel lblFindPassword = new JLabel();

        JPanel pnlHeader = new JPanel();
        JPanel pnlLocale = new JPanel();
        JPanel pnlMain = new JPanel();

        /* Set the localized texts. */
        btnLogin.setText(localeBundle.getString("login.button.login"));
        btnRegister.setText(localeBundle.getString("login.button.register"));

        hpfPassword.setHint(localeBundle.getString("login.field.hint.password"));
        htfId.setHint(localeBundle.getString("login.field.hint.id"));

        lblFindPassword.setText(localeBundle.getString("login.label.password"));

        /* Set the properties of sub panels */
        pnlHeader.setLayout(new BorderLayout());
        pnlLocale.setLayout(new BorderLayout());
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        // For Testing!!
        pnlLocale.setBackground(Color.PINK);

        /* Set the properties of components */
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(300, 35));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(300, 35));

        lblFindPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFindPassword.setMaximumSize(new Dimension(300, 24));
        lblFindPassword.setHorizontalAlignment(JLabel.CENTER);

        htfId.setMaximumSize(new Dimension(300, 40));
        htfId.setPreferredSize(new Dimension(300, 40));
        hpfPassword.setMaximumSize(new Dimension(300, 40));
        hpfPassword.setPreferredSize(new Dimension(300, 40));

        /* Add listeners on components */
        btnLogin.addActionListener(e -> {
            String id = htfId.getText().trim();
            String pwd = new String(hpfPassword.getPassword());

            if (id.isEmpty()) {

                return;
            }
            if (pwd.isEmpty()) {
                return;
            }
            if (controller.login(id, pwd)) {
                System.out.println("Login complete");

            } else {
                System.out.println("Login failed");
            }
        });

        btnRegister.addActionListener(e -> {

        });

        // DocumentFilter
        ((AbstractDocument) htfId.getDocument()).setDocumentFilter(new IDFilter());

        /* Add components to sub panels */
        pnlHeader.add(pnlLocale, BorderLayout.NORTH);

        pnlLocale.add(new JButton("Test"), BorderLayout.EAST);

        // Box Vertical Glue
        // Do not touch on these settings!
        pnlMain.add(Box.createVerticalGlue());
        pnlMain.add(htfId);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(hpfPassword);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(btnLogin);
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(lblFindPassword);
        pnlMain.add(Box.createVerticalStrut(100));
        pnlMain.add(btnRegister);
        pnlMain.add(Box.createVerticalStrut(50));

        /* Add components to panel */
        this.add(pnlHeader, BorderLayout.NORTH);
        this.add(pnlMain);
    }

    @Override
    public void getLocaleString(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        onLocaleChanged(locale);
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        LocaleChangeListener.broadcastLocaleChanged(newLocale, LoginPanel.this);
    }
}
