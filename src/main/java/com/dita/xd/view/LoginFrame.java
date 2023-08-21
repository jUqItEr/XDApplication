package com.dita.xd.view;

import com.dita.xd.controller.LoginController;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.locale.LocaleChangeable;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFrame extends JFrame implements LocaleChangeable {
    private final ResourceBundle configBundle;
    private final ResourceBundle localeBundle;
    private LoginController controller = null;
    private String title;

    private JPanel pnlLogo;
    private JHintTextField htfId;
    private JHintTextField htfPassword;
    private JButton btnLogin;

    public LoginFrame() {
        this.configBundle = ResourceBundle.getBundle("config", Locale.ROOT);
        this.localeBundle = ResourceBundle.getBundle("language", Locale.getDefault());
        this.controller = new LoginController();
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
        this.getContentPane().setLayout(null);

        htfId = new JHintTextField(localeBundle.getString("login.htfIdHint"));
        htfPassword = new JHintTextField(localeBundle.getString("login.htfPasswordHint"));

        htfId.setBounds(75, 300, 300, 40);
        htfPassword.setBounds(75, 360, 300, 40);

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
        this.add(htfPassword);
        this.add(lblFindPassword);
        this.add(btnLogin);
        this.add(btnRegister);
    }

    @Override
    public void localeChanged(Locale newLocale) {

    }
}
