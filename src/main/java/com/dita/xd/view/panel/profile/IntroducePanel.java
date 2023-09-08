package com.dita.xd.view.panel.profile;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JXdTextPane;
import com.dita.xd.view.manager.ProfileLayoutMgr;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class IntroducePanel extends JPanel implements LocaleChangeListener {
    private final ProfileLayoutMgr mgr;
    private final UserRepository repository;

    private ResourceBundle localeBundle;
    private JLabel lblTitle;
    private JXdTextPane txaIntroduce;

    public IntroducePanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        mgr = ProfileLayoutMgr.getInstance();
        repository = UserRepository.getInstance();

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        /* Variables declaration */
        JPanel mainPane = new JPanel();
        JPanel subPane = new JPanel();

        lblTitle = new JLabel();
        txaIntroduce = new JXdTextPane();

        if (repository.getUserAccount().getIntroduce() != null) {
            try{
                txaIntroduce.getStyledDocument().insertString(
                        0, repository.getUserAccount().getIntroduce(),null);
            } catch (BadLocationException e){
                e.printStackTrace();
            }
        }

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        subPane.setLayout(new BorderLayout());

        /* Set the properties of components */
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        txaIntroduce.setAlignmentX(Component.CENTER_ALIGNMENT);
        txaIntroduce.setPreferredSize(new Dimension(300,80));
        txaIntroduce.setMaximumSize(new Dimension(300, 80));

        mainPane.setBorder(BorderFactory.createEmptyBorder(60,0,10,0));
        mainPane.add(lblTitle);
        mainPane.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPane.add(txaIntroduce);
        mainPane.add(Box.createGlue());

        add(mainPane);
    }

    public UserBean getBean() {
        UserBean bean = new UserBean();

        bean.setIntroduce(txaIntroduce.getText());

        return bean;
    }

    private void loadText() {
        lblTitle.setText("자신을 소개해 보아요");
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
