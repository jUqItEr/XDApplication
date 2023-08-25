package com.dita.xd.view.panel;

import com.dita.xd.controller.LoginController;
import com.dita.xd.controller.MailController;
import com.dita.xd.controller.RegisterController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.dialog.MailCodeDialog;
import com.dita.xd.view.dialog.PlainDialog;
import com.dita.xd.view.manager.LoginTransitionMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class FindPasswordPanel extends JPanel implements LocaleChangeListener {
    private final LoginController loginController;
    private final MailController mailController;
    private final RegisterController registerController;
    private final LoginTransitionMgr mgr;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    /* Variables declaration */
    private JButton btnCancel;
    private JButton btnEmailAuth;

    private JHintTextField htfEmail;
    private JHintTextField htfId;

    public FindPasswordPanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        loginController = new LoginController();
        mailController = new MailController();
        registerController = new RegisterController();
        mgr = LoginTransitionMgr.getInstance();

        initialize();

        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        /* Load to memory */
        btnCancel = new JButton();
        btnEmailAuth = new JButton();

        htfEmail = new JHintTextField();
        htfId = new JHintTextField();

        JPanel pnlButton = new JPanel();
        JPanel pnlMain = new JPanel();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.X_AXIS));

        /* Add components to sub panel */

        pnlMain.add(Box.createVerticalGlue());
        pnlMain.add(htfId);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(htfEmail);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(pnlButton);

        pnlButton.add(btnEmailAuth);
        pnlButton.add(Box.createHorizontalStrut(20));
        pnlButton.add(btnCancel);

        pnlMain.add(Box.createVerticalStrut(60));

        /* Add components to panel */
        this.add(pnlMain);

        /* Set the properties of components */
        htfId.setMaximumSize(new Dimension(300, 40));
        htfId.setPreferredSize(new Dimension(300, 40));
        htfEmail.setMaximumSize(new Dimension(300, 40));
        htfEmail.setPreferredSize(new Dimension(300, 40));

        btnEmailAuth.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEmailAuth.setMaximumSize(new Dimension(120, 35));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.setMaximumSize(new Dimension(120, 35));

        setBackground(Color.GRAY);

        btnCancel.addActionListener(e -> {
            clear();
            mgr.show("login");
        });
        btnEmailAuth.addActionListener(e -> {
            String id = htfId.getText().trim();
            String email = htfEmail.getText().trim();

            if (id.isEmpty()) {
                PlainDialog idDialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("register.field.hint.id"),
                        PlainDialog.MessageType.INFORMATION
                );
                idDialog.setVisible(true);
                return;
            }
            if (email.isEmpty()) {
                PlainDialog emailDialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("register.field.hint.email"),
                        PlainDialog.MessageType.INFORMATION
                );
                emailDialog.setVisible(true);
                return;
            }
            if (!registerController.hasId(id)) {
                PlainDialog emailDialog = new PlainDialog(
                        currentLocale,
                        "가입되지 않은 아이디 입니다.",
                        PlainDialog.MessageType.INFORMATION
                );
                emailDialog.setVisible(true);
                return;
            }
            if (!registerController.hasEmail(email)) {
                PlainDialog emailDialog = new PlainDialog(
                        currentLocale,
                        "가입되지 않은 이메일 입니다.",
                        PlainDialog.MessageType.INFORMATION
                );
                emailDialog.setVisible(true);
                return;
            }
            UserBean bean = loginController.getUser(id);

            if (loginController.checkEmail(bean, email)) {
                if (mailController.sendRequestCode(email)) {
                    MailCodeDialog mailCodeDialog = new MailCodeDialog(
                            currentLocale, email);
                    if (mailCodeDialog.showDialog()) {
                        mgr.setId(id);
                        clear();
                        mgr.show("change");
                    }
                }
            }

        });

    }

    private void clear() {
        htfId.setText("");
        htfEmail.setText("");
    }

    private void loadText() {
        btnCancel.setText(localeBundle.getString("register.button.cancel"));
        btnEmailAuth.setText("이메일 인증"); /* 임시 데이터 */

        htfEmail.setHint(localeBundle.getString("register.field.hint.email"));
        htfEmail.repaint();
        htfId.setHint(localeBundle.getString("register.field.hint.id"));
        htfId.repaint();
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
