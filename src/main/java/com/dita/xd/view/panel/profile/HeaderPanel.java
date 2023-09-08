package com.dita.xd.view.panel.profile;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.UserBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.util.helper.ImageTransferHelper;
import com.dita.xd.view.base.JImageView;
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

public class HeaderPanel extends JPanel implements LocaleChangeListener {
    private final UserRepository repository;
    private final ProfileLayoutMgr mgr;
    private ResourceBundle localeBundle;
    private JLabel lblTitle;
    private String imagePath;

    public HeaderPanel(Locale locale) {

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
        JImageView imvHeader = new JImageView();
        JFileChooser chooser = new JFileChooser();

        lblTitle = new JLabel();

        /* Set the localized texts. */
        loadText();

        /* Set the properties of sub panels */
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        /* Set the properties of components */
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f).deriveFont(Font.BOLD));

        if (repository.getUserAccount().getHeaderImage() != null) {
            try {
                imagePath = repository.getUserAccount().getHeaderImage();
                ImageIcon icon = new ImageIcon(new URL(imagePath));
                icon = new ImageIcon(icon.getImage().getScaledInstance(483, 190, Image.SCALE_SMOOTH));
                imvHeader.setIcon(icon);
            } catch (MalformedURLException e) {
                imvHeader.setIcon(new ImageIcon("resources/images/xd.png"));
            }
        } else {
            imvHeader.setIcon(new ImageIcon("resources/images/xd.png"));
        }
        imvHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        imvHeader.setMaximumSize(new Dimension(485, 190));
        imvHeader.setPreferredSize(new Dimension(485, 190));

        imvHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        /* 여백 공간 설정 */
        mainPane.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));

        mainPane.add(imvHeader);
        mainPane.add(Box.createRigidArea(new Dimension(0,20)));
        mainPane.add(lblTitle);
        mainPane.add(Box.createGlue());

        imvHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & PNG Images",
                        "jpg","png");
                chooser.setFileFilter(filter);

                int result = chooser.showOpenDialog(getParent());

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
                            icon = new ImageIcon(icon.getImage().getScaledInstance(483,190,Image.SCALE_SMOOTH));
                        } else {
                            throw new MalformedURLException("No Valid URL");
                        }
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                        icon = new ImageIcon("resources/images/xd.png");
                    }
                    imvHeader.setIcon(icon);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                imvHeader.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imvHeader.setCursor(Cursor.getDefaultCursor());
            }
        });

        add(mainPane);
    }

    public UserBean getBean() {
        UserBean bean = new UserBean();

        bean.setHeaderImage(imagePath);

        return bean;
    }

    private void loadText() {
        lblTitle.setText("헤더 이미지 설정");
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }
}
