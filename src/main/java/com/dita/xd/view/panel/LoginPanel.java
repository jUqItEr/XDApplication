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

    public LoginPanel() {
        this.localeBundle = ResourceBundle.getBundle("language", Locale.KOREAN);

        broadcastLocaleChange(Locale.getDefault());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initialize();
    }

    private void initialize() {
        /* Use Absolute Layout (For testing) */

        JButton btnLogin =
                new JButton(localeBundle.getString("login.button.login"));
        JButton btnRegister =
                new JButton(localeBundle.getString("login.button.register"));
        JHintTextField htfId =
                new JHintTextField(localeBundle.getString("login.field.hint.id"));
        JHintPasswordField hpfPassword =
                new JHintPasswordField(localeBundle.getString("login.field.hint.password"));
        JLabel lblFindPassword =
                new JLabel(localeBundle.getString("login.label.password"));

        this.add(htfId);
        this.add(hpfPassword);
        this.add(btnLogin);
        this.add(lblFindPassword);
        this.add(btnRegister);

        setBackground(Color.GRAY);

        htfId.setMaximumSize(new Dimension(300, 40));
        hpfPassword.setMaximumSize(new Dimension(300, 40));

        htfId.setAlignmentX(Component.CENTER_ALIGNMENT);
        hpfPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
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
