package com.dita.xd.view.panel;

import com.dita.xd.controller.MailController;
import com.dita.xd.controller.RegisterController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.dialog.MailCodeDialog;
import com.dita.xd.view.dialog.PlainDialog;
import com.dita.xd.view.manager.LoginLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>The Register Panel</p>
 *
 * @author DelynMk2 (Hyeong-won Park)
 * @version 1.0.2
 * @see CardLayout
 */
public class RegisterPanel extends JPanel implements LocaleChangeListener {
    private final RegisterController registerController;
    private final MailController mailController;
    private final LoginLayoutMgr mgr;

    private ResourceBundle localeBundle;
    private Locale currentLocale;

    /* Variables declaration */
    private JButton btnCancel;
    private JButton btnRegister;

    private JHintPasswordField hpfPassword;
    private JHintTextField htfEmail;
    private JHintTextField htfId;

    public RegisterPanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        registerController = new RegisterController();
        mailController = new MailController();
        mgr = LoginLayoutMgr.getInstance();

        initialize();

        onLocaleChanged(locale);
    }   // -- End of constructor

    private void initialize() {
        setLayout(new BorderLayout());

        /* Load to memory */
        btnCancel = new JButton();
        btnRegister = new JButton();

        hpfPassword = new JHintPasswordField();
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
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(hpfPassword);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(htfEmail);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(pnlButton);

        pnlButton.add(btnRegister);
        pnlButton.add(Box.createHorizontalStrut(20));
        pnlButton.add(btnCancel);

        pnlMain.add(Box.createVerticalStrut(60));

        /* Add components to panel */
        this.add(pnlMain);

        /* Set the properties of components */
        htfId.setMaximumSize(new Dimension(300, 40));
        htfId.setPreferredSize(new Dimension(300, 40));
        hpfPassword.setMaximumSize(new Dimension(300, 40));
        hpfPassword.setPreferredSize(new Dimension(300, 40));
        htfEmail.setMaximumSize(new Dimension(300, 40));
        htfEmail.setPreferredSize(new Dimension(300, 40));

        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(120, 35));
        btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancel.setMaximumSize(new Dimension(120, 35));

        setBackground(Color.GRAY);

        btnCancel.addActionListener(e -> {
            clear();
            mgr.show("login");
        });
        btnRegister.addActionListener(e -> {
            PlainDialog dialog = null;
            boolean isError = false;
            String id = htfId.getText().trim();
            String pwd = new String(hpfPassword.getPassword());
            String email = htfEmail.getText().trim();

            if (id.isEmpty()) {
                dialog = new PlainDialog(
                        currentLocale,
                        String.format(localeBundle.getString("dialog.plain.message"),
                                localeBundle.getString("register.field.hint.id")),
                        PlainDialog.MessageType.INFORMATION
                );
                isError = true;
            } else if (pwd.isEmpty()) {
                dialog = new PlainDialog(
                        currentLocale,
                        String.format(localeBundle.getString("dialog.plain.message"),
                                localeBundle.getString("register.field.hint.password")),
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
            } else if (registerController.hasId(id)) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("dialog.plain.message.error.id_exists"),
                        PlainDialog.MessageType.ERROR
                );
                isError = true;
            } else if (registerController.hasEmail(email)) {
                dialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("dialog.plain.message.error.email_exists"),
                        PlainDialog.MessageType.ERROR
                );
                isError = true;
            }   // End of if-else if (Validation)

            if (isError) {
                dialog.setVisible(true);
            } else if (mailController.sendRequestCode(email)) {
                MailCodeDialog mailCodeDialog = new MailCodeDialog(currentLocale, email);

                if (mailCodeDialog.showDialog()) {
                    if (registerController.register(id, pwd, email)) {
                        PlainDialog registerDialog = new PlainDialog(
                                currentLocale,
                                "회원가입 완료",
                                PlainDialog.MessageType.INFORMATION
                        );
                        registerDialog.setVisible(true);
                        clear();
                        System.out.println("Register complete");
                        mgr.show("login");
                    }   // -- End of if (registerController.register)
                } else {
                    System.err.println("Register failed");
                }   // -- End of if (mailCodeDialog.showDialog)
            }   // -- End of if (mailController.sendRequestCode)

        });

    }   // -- End of function (initialize)

    private void clear() {
        htfId.setText("");
        htfEmail.setText("");
        hpfPassword.setText("");
    }

    private void loadText() {
        btnCancel.setText(localeBundle.getString("register.button.cancel"));
        btnRegister.setText(localeBundle.getString("register.button.register"));

        hpfPassword.setHint(localeBundle.getString("register.field.hint.password"));
        hpfPassword.repaint();
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
}   // -- End of class