package com.dita.xd.view.panel;

import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.locale.LocaleChangeListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginPanel extends JPanel implements LocaleChangeListener {
    private final ResourceBundle localeBundle;

    private JHintTextField htfId;
    private JHintTextField htfPassword;

    public LoginPanel() {
        this.localeBundle = ResourceBundle.getBundle("language", Locale.getDefault());

        broadcastLocaleChange(Locale.getDefault());

        initialize();
    }

    private void initialize() {
        setLayout(null);
        htfId = new JHintTextField(localeBundle.getString("login.htfIdHint"));

        JPasswordField pfPassword = new JHintPasswordField(localeBundle.getString("login.htfPasswordHint"));
        htfPassword = new JHintTextField(localeBundle.getString("login.htfPasswordHint"));


        htfId.setBounds(75, 300, 300, 40);
        htfPassword.setBounds(75, 360, 300, 40);
        pfPassword.setBounds(75, 360, 300, 40);


        JLabel lblFindPassword = new JLabel(localeBundle.getString("login.lblFindPassword"));
        lblFindPassword.setBounds(75, 500, 300, 20);
        Font lblFont = lblFindPassword.getFont();
        lblFindPassword.setFont(new Font(lblFont.getFontName(), Font.BOLD, lblFont.getSize()));
        lblFindPassword.setForeground(Color.BLUE);
        lblFindPassword.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnLogin = new JButton(localeBundle.getString("login.btnLogin"));
        btnLogin.setBounds(75, 420, 300, 30);

        JButton btnRegister = new JButton(localeBundle.getString("login.btnRegister"));
        btnRegister.setBounds(75, 560, 300, 30);

        this.add(htfId);
        //this.add(htfPassword);
        this.add(pfPassword);
        this.add(lblFindPassword);
        this.add(btnLogin);
        this.add(btnRegister);
    }

    @Override
    public void onLocaleChange(Locale newLocale) {
        broadcastLocaleChange(newLocale);
    }

    private void broadcastLocaleChange(Locale locale) {
        List<Component> components = LocaleChangeListener.getChildren(Component.class, LoginPanel.this);
        components.stream().filter(LocaleChangeListener.class::isInstance)
                .map(LocaleChangeListener.class::cast)
                .forEach(lc -> lc.onLocaleChange(locale));
    }
}
