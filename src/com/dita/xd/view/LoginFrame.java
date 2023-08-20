package com.dita.xd.view;

import com.dita.xd.controller.LoginController;
import com.dita.xd.view.base.JHintTextField;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginFrame extends JFrame {
    private LoginController controller = null;
    private String title;

    private JPanel pnlLogo;
    private JHintTextField htfId;
    private JHintTextField htfPwd;
    private JButton btnLogin;

    public LoginFrame() {
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
        this.getContentPane().setLayout(new BorderLayout());

        JPanel pnlTop = new JPanel();
        pnlTop.setBounds(0, 0, 0, 300);

        JPanel pnlLogin = new JPanel(new BorderLayout());

        htfId = new JHintTextField("아이디");
        htfPwd = new JHintTextField("비밀번호");

        pnlLogin.add(htfId, BorderLayout.NORTH);
        pnlLogin.add(htfPwd, BorderLayout.SOUTH);

        this.add(pnlLogin);


        this.add(pnlTop, BorderLayout.NORTH);
    }
}
