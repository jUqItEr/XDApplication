package com.dita.xd.view.panel.profile;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.manager.ProfileLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class NicknamePanel extends JPanel implements LocaleChangeListener {
    private final ProfileLayoutMgr mgr;

    private ResourceBundle localeBundle;

    private JHintTextField htfNickname;
    private JLabel lblTitle;

    public NicknamePanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        mgr = ProfileLayoutMgr.getInstance();

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        /* Variables declaration */
        JPanel mainPane = new JPanel();
        JRoundedImageView rivProfile = new JRoundedImageView();

        htfNickname = new JHintTextField();
        lblTitle = new JLabel();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        /* Set the properties of components */
        htfNickname.setAlignmentX(Component.CENTER_ALIGNMENT);
        htfNickname.setMaximumSize(new Dimension(300, 40));
        htfNickname.setPreferredSize(new Dimension(300, 40));

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        rivProfile.setIcon(new ImageIcon("resources/images/profile.png"));
        rivProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
        rivProfile.setMaximumSize(new Dimension(80, 80));
        rivProfile.setPreferredSize(new Dimension(80, 80));

        rivProfile.setBackground(Color.BLUE);

        mainPane.add(Box.createVerticalStrut(40));
        mainPane.add(lblTitle);
        mainPane.add(Box.createVerticalStrut(20));
        mainPane.add(rivProfile);
        mainPane.add(Box.createVerticalStrut(20));
        mainPane.add(htfNickname);
        mainPane.add(Box.createVerticalGlue());

        add(mainPane);
    }

    private void clear() {

    }

    private void loadText() {
        htfNickname.setHint(localeBundle.getString("profile.field.hint.nickname"));
        lblTitle.setText("나는 제목이오");
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
