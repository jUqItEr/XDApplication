package com.dita.xd.view.frame;

import com.dita.xd.controller.LoginController;
import com.dita.xd.controller.RegisterController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.util.filter.IDFilter;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.dialog.EmptyDialog;

import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFrame extends JFrame implements LocaleChangeListener {
    CardLayout clMain;
    JPanel headerPane;
    JPanel localePane;
    JPanel mainPane;

    FindPasswordPanel findPane;
    LoginPanel loginPane;
    RegisterPanel registerPane;

    Locale currentLocale;

    private ResourceBundle localeBundle;
    private String title;

    public LoginFrame() {
        currentLocale = Locale.CHINA;
        changeLocale(currentLocale);

        /* Initialize components */
        initialize();
    }

    private void initialize() {
        headerPane = new JPanel();
        localePane = new JPanel();
        mainPane = new JPanel();

        findPane = new FindPasswordPanel(currentLocale);
        loginPane = new LoginPanel(currentLocale);
        registerPane = new RegisterPanel(currentLocale);

        /* Set the properties of initialize */
        this.setBounds(100, 100, 450, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle(title);
        this.setLayout(new BorderLayout());

        // DEPRECATED
        localePane.setBackground(Color.PINK);

        /* Set the properties of sub panels */
        headerPane.setLayout(new BorderLayout());
        localePane.setLayout(new BorderLayout());
        mainPane.setLayout(clMain = new CardLayout());

        headerPane.add(localePane, BorderLayout.NORTH);
        headerPane.add(new JImageView("resources/images/logo.png"));

        localePane.add(new JButton(localeBundle.getString("login.header.language")), BorderLayout.EAST);

        mainPane.add(loginPane, "login");
        mainPane.add(registerPane, "register");
        mainPane.add(findPane, "find");

        this.add(headerPane, BorderLayout.NORTH);
        this.add(mainPane);
    }

    @Override
    public void changeLocale(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        onLocaleChanged(locale);
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        this.localeBundle = ResourceBundle.getBundle("language", newLocale);
        this.title = localeBundle.getString("login.title");
    }

    /**
     * <p>The Login Panel</p>
     *
     * @author jUqItEr (Ki-seok Kang)
     * @version 1.1.2
     * @see CardLayout
     */
    class LoginPanel extends JPanel implements LocaleChangeListener {
        private final LoginController controller;
        private ResourceBundle localeBundle;
        private Locale currentLocale;

        /* Variables declaration */
        JButton btnLogin;
        JButton btnRegister;

        JHintPasswordField hpfPassword;
        JHintTextField htfId;

        JLabel lblFindPassword;
        JPanel pnlMain;

        public LoginPanel(Locale locale) {
            controller = new LoginController();
            localeBundle = ResourceBundle.getBundle("language", locale);

            initialize();

            /* Change the current locale. */
            changeLocale(locale);

        }

        private void initialize() {
            /* Set the default properties to parent panel. */
            setLayout(new BorderLayout());

            /* Load to memory */
            btnLogin = new JButton();
            btnRegister = new JButton();

            hpfPassword = new JHintPasswordField();
            htfId = new JHintTextField();

            lblFindPassword = new JLabel();

            pnlMain = new JPanel();

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
                    EmptyDialog idDialog = new EmptyDialog(localeBundle.getString("login.field.hint.id"));
                    idDialog.setVisible(true);
                    return;
                }
                if (pwd.isEmpty()) {
                    EmptyDialog pwdDialog = new EmptyDialog(localeBundle.getString("login.field.hint.password"));
                    pwdDialog.setVisible(true);
                    return;
                }
                if (controller.login(id, pwd)) {
                    System.out.println("Login complete");

                } else {
                    System.out.println("Login failed");
                }
            });

            btnRegister.addActionListener(e -> {
                clMain.show(mainPane, "register");
            });

            lblFindPassword.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    clMain.show(mainPane, "find");
                }
            });

            // DocumentFilter
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

        private void loadText() {
            btnLogin.setText(localeBundle.getString("login.button.login"));
            btnRegister.setText(localeBundle.getString("login.button.register"));

            hpfPassword.setHint(localeBundle.getString("login.field.hint.password"));
            htfId.setHint(localeBundle.getString("login.field.hint.id"));

            lblFindPassword.setText(localeBundle.getString("login.label.password"));
        }

        @Override
        public void changeLocale(Locale locale) {
            currentLocale = locale;
            localeBundle = ResourceBundle.getBundle("language", locale);
            onLocaleChanged(locale);
            loadText();
        }

        @Override
        public void onLocaleChanged(Locale newLocale) {
            LocaleChangeListener.broadcastLocaleChanged(newLocale, LoginPanel.this);
        }
    }

    /**
     * <p>The Register Panel</p>
     *
     * @author DelynMk2 (Hyeong-won Park)
     * @version 1.0.2
     * @see CardLayout
     * */
    class RegisterPanel extends JPanel implements LocaleChangeListener {
        private final RegisterController controller;
        private ResourceBundle localeBundle;
        private Locale currentLocale;

        /* Variables declaration */
        JButton btnCancel;
        JButton btnRegister;

        JHintPasswordField hpfPassword;
        JHintTextField htfEmail;
        JHintTextField htfId;

        JPanel pnlButton;
        JPanel pnlMain;

        public RegisterPanel(Locale locale) {
            controller = new RegisterController();
            localeBundle = ResourceBundle.getBundle("language", locale);

            initialize();

            changeLocale(locale);
        }   // -- End of constructor

        private void initialize() {
            setLayout(new BorderLayout());

            /* Load to memory */
            btnCancel = new JButton();
            btnRegister = new JButton();

            hpfPassword = new JHintPasswordField();
            htfEmail = new JHintTextField();
            htfId = new JHintTextField();

            pnlButton = new JPanel();
            pnlMain = new JPanel();

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
                clMain.show(mainPane, "login");
            });
            btnRegister.addActionListener(e -> {
                String id = htfId.getText().trim();
                String pwd = new String(hpfPassword.getPassword());
                String email = htfEmail.getText().trim();

                if (id.isEmpty()) {
                    EmptyDialog idDialog = new EmptyDialog(localeBundle.getString("register.field.hint.id"));
                    idDialog.setVisible(true);
                    return;
                }
                if (pwd.isEmpty()) {
                    EmptyDialog pwdDialog = new EmptyDialog(localeBundle.getString("register.field.hint.password"));
                    pwdDialog.setVisible(true);
                    return;
                }
                if (email.isEmpty()) {
                    EmptyDialog emailDialog = new EmptyDialog(localeBundle.getString("register.field.hint.email"));
                    emailDialog.setVisible(true);
                    return;
                }

                if (isValidEmail(email)) {
                    System.out.println("Oh");
                } else {
                    System.out.println("My god");
                }
//                if (controller.register(id, pwd, email)) {
                if (true) {
                    System.out.println("Register complete");
                    clMain.show(mainPane, "login");
                } else {
                    System.out.println("Register failed");
                }

            });

        }   // -- End of function (initialize)

        private void loadText() {
            btnCancel.setText(localeBundle.getString("register.button.cancel"));
            btnRegister.setText(localeBundle.getString("register.button.register"));

            hpfPassword.setHint(localeBundle.getString("register.field.hint.password"));
            htfEmail.setHint(localeBundle.getString("register.field.hint.email"));
            htfId.setHint(localeBundle.getString("register.field.hint.id"));
        }

        private boolean isValidEmail(String email) {
            boolean result = true;

            try {
                InternetAddress emailAddr = new InternetAddress(email);
                emailAddr.validate();
            } catch (AddressException e) {
                result = false;
            }
            return result;
        }

        @Override
        public void changeLocale(Locale locale) {
            currentLocale = locale;
            localeBundle = ResourceBundle.getBundle("language", locale);
            onLocaleChanged(locale);
            loadText();
        }

        @Override
        public void onLocaleChanged(Locale newLocale) {
            LocaleChangeListener.broadcastLocaleChanged(newLocale, RegisterPanel.this);
        }
    }   // -- End of class

    class FindPasswordPanel extends JPanel implements LocaleChangeListener {

        //private final FindPasswordController;
        private Locale currentLocale;
        private ResourceBundle localeBundle;

        /* Variables declaration */
        JButton btnCancel;
        JButton btnEmailAuth;

        JHintTextField htfEmail;
        JHintTextField htfId;

        JPanel pnlButton;
        JPanel pnlMain;

        public FindPasswordPanel(Locale locale) {
            //controller = new FindPasswordController;
            localeBundle = ResourceBundle.getBundle("language", locale);

            initialize();

            changeLocale(locale);
        }

        private void initialize() {
            setLayout(new BorderLayout());

            /* Load to memory */
            btnCancel = new JButton();
            btnEmailAuth = new JButton();

            htfEmail = new JHintTextField();
            htfId = new JHintTextField();

            pnlButton = new JPanel();
            pnlMain = new JPanel();

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
                clMain.show(mainPane, "login");
            });
            btnEmailAuth.addActionListener(e -> {
                String id = htfId.getText().trim();
                String email = htfEmail.getText().trim();

                if (id.isEmpty()) {
                    EmptyDialog idDialog = new EmptyDialog(localeBundle.getString("register.field.hint.id"));
                    idDialog.setVisible(true);
                    return;
                }
                if (email.isEmpty()) {
                    EmptyDialog emailDialog = new EmptyDialog(localeBundle.getString("register.field.hint.email"));
                    emailDialog.setVisible(true);
                    return;
                }

//                if (controller.register(id, pwd, email)) {
                if (true) {

                } else {

                }

            });

        }

        private void loadText() {
            btnCancel.setText(localeBundle.getString("register.button.cancel"));
//            btnEmailAuth.setText(localeBundle.getString("register.button.register"));
            btnEmailAuth.setText("이메일 인증"); /* 임시 데이터 */

            htfEmail.setHint(localeBundle.getString("register.field.hint.email"));
            htfId.setHint(localeBundle.getString("register.field.hint.id"));
        }

        @Override
        public void changeLocale(Locale locale) {
            currentLocale = locale;
            localeBundle = ResourceBundle.getBundle("language", locale);
            onLocaleChanged(locale);
            loadText();
        }

        @Override
        public void onLocaleChanged(Locale newLocale) {
            LocaleChangeListener.broadcastLocaleChanged(newLocale, FindPasswordPanel.this);
        }
    }

    class ChangePasswordPanel extends JPanel implements LocaleChangeListener{
        private Locale currentLocale;
        private ResourceBundle localeBundle;

        public ChangePasswordPanel(Locale locale){

        }
    }
}
