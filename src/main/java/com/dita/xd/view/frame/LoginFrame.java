package com.dita.xd.view.frame;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.manager.LoginTransitionMgr;
import com.dita.xd.view.panel.ChangePasswordPanel;
import com.dita.xd.view.panel.FindPasswordPanel;
import com.dita.xd.view.panel.LoginPanel;
import com.dita.xd.view.panel.RegisterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFrame extends JFrame implements LocaleChangeListener {
    private final LoginTransitionMgr mgr;

    private ResourceBundle localeBundle;

    private final ChangePasswordPanel changePwdPane;
    private final FindPasswordPanel findPane;
    private final LoginPanel loginPane;
    private final RegisterPanel registerPane;

    private JLabel lblSelection;

    public LoginFrame(Locale locale) {
        this.localeBundle = ResourceBundle.getBundle("language", locale);

        findPane = new FindPasswordPanel(locale);
        loginPane = new LoginPanel(locale);
        registerPane = new RegisterPanel(locale);
        changePwdPane = new ChangePasswordPanel(locale);

        mgr = LoginTransitionMgr.getInstance();

        /* Initialize components */
        initialize();

        onLocaleChanged(locale);
    }   // -- End of constructor

    private void initialize() {
        /* Variable declaration */
        CardLayout clMain = new CardLayout();

        JComboBox<String> cmbLocale = new JComboBox<>();

        JPanel headerPane = new JPanel();
        JPanel localePane = new JPanel();
        JPanel localeSubPane = new JPanel();
        JPanel mainPane = new JPanel();

        lblSelection = new JLabel();

        /* Set the properties of initialize */
        this.setBounds(100, 100, 450, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        /* Set the properties of sub panels */

        headerPane.setLayout(new BorderLayout());
        localePane.setLayout(new BorderLayout());
        localeSubPane.setLayout(new BorderLayout());
        mainPane.setLayout(clMain);

        headerPane.add(localePane, BorderLayout.NORTH);
        headerPane.add(new JImageView("resources/images/logo.png"));

        /* Set CardLayout controller */
        mgr.setChgPane(changePwdPane);
        mgr.setMainFrame(this);
        mgr.setMainLayout(clMain);
        mgr.setMainPane(mainPane);

        cmbLocale.addItem("한국어");
        cmbLocale.addItem("日本語");
        cmbLocale.addItem("中文");
        cmbLocale.addItem("English");
        cmbLocale.addItem("Español");

        cmbLocale.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) e.getItem();

                switch (item) {
                    case "한국어" -> onLocaleChanged(Locale.KOREA);
                    case "日本語" -> onLocaleChanged(Locale.JAPAN);
                    case "中文" -> onLocaleChanged(Locale.CHINA);
                    case "English" -> onLocaleChanged(Locale.US);
                    case "Español" -> onLocaleChanged(new Locale("es_ES"));
                }
            }
        });

        lblSelection.setFont(lblSelection.getFont().deriveFont(Font.BOLD));

        loadText();

        localePane.add(localeSubPane, BorderLayout.EAST);

        localeSubPane.add(lblSelection, BorderLayout.WEST);
        localeSubPane.add(cmbLocale);

        mainPane.add(loginPane, "login");
        mainPane.add(registerPane, "register");
        mainPane.add(findPane, "find");
        mainPane.add(changePwdPane, "change");

        this.add(headerPane, BorderLayout.NORTH);
        this.add(mainPane);
    }

    private void loadText() {
        setTitle(localeBundle.getString("login.title"));
        lblSelection.setText(localeBundle.getString("login.label.selection"));
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);

        loginPane.onLocaleChanged(newLocale);
        registerPane.onLocaleChanged(newLocale);
        findPane.onLocaleChanged(newLocale);
        changePwdPane.onLocaleChanged(newLocale);

        loadText();
    }
}
