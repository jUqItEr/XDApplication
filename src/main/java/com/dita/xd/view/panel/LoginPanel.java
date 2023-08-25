package com.dita.xd.view.panel;

import com.dita.xd.controller.LoginController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.util.filter.IDFilter;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.dialog.PlainDialog;
import com.dita.xd.view.frame.MainFrame;
import com.dita.xd.view.manager.LoginTransitionMgr;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>The Login Panel</p>
 *
 * @author jUqItEr (Ki-seok Kang)
 * @version 1.1.2
 * @see CardLayout
 */
public class LoginPanel extends JPanel implements LocaleChangeListener {
    private final LoginController controller;
    private final LoginTransitionMgr mgr;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    /* Variables declaration */
    private JButton btnLogin;
    private JButton btnRegister;

    private JHintPasswordField hpfPassword;
    private JHintTextField htfId;

    private JLabel lblFindPassword;

    public LoginPanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new LoginController();
        mgr = LoginTransitionMgr.getInstance();

        initialize();

        /* Change the current locale. */
        onLocaleChanged(locale);
    }

    private void initialize() {
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        /* Variables declaration */
        JPanel pnlMain = new JPanel();

        btnLogin = new JButton();
        btnRegister = new JButton();

        hpfPassword = new JHintPasswordField();
        htfId = new JHintTextField();

        lblFindPassword = new JLabel();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        /* Set the properties of components */
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(300, 35));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(300, 35));

        lblFindPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFindPassword.setMaximumSize(new Dimension(300, 24));
        lblFindPassword.setHorizontalAlignment(JLabel.CENTER);

        htfId.setMaximumSize(new Dimension(300, 40));
        htfId.setPreferredSize(new Dimension(300, 40));
        hpfPassword.setMaximumSize(new Dimension(300, 40));
        hpfPassword.setPreferredSize(new Dimension(300, 40));

        /* Add listeners on components */
        btnLogin.addActionListener(e -> {
            String id = htfId.getText().trim();
            String pwd = new String(hpfPassword.getPassword());

            if (id.isEmpty()) {
                PlainDialog idDialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("login.field.hint.id"),
                        PlainDialog.MessageType.INFORMATION
                );
                idDialog.setVisible(true);
                return;
            }
            if (pwd.isEmpty()) {
                PlainDialog pwdDialog = new PlainDialog(
                        currentLocale,
                        localeBundle.getString("login.field.hint.password"),
                        PlainDialog.MessageType.INFORMATION
                );
                pwdDialog.setVisible(true);
                return;
            }
            if (controller.login(id, pwd)) {
                MainFrame frame = new MainFrame(currentLocale);
                frame.setVisible(true);
                mgr.dispose();
                System.out.println("Login complete");

            } else {
                PlainDialog loginDialog = new PlainDialog(
                        currentLocale,
                        "아이디 혹은 비밀번호가 올바르지 않습니다.",
                        PlainDialog.MessageType.INFORMATION
                );
                loginDialog.setVisible(true);
            }
        });

        btnRegister.addActionListener(e -> {
            clear();
            mgr.show("register");
        });

        lblFindPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clear();
                mgr.show("find");
            }
        });

        /* Add DocumentFilter to 'htfId' */
        ((AbstractDocument) htfId.getDocument()).setDocumentFilter(new IDFilter());

        // Box Vertical Glue
        // Do not touch on these settings!
        pnlMain.add(Box.createVerticalGlue());
        pnlMain.add(htfId);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(hpfPassword);
        pnlMain.add(Box.createVerticalStrut(30));
        pnlMain.add(btnLogin);
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(lblFindPassword);
        pnlMain.add(Box.createVerticalStrut(100));
        pnlMain.add(btnRegister);
        pnlMain.add(Box.createVerticalStrut(50));

        /* Add components to panel */
        this.add(pnlMain);
    }

    private void clear() {
        htfId.setText("");
        hpfPassword.setText("");
    }

    private void loadText() {
        btnLogin.setText(localeBundle.getString("login.button.login"));
        btnRegister.setText(localeBundle.getString("login.button.register"));

        hpfPassword.setHint(localeBundle.getString("login.field.hint.password"));
        htfId.setHint(localeBundle.getString("login.field.hint.id"));

        lblFindPassword.setText(localeBundle.getString("login.label.password"));
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, LoginPanel.this);
        loadText();
    }
}