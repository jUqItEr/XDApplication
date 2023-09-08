package com.dita.xd.view.panel.profile;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.manager.ProfileLayoutMgr;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class OtherInfoPanel extends JPanel implements LocaleChangeListener {
    private final UserRepository repository;
    private final ProfileLayoutMgr mgr;

    private ResourceBundle localeBundle;


    JRadioButton rb1 = new JRadioButton("남");
    JRadioButton rb2 = new JRadioButton("여");
    JRadioButton rb3 = new JRadioButton("밝히지 않음");
    private JLabel lblGender;
    private JLabel lblAddrees;
    private JLabel lblLink;
    private JHintTextField htfAddress;
    private JHintTextField htfLink;
    public OtherInfoPanel(Locale locale) {
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
        JPanel radioPane = new JPanel();

        ButtonGroup group = new ButtonGroup();

        lblGender = new JLabel();
        lblAddrees = new JLabel();
        lblLink = new JLabel();

        htfAddress = new JHintTextField();
        htfLink = new JHintTextField();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        radioPane.setLayout(new GridLayout(1,5,5,0));

        /* Set the properties of components */
        lblGender.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblGender.setFont(lblGender.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        lblAddrees.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAddrees.setFont(lblAddrees.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        lblLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLink.setFont(lblLink.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        htfAddress.setAlignmentX(Component.CENTER_ALIGNMENT);
        htfAddress.setPreferredSize(new Dimension(300, 40));
        htfAddress.setMaximumSize(new Dimension(300, 40));

        htfLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        htfLink.setPreferredSize(new Dimension(300, 40));
        htfLink.setMaximumSize(new Dimension(300, 40));

        mainPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        group.add(rb1);
        group.add(rb2);
        group.add(rb3);

        String gender = repository.getUserAccount().getGender();

        if (gender.equals("M")) {
            rb1.setSelected(true);
        } else if (gender.equals("F")) {
            rb2.setSelected(true);
        } else {
            rb3.setSelected(true);
        }
        radioPane.add(Box.createGlue());
        radioPane.add(rb1);
        radioPane.add(rb2);
        radioPane.add(rb3);
        radioPane.add(Box.createGlue());

        mainPane.add(lblGender);
        mainPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPane.add(radioPane);
        mainPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPane.add(lblAddrees);
        mainPane.add(Box.createRigidArea(new Dimension(0,10)));
        mainPane.add(htfAddress);
        mainPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPane.add(lblLink);
        mainPane.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPane.add(htfLink);

        add(mainPane);
    }

    public UserBean getBean() {
        UserBean bean = new UserBean();
        String gender = "N";

        if (rb1.isSelected()) {
            gender = "M";
        } else if (rb2.isSelected()) {
            gender = "F";
        }
        bean.setGender(gender);

        if (!htfAddress.getText().isEmpty()) {
            bean.setAddress(htfAddress.getText());
        }
        if (!htfLink.getText().isEmpty()) {
            bean.setWebsite(htfLink.getText());
        }
        return bean;
    }

    private void loadText() {
        lblGender.setText("성별");
        lblAddrees.setText("주소");
        lblLink.setText("개임 홈페이지");
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
