package com.dita.xd.view.dialog;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.manager.ProfileLayoutMgr;
import com.dita.xd.view.panel.profile.HeaderPanel;
import com.dita.xd.view.panel.profile.BirthdayPanel;
import com.dita.xd.view.panel.profile.IntroducePanel;
import com.dita.xd.view.panel.profile.NicknamePanel;
import com.dita.xd.view.panel.profile.OtherInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProfileDialog extends JDialog implements LocaleChangeListener {
    private final String[] pages = new String[] {
            "nickname", "header", "birthday", "introduce", "other"
    };

    private final UserRepository repository;
    private final ProfileLayoutMgr mgr;

    private Locale currentLocale;
    private ResourceBundle localeBundle;

    private final JPanel[] panes;
    private final BirthdayPanel birthdayPane;
    private final HeaderPanel headerPane;
    private final IntroducePanel introducePane;
    private final NicknamePanel nickNamePane;
    private final OtherInfoPanel otherInfoPane;

    private JButton btnNext;
    private JButton btnPrev;

    private boolean result;
    private int currentIndex;

    public ProfileDialog(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);

        panes = new JPanel[] {
                nickNamePane = new NicknamePanel(locale),
                headerPane = new HeaderPanel(locale),
                birthdayPane = new BirthdayPanel(locale),
                introducePane = new IntroducePanel(locale),
                otherInfoPane = new OtherInfoPanel(locale)
        };
        mgr = ProfileLayoutMgr.getInstance();

        repository = UserRepository.getInstance();

        this.result = false;

        initialize();
        onLocaleChanged(locale);
    }   // -- End of constructor

    private void initialize() {
        /* Variables declaration */
        CardLayout clMain = new CardLayout();

        JPanel buttonPane = new JPanel();
        JPanel buttonPaddingPane = new JPanel();
        JPanel mainPane = new JPanel();

        btnPrev = new JButton();
        btnNext = new JButton();

        /* Set the properties of initialize */
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        this.setSize(new Dimension(600, 400));

        /* Set the layout of sub-panels */
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
        buttonPaddingPane.setLayout(new BoxLayout(buttonPaddingPane, BoxLayout.Y_AXIS));
        mainPane.setLayout(clMain);

        btnPrev.setEnabled(false);
        btnPrev.setPreferredSize(new Dimension(120, 40));
        btnPrev.setMaximumSize(new Dimension(120, 40));
        btnNext.setPreferredSize(new Dimension(120, 40));
        btnNext.setMaximumSize(new Dimension(120, 40));

        mgr.setMainDialog(this);
        mgr.setMainLayout(clMain);
        mgr.setMainPane(mainPane);

        for (int i = 0; i < panes.length; ++i) {
            mainPane.add(panes[i], pages[i]);
        }
        currentIndex = 0;

        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(btnPrev);
        buttonPane.add(Box.createHorizontalStrut(20));
        buttonPane.add(btnNext);
        buttonPane.add(Box.createHorizontalGlue());

        buttonPaddingPane.add(buttonPane);
        buttonPaddingPane.add(Box.createVerticalStrut(30));

        btnNext.addActionListener(e -> {
            switch (currentIndex) {
                case 0, 1, 2 -> {
                    btnPrev.setEnabled(true);

                    if (currentIndex == 0) {
                        UserBean bean = nickNamePane.getBean();
                        repository.updateProfile(bean);
                    } else if (currentIndex == 1) {
                        UserBean bean = headerPane.getBean();
                        repository.updateProfile(bean);
                    } else {
                        UserBean bean = birthdayPane.getBean();
                        repository.updateProfile(bean);
                    }
                    mgr.show(pages[++currentIndex]);
                }
                case 3 -> {
                    btnNext.setText(localeBundle.getString("profile.button.finish"));

                    UserBean bean = introducePane.getBean();
                    repository.updateProfile(bean);
                    mgr.show(pages[++currentIndex]);
                }
                case 4 -> {

                    result = repository.verifyProfile();
                    dispose();
                }
            }
        });
        btnPrev.addActionListener(e -> {
            switch (currentIndex) {
                case 1 ->  {
                    btnPrev.setEnabled(false);
                    mgr.show(pages[--currentIndex]);
                }
                case 2, 3, 4 -> {
                    btnNext.setText(localeBundle.getString("profile.button.next"));
                    mgr.show(pages[--currentIndex]);
                }
            }
        });

        this.add(mainPane);
        this.add(buttonPaddingPane, BorderLayout.SOUTH);
    }

    private void loadText() {
        this.setTitle(localeBundle.getString("profile.title"));

        btnNext.setText(localeBundle.getString("profile.button.next"));
        btnPrev.setText(localeBundle.getString("profile.button.prev"));
    }

    public boolean showDialog() {
        setVisible(true);
        return result;
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        currentLocale = newLocale;
        localeBundle = ResourceBundle.getBundle("language", newLocale);

        birthdayPane.onLocaleChanged(newLocale);
        headerPane.onLocaleChanged(newLocale);
        introducePane.onLocaleChanged(newLocale);
        nickNamePane.onLocaleChanged(newLocale);
        otherInfoPane.onLocaleChanged(newLocale);

        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}