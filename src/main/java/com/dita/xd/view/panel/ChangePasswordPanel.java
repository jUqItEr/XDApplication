package com.dita.xd.view.panel;

import com.dita.xd.controller.RegisterController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.dialog.PlainDialog;
import com.dita.xd.view.manager.LoginLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChangePasswordPanel extends JPanel implements LocaleChangeListener {
    private final RegisterController controller;

    private final LoginLayoutMgr mgr;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private String id;

    private JButton btnCancel;
    private JButton btnChange;
    private JHintPasswordField hpfPwd;
    private JHintPasswordField hpfPwdConfirmed;

    private JLabel lblTitle;


    public ChangePasswordPanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new RegisterController();
        mgr = LoginLayoutMgr.getInstance();

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        /* Load to memory */
        btnCancel = new JButton();
        btnChange = new JButton();

        hpfPwd = new JHintPasswordField();
        hpfPwdConfirmed = new JHintPasswordField();

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
        pnlMain.add(hpfPwd);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(hpfPwdConfirmed);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(pnlButton);

        pnlButton.add(btnChange);
        pnlButton.add(Box.createHorizontalStrut(20));
        pnlButton.add(btnCancel);

        pnlMain.add(Box.createVerticalStrut(60));

        /* Add components to panel */
        this.add(pnlMain);

        /* Set the properties of components */
        hpfPwd.setMaximumSize(new Dimension(300, 40));
        hpfPwd.setPreferredSize(new Dimension(300, 40));
        hpfPwdConfirmed.setMaximumSize(new Dimension(300, 40));
        hpfPwdConfirmed.setPreferredSize(new Dimension(300, 40));

        btnChange.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChange.setMaximumSize(new Dimension(120, 35));
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
        btnChange.addActionListener(e -> {
            PlainDialog dialog = null;
            boolean isError = false;
            String pwd = new String(hpfPwd.getPassword());
            String confirmedPwd = new String(hpfPwdConfirmed.getPassword());

            if (pwd.isEmpty()) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("register.field.hint.password"),
                        PlainDialog.MessageType.INFORMATION
                );
                isError = true;
            } else if (confirmedPwd.isEmpty()) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("register.field.hint.password"),
                        PlainDialog.MessageType.INFORMATION
                );
                isError = true;
            } else if (!pwd.equals(confirmedPwd)) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("dialog.plain.message.error.password"),
                        PlainDialog.MessageType.INFORMATION
                );
                isError = true;
            }

            if (isError) {
                dialog.setVisible(true);
            } else {
                if (controller.changePassword(id, pwd)) {
                    dialog = new PlainDialog(
                            currentLocale,
                            localeBundle.getString("dialog.plain.message.ok.password_changed"),
                            PlainDialog.MessageType.INFORMATION
                    );
                    dialog.setVisible(true);
                    clear();
                    mgr.show("login");
                } else {
                    System.err.println("Password change err");
                }
            }
        });
    }

    private void clear() {
        hpfPwd.setText("");
        hpfPwdConfirmed.setText("");
    }

    private void loadText() {
        btnCancel.setText(localeBundle.getString("register.button.cancel"));
        btnChange.setText(localeBundle.getString("change.button.change"));

        hpfPwd.setHint(localeBundle.getString("change.field.hint.password"));
        hpfPwd.repaint();
        hpfPwdConfirmed.setHint(localeBundle.getString("change.field.hint.password_confirm"));
        hpfPwdConfirmed.repaint();

        lblTitle.setText(localeBundle.getString("change.label.title"));
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
