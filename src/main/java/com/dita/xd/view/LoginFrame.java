package com.dita.xd.view;

import com.dita.xd.controller.LoginController;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.locale.LocaleChangeable;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFrame extends JFrame implements LocaleChangeable {
    private ResourceBundle configBundle;
    private LoginController controller = null;
    private String title;

    private JPanel pnlLogo;
    private JHintTextField htfId;
    private JHintTextField htfPwd;
    private JButton btnLogin;

    public LoginFrame() {
        this.configBundle = ResourceBundle.getBundle("config", Locale.ROOT);
        this.controller = new LoginController();
        this.title = "XD Login";

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

        htfId = new JHintTextField("아이디");
        htfPwd = new JHintTextField("비밀번호");

        htfId.setBounds(75, 400, 300, 40);
        htfPwd.setBounds(75, 460, 300, 40);

        this.add(htfId);
        this.add(htfPwd);
    }

    @Override
    public void localeChanged(Locale newLocale) {

    }
}
