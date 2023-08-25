package com.dita.xd.view.panel;

import com.dita.xd.controller.RegisterController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.dialog.PlainDialog;
import com.dita.xd.view.manager.LoginTransitionMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChangePasswordPanel extends JPanel implements LocaleChangeListener {
    private final RegisterController controller;

    private final LoginTransitionMgr mgr;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private String id;

    private JButton btnCancel;
    private JButton btnPasswordChange;
    private JHintPasswordField hpfPassword;
    private JHintPasswordField hpfConfirmedPassword;

    private JLabel lblTitle;


    public ChangePasswordPanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new RegisterController();
        mgr = LoginTransitionMgr.getInstance();

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        /* Load to memory */
        btnCancel = new JButton();
        btnPasswordChange = new JButton();

        hpfPassword = new JHintPasswordField();
        hpfConfirmedPassword = new JHintPasswordField();

        JPanel pnlButton = new JPanel();
        JPanel pnlMain = new JPanel();

        lblTitle = new JLabel();
        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.X_AXIS));

        /* Add components to sub panel */

        pnlMain.add(Box.createVerticalGlue());
        pnlMain.add(lblTitle);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(hpfPassword);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(hpfConfirmedPassword);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(pnlButton);

        pnlButton.add(btnPasswordChange);
        pnlButton.add(Box.createHorizontalStrut(20));
        pnlButton.add(btnCancel);

        pnlMain.add(Box.createVerticalStrut(60));

        /* Add components to panel */
        this.add(pnlMain);

        /* Set the properties of components */
        hpfPassword.setMaximumSize(new Dimension(300, 40));
        hpfPassword.setPreferredSize(new Dimension(300, 40));
        hpfConfirmedPassword.setMaximumSize(new Dimension(300, 40));
        hpfConfirmedPassword.setPreferredSize(new Dimension(300, 40));

        btnPasswordChange.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPasswordChange.setMaximumSize(new Dimension(120, 35));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.setMaximumSize(new Dimension(120, 35));

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setMaximumSize(new Dimension(300, 24));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);

        setBackground(Color.GRAY);

        btnCancel.addActionListener(e -> {
            clear();
            mgr.show("login");
        });
        btnPasswordChange.addActionListener(e -> {
            String pwd = new String(hpfPassword.getPassword());
            String confirmedPwd = new String(hpfConfirmedPassword.getPassword());

            if (pwd.isEmpty()) {
                PlainDialog pwdDialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("register.field.hint.password"),
                        PlainDialog.MessageType.INFORMATION
                );
                pwdDialog.setVisible(true);
                return;
            }
            if (confirmedPwd.isEmpty()) {
                PlainDialog confirmedPwdDialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("register.field.hint.password"),
                        PlainDialog.MessageType.INFORMATION
                );
                confirmedPwdDialog.setVisible(true);
                return;
            }
            if (!pwd.equals(confirmedPwd)) {
                PlainDialog notEqualsPwd = new PlainDialog(
                        currentLocale,
                        "비밀번호가 같지 않습니다.",
                        PlainDialog.MessageType.INFORMATION
                );
                notEqualsPwd.setVisible(true);
                return;
            }
            if (controller.changePassword(id, pwd)) {
                PlainDialog changedDialog = new PlainDialog(
                        currentLocale,
                        "비밀번호가 변경되었습니다.",
                        PlainDialog.MessageType.INFORMATION
                );
                changedDialog.setVisible(true);
                clear();
                mgr.show("login");
            } else {
                System.out.println("Password change err");
            }

        });
    }

    private void clear() {
        hpfPassword.setText("");
        hpfConfirmedPassword.setText("");
    }

    private void loadText() {
        btnCancel.setText(localeBundle.getString("register.button.cancel"));
//            btnEmailAuth.setText(localeBundle.getString("register.button.register"));
        btnPasswordChange.setText("비밀번호 변경"); /* 임시 데이터 */

        hpfPassword.setHint(localeBundle.getString("register.field.hint.password"));
//            htfConfirmedPassword.setHint(localeBundle.getString(" ??? "));
        hpfConfirmedPassword.setHint(" 비밀번호 확인 ");

        lblTitle.setText("비밀번호 변경");
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, ChangePasswordPanel.this);
        loadText();
    }
}
