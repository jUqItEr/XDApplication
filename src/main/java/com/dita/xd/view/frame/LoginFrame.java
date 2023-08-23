package com.dita.xd.view.frame;

import com.dita.xd.controller.LoginController;
import com.dita.xd.controller.RegisterController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.util.filter.IDFilter;
import com.dita.xd.view.base.JHintPasswordField;
import com.dita.xd.view.base.JHintTextField;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

public class LoginFrame extends JFrame implements LocaleChangeListener {
    public CardLayout clMain;

    private ResourceBundle localeBundle;
    private String title;

    public LoginFrame() {
        Locale currentLocale = Locale.CHINA;
        getLocaleString(currentLocale);

        /* Initialize components */
        initialize();
    }

    private void initialize() {
        JPanel headerPane = new JPanel();
        JPanel localePane = new JPanel();
        JPanel mainPane = new JPanel();

        LoginPanel loginPane = new LoginPanel();
        RegisterPanel registerPane = new RegisterPanel();

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
        localePane.add(new JButton("Language"), BorderLayout.EAST);
        mainPane.add(loginPane);
        mainPane.add(registerPane);

        this.add(headerPane, BorderLayout.NORTH);
        this.add(mainPane);
    }

    @Override
    public void getLocaleString(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        onLocaleChanged(locale);
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        this.localeBundle = ResourceBundle.getBundle("language", newLocale);
        this.title = localeBundle.getString("login.title");
    }



    class LoginPanel extends JPanel implements LocaleChangeListener {
        private final LoginController controller;
        private ResourceBundle localeBundle;

        public LoginPanel() {
            /* Change the current locale. */
            Locale currentLocale = Locale.KOREAN;
            getLocaleString(currentLocale);

            controller = new LoginController();

            initialize();
        }

        private void initialize() {
            /* Set the default properties to parent panel. */
            setLayout(new BorderLayout());

            /* Variables declaration */
            JButton btnLogin = new JButton();
            JButton btnRegister = new JButton();

            JHintPasswordField hpfPassword = new JHintPasswordField();
            JHintTextField htfId = new JHintTextField();

            JLabel lblFindPassword = new JLabel();
            JPanel pnlMain = new JPanel();

            /* Set the localized texts. */
            btnLogin.setText(localeBundle.getString("login.button.login"));
            btnRegister.setText(localeBundle.getString("login.button.register"));

            hpfPassword.setHint(localeBundle.getString("login.field.hint.password"));
            htfId.setHint(localeBundle.getString("login.field.hint.id"));

            lblFindPassword.setText(localeBundle.getString("login.label.password"));

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

                    return;
                }
                if (pwd.isEmpty()) {
                    return;
                }
                if (controller.login(id, pwd)) {
                    System.out.println("Login complete");

                } else {
                    System.out.println("Login failed");
                }
            });

            btnRegister.addActionListener(e -> {

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

        @Override
        public void getLocaleString(Locale locale) {
            localeBundle = ResourceBundle.getBundle("language", locale);
            onLocaleChanged(locale);
        }

        @Override
        public void onLocaleChanged(Locale newLocale) {
            LocaleChangeListener.broadcastLocaleChanged(newLocale, LoginPanel.this);
        }
    }

    static class RegisterPanel extends JPanel {

        private final RegisterController controller;
        public RegisterPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            controller = new RegisterController();

            initialize();
        }   // -- End of constructor

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

}
