package com.dita.xd.view.panel;

import com.dita.xd.controller.LoginController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.util.filter.IDFilter;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.dialog.PlainDialog;
import com.dita.xd.view.frame.MainFrame;
import com.dita.xd.view.manager.LoginLayoutMgr;

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
    private final LoginLayoutMgr mgr;

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
        mgr = LoginLayoutMgr.getInstance();

        initialize();

        /* Change the current locale. */
        onLocaleChanged(locale);
    }

    private void initialize() {
        /* Set the default properties to parent panel. */
        setLayout(new BorderLayout());

        /* Variables declaration */
        JPanel mainPane = new JPanel();

        btnLogin = new JButton();
        btnRegister = new JButton();

        hpfPassword = new JHintPasswordField();
        htfId = new JHintTextField();

        lblFindPassword = new JLabel();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        /* Set the properties of components */
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(300, 35));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(300, 35));

        lblFindPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFindPassword.setMaximumSize(new Dimension(300, 24));
        lblFindPassword.setHorizontalAlignment(JLabel.CENTER);
        lblFindPassword.setFont(lblFindPassword.getFont().deriveFont(16f));

        htfId.setMaximumSize(new Dimension(300, 40));
        htfId.setPreferredSize(new Dimension(300, 40));
        hpfPassword.setMaximumSize(new Dimension(300, 40));
        hpfPassword.setPreferredSize(new Dimension(300, 40));

        /* Add listeners on components */
        btnLogin.addActionListener(e -> {
            login();
        });
        btnRegister.addActionListener(e -> {
            clear();
            mgr.show("register");
        });
        htfId.addActionListener(e -> {
            String id = htfId.getText().trim();

            if (!id.isEmpty()) {
                hpfPassword.requestFocus();
            }
        });
        hpfPassword.addActionListener( e -> login());
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
        mainPane.add(Box.createVerticalGlue());
        mainPane.add(htfId);
        mainPane.add(Box.createVerticalStrut(10));
        mainPane.add(hpfPassword);
        mainPane.add(Box.createVerticalStrut(30));
        mainPane.add(btnLogin);
        mainPane.add(Box.createVerticalStrut(20));
        mainPane.add(lblFindPassword);
        mainPane.add(Box.createVerticalStrut(100));
        mainPane.add(btnRegister);
        mainPane.add(Box.createVerticalStrut(50));

        /* Add components to panel */
        this.add(mainPane);
    }

    private void clear() {
        htfId.setText("");
        hpfPassword.setText("");
    }

    private void loadText() {
        btnLogin.setText(localeBundle.getString("login.button.login"));
        btnRegister.setText(localeBundle.getString("login.button.register"));

        hpfPassword.setHint(localeBundle.getString("login.field.hint.password"));
        hpfPassword.repaint();
        htfId.setHint(localeBundle.getString("login.field.hint.id"));
        htfId.repaint();

        lblFindPassword.setText(localeBundle.getString("login.label.password"));
    }

    private void login() {
        PlainDialog dialog = null;
        boolean isError = false;
        String id = htfId.getText().trim();
        String pwd = new String(hpfPassword.getPassword());

        if (id.isEmpty()) {
            dialog = new PlainDialog(
                    currentLocale,
                    String.format(localeBundle.getString("dialog.plain.message"),
                            localeBundle.getString("login.field.hint.id")),
                    PlainDialog.MessageType.INFORMATION
            );
            isError = true;
        } else if (pwd.isEmpty()) {
            dialog = new PlainDialog(
                    currentLocale,
                    String.format(localeBundle.getString("dialog.plain.message"),
                            localeBundle.getString("login.field.hint.password")),
                    PlainDialog.MessageType.INFORMATION
            );
            isError = true;
        } else if (!controller.login(id, pwd)) {
            dialog = new PlainDialog(
                    currentLocale,
                    localeBundle.getString("dialog.plain.message.error.no_exists"),
                    PlainDialog.MessageType.INFORMATION
            );
            isError = true;
        }
        if (isError) {
            dialog.setVisible(true);
        } else {
            MainFrame frame = new MainFrame(currentLocale);
            frame.setVisible(true);
            mgr.dispose();
            System.out.println("Login complete");
        }
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}