package com.dita.xd.view.panel.login;

import com.dita.xd.controller.LoginController;
import com.dita.xd.controller.MailController;
import com.dita.xd.controller.RegisterController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.dialog.MailCodeDialog;
import com.dita.xd.view.dialog.PlainDialog;
import com.dita.xd.view.manager.LoginLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class FindPasswordPanel extends JPanel implements LocaleChangeListener {
    private final LoginController loginController;
    private final MailController mailController;
    private final RegisterController registerController;
    private final LoginLayoutMgr mgr;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    /* Variables declaration */
    private JButton btnCancel;
    private JButton btnAuth;

    private JHintTextField htfEmail;
    private JHintTextField htfId;

    public FindPasswordPanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        loginController = new LoginController();
        mailController = new MailController();
        registerController = new RegisterController();
        mgr = LoginLayoutMgr.getInstance();

        initialize();

        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        /* Load to memory */
        btnCancel = new JButton();
        btnAuth = new JButton();

        htfEmail = new JHintTextField();
        htfId = new JHintTextField();

        JPanel buttonPane = new JPanel();
        JPanel mainPane = new JPanel();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));

        /* Add components to sub panel */
        mainPane.add(Box.createVerticalGlue());
        mainPane.add(htfId);
        mainPane.add(Box.createVerticalStrut(10));
        mainPane.add(htfEmail);
        mainPane.add(Box.createVerticalStrut(10));
        mainPane.add(buttonPane);

        buttonPane.add(btnAuth);
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(btnCancel);

        mainPane.add(Box.createVerticalStrut(60));

        /* Add components to panel */
        this.add(mainPane);

        /* Set the properties of components */
        htfId.setMaximumSize(new Dimension(300, 40));
        htfId.setPreferredSize(new Dimension(300, 40));
        htfEmail.setMaximumSize(new Dimension(300, 40));
        htfEmail.setPreferredSize(new Dimension(300, 40));

        btnAuth.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAuth.setMaximumSize(new Dimension(120, 35));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.setMaximumSize(new Dimension(120, 35));

        setBackground(Color.GRAY);

        btnAuth.addActionListener(e -> {
            PlainDialog dialog = null;
            UserBean bean;
            boolean isError = false;
            String id = htfId.getText().trim();
            String email = htfEmail.getText().trim();

            if (id.isEmpty()) {
                dialog = new PlainDialog(
                        currentLocale,
                        String.format(localeBundle.getString("dialog.plain.message"),
                                localeBundle.getString("register.field.hint.id")),
                        PlainDialog.MessageType.INFORMATION
                );
                isError = true;
            } else if (email.isEmpty()) {
                dialog = new PlainDialog(
                        currentLocale,
                        String.format(localeBundle.getString("dialog.plain.message"),
                                localeBundle.getString("register.field.hint.email")),
                        PlainDialog.MessageType.INFORMATION
                );
                isError = true;
            } else if (!mailController.isValidEmail(email)) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("dialog.plain.message.error.email_format"),
                        PlainDialog.MessageType.ERROR
                );
                isError = true;
            } else if (!registerController.hasId(id)) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("dialog.plain.message.error.id"),
                        PlainDialog.MessageType.ERROR
                );
                isError = true;
            } else if (!registerController.hasEmail(email)) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("dialog.plain.message.error.email"),
                        PlainDialog.MessageType.ERROR
                );
                isError = true;
            }
            bean = loginController.getUser(id);

            if (isError) {
                dialog.setVisible(true);
            } else if (loginController.checkEmail(bean, email)) {
                if (mailController.sendRequestCode(email)) {
                    MailCodeDialog mailCodeDialog = new MailCodeDialog(currentLocale, email);

                    if (mailCodeDialog.showDialog()) {
                        mgr.setId(id);
                        clear();
                        mgr.show("change");
                    }
                }
            }
        });
        btnCancel.addActionListener(e -> {
            clear();
            mgr.show("login");
        });
    }

    private void clear() {
        htfId.setText("");
        htfEmail.setText("");
    }

    private void loadText() {
        btnAuth.setText(localeBundle.getString("find.button.auth"));
        btnCancel.setText(localeBundle.getString("register.button.cancel"));

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
