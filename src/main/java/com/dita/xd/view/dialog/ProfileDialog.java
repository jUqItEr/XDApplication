package com.dita.xd.view.dialog;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.manager.ProfileLayoutMgr;
import com.dita.xd.view.panel.NicknamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfileDialog extends JDialog implements LocaleChangeListener {
    private final String[] pages = new String[] {
            "nickname",
    };
    private final ProfileLayoutMgr mgr;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private final NicknamePanel nickNamePane;

    private JButton btnPrev;
    private JButton btnRight;

    private boolean result;

    public ProfileDialog(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        nickNamePane = new NicknamePanel(locale);

        mgr = ProfileLayoutMgr.getInstance();

        this.result = false;

        initialize();
        onLocaleChanged(locale);
    }   // -- End of constructor

    private void initialize() {
        /* Variables declaration */
        CardLayout clMain = new CardLayout();

        JPanel buttonPane = new JPanel();
        JPanel buttonLeftPane = new JPanel();
        JPanel buttonRightPane = new JPanel();
        JPanel mainPane = new JPanel();

        /* Set the properties of initialize */
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        this.setSize(new Dimension(550, 350));

        /* Set the layout of sub-panels */
        buttonPane.setLayout(new BorderLayout());
        buttonLeftPane.setLayout(new BoxLayout(buttonLeftPane, BoxLayout.PAGE_AXIS));
        buttonRightPane.setLayout(new BoxLayout(buttonRightPane, BoxLayout.PAGE_AXIS));
        mainPane.setLayout(clMain);

        buttonPane.setBackground(Color.PINK);

        mgr.setMainDialog(this);
        mgr.setMainLayout(clMain);
        mgr.setMainPane(mainPane);

        mainPane.add(nickNamePane, pages[0]);

        this.add(mainPane);
        this.add(buttonPane, BorderLayout.SOUTH);
    }

    private void loadText() {
        this.setTitle(localeBundle.getString("profile.title"));
    }

    public boolean showDialog() {
        setVisible(true);
        return result;
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);

        nickNamePane.onLocaleChanged(newLocale);

        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}