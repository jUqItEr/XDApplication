package com.dita.xd.view.panel;

import com.dita.xd.controller.RegisterController;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel2 extends JPanel {

    private final RegisterController controller;
    public RegisterPanel2() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        controller = new RegisterController();

        initialize();
    }   // -- End of constructor

    /**
     * @// FIXME: 2023/08/23
     * */
    private void initialize() {
        setLayout(new BorderLayout());
        /* Variables declaration */

        JButton btnRegister = new JButton("회원가입");
        JButton btnCancel = new JButton("취소");

        JHintTextField htfId = new JHintTextField("아이디");
        JHintPasswordField hpfPassword = new JHintPasswordField("비밀번호");
        JHintTextField htfEmail = new JHintTextField("이메일");

        JPanel pnlHeader = new JPanel();
        JPanel pnlLocale = new JPanel();
        JPanel pnlMain = new JPanel();
        JPanel pnlButton = new JPanel();

        /* Set the properties of sub panels */
        pnlHeader.setLayout(new BorderLayout());
        pnlLocale.setLayout(new BorderLayout());
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.X_AXIS));

        /* Add components to sub panel */
        pnlHeader.add(pnlLocale, BorderLayout.NORTH);

        pnlLocale.add(new JButton("Language"), BorderLayout.EAST);

        pnlMain.add(Box.createVerticalGlue());
        pnlMain.add(htfId);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(hpfPassword);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(htfEmail);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(pnlButton);

        pnlButton.add(btnRegister);
        pnlButton.add(Box.createHorizontalStrut(50));
        pnlButton.add(btnCancel);

        pnlMain.add(Box.createVerticalStrut(60));

        /* Add components to panel */
        this.add(pnlHeader, BorderLayout.NORTH);
        this.add(pnlMain);

        /* Set the properties of components */
        htfId.setMaximumSize(new Dimension(300, 40));
        htfId.setPreferredSize(new Dimension(300, 40));
        hpfPassword.setMaximumSize(new Dimension(300, 40));
        hpfPassword.setPreferredSize(new Dimension(300, 40));
        htfEmail.setMaximumSize(new Dimension(300, 40));
        htfEmail.setPreferredSize(new Dimension(300, 40));

        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(100, 35));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.setMaximumSize(new Dimension(100, 35));

        setBackground(Color.GRAY);

        btnRegister.addActionListener(e ->{
            String id = htfId.getText().trim();
            String pwd = new String(hpfPassword.getPassword());
            String email = htfEmail.getText().trim();

            if (id.isEmpty()) {

                return;
            }
            if (pwd.isEmpty()) {
                return;
            }
            if (email.isEmpty()){
                return;
            }
            if (controller.register(id, pwd, email)) {
                System.out.println("Register complete");

            } else {
                System.out.println("Register failed");
            }

        });

    }   // -- End of function (initialize)
}   // -- End of class
