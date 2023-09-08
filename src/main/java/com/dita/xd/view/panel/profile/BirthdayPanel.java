package com.dita.xd.view.panel.profile;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.manager.ProfileLayoutMgr;
import io.loli.datepicker.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class BirthdayPanel extends JPanel implements LocaleChangeListener {
    private final UserRepository repository;
    private final ProfileLayoutMgr mgr;

    private ResourceBundle localeBundle;

    private JLabel lblTitle;

    JTextField tfBirthday;

    public BirthdayPanel(Locale locale) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        mgr = ProfileLayoutMgr.getInstance();
        repository = UserRepository.getInstance();

        initialize();
        onLocaleChanged(locale);
    }

    public UserBean getBean() {
        UserBean bean = new UserBean();

        try {
            bean.setBirthday(Date.valueOf(tfBirthday.getText()));
        } catch (Exception e) {
            System.err.println("생일이 입력되지 않았음");
        }
        return bean;
    }

    private void initialize() {
        setLayout(new BorderLayout());


        /* Variables declaration */
        JPanel mainPane = new JPanel();
        tfBirthday = new JTextField();
        DatePicker.datePicker(tfBirthday);

        lblTitle = new JLabel();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        /* Set the properties of components */
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        tfBirthday.setAlignmentX(Component.CENTER_ALIGNMENT);
        tfBirthday.setPreferredSize(new Dimension(200, 40));
        tfBirthday.setMaximumSize(new Dimension(200, 40));

        /* 여백 공간 설정 */
        mainPane.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));

        mainPane.add(Box.createGlue());
        mainPane.add(lblTitle);
        mainPane.add(Box.createRigidArea(new Dimension(0,20)));
        mainPane.add(tfBirthday);
        mainPane.add(Box.createGlue());

        add(mainPane);
    }

    private void loadText() {
        lblTitle.setText("생일 설정");

    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
