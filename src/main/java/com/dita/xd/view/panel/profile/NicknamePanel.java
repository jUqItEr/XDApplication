package com.dita.xd.view.panel.profile;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.manager.ProfileLayoutMgr;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.dita.xd.util.helper.ImageTransferHelper.uploadImage;

public class NicknamePanel extends JPanel implements LocaleChangeListener {
    private final UserRepository repository;
    private final ProfileLayoutMgr mgr;

    private ResourceBundle localeBundle;

    private JHintTextField htfNickname;
    private JLabel lblTitle;

    private String imagePath;

    public NicknamePanel(Locale locale) {
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

        if (repository.getUserNickname() != null) {
            htfNickname.setText(repository.getUserNickname());
        }

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        ImageIcon icon;

        try {
            imagePath = repository.getUserAccount().getProfileImage();
            icon = new ImageIcon(new URL(imagePath));
        } catch (MalformedURLException ex) {
            icon = new ImageIcon("resources/images/anonymous.jpg");
        }
        rivProfile.setIcon(icon);
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

        rivProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & PNG Images",
                        "jpg","png");
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(filter);
                int result = chooser.showDialog(getParent(), "불러오기");

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();
                    Path selectedPath = selectedFile.toPath();

                    String uuid = uploadImage(selectedPath);
                    ImageIcon icon;

                    try {
                        if (uuid != null) {
                            String filename = "http://hxlab.co.kr:9001/photo/" + uuid;
                            imagePath = filename;
                            icon = new ImageIcon(new URL(filename));
                        } else {
                            throw new MalformedURLException("No Valid URL");
                        }
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                        icon = new ImageIcon("resources/images/anonymous.jpg");
                    }
                    rivProfile.setIcon(icon);
                    revalidate();
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                rivProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rivProfile.setCursor(Cursor.getDefaultCursor());
            }
        });

        add(mainPane);
    }

    public UserBean getBean() {
        UserBean bean = new UserBean();
        bean.setNickname(htfNickname.getText());
        bean.setProfileImage(imagePath);
        return bean;
    }

    private void loadText() {
        htfNickname.setHint(localeBundle.getString("profile.field.hint.nickname"));
        lblTitle.setText("프로필 사진과 별명 설정");
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
