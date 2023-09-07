package com.dita.xd.view.panel.main.home;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.controller.FeedController;
import com.dita.xd.controller.LoginController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.MediaBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.base.JVerticalScrollPane;
import com.dita.xd.view.base.JXdTextPane;
import com.dita.xd.view.manager.MainLayoutMgr;
import com.dita.xd.view.panel.main.FeedPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.dita.xd.util.helper.ImageTransferHelper.uploadImage;

public class HomePagePanel extends JPanel{
    private static final String HOST = "http://hxlab.co.kr:9001/";
    private static final String DOWNLOAD_LINK = HOST + "photo/";
    private final FeedController feedController;
    private final LoginController loginController;
    private final ActivityController activityController;
    private final UserRepository repository;
    private final MainLayoutMgr mgr;
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private FeedPanel feedPanel;

    private JLabel lblImgCount;
    private JLabel lblImgBox;
    private JButton btnUpload;
    private JButton btnImageUpload;
    private JXdTextPane txaFeedText;

    private JPanel mainPane;
    private String messageEmptyError;
    private Vector<MediaBean> selectedFiles;
    private File[] filesBox;

    public HomePagePanel(Locale locale){
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);

        feedController = new FeedController();
        activityController = new ActivityController();
        loginController = new LoginController();

        mgr = MainLayoutMgr.getInstance();

        repository = UserRepository.getInstance();

        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout());



        mainPane = new JPanel();
        JPanel boardPane = new JPanel();
        JPanel boxParentPane = new JPanel();
        JPanel boxPane = new JPanel();
        JPanel profilePane = new JPanel();
        JPanel profileSubPane = new JPanel();
        JPanel contentPane = new JPanel();
        JPanel activityPane = new JPanel();
        JPanel activeImgPane = new JPanel();

        JFileChooser chooser = new JFileChooser();

        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(mainPane));
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        selectedFiles = new Vector<>();

        btnUpload = new JButton();
        btnImageUpload = new JButton();

        lblImgCount = new JLabel();
        lblImgBox = new JLabel();

        txaFeedText = new JXdTextPane();

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);
        scrollPane.setVerticalScrollBar(scrollBar);

        mainPane.setLayout(new GridBagLayout());

//        boxPane.setLayout(new GridLayout(1,2));
        boardPane.setLayout(new BorderLayout());
        boxPane.setLayout(new BorderLayout());
        boxParentPane.setLayout(new BorderLayout());
        profilePane.setLayout(new BorderLayout());
        profileSubPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.setLayout(new BorderLayout());
        activityPane.setLayout(new BorderLayout());
        activeImgPane.setLayout(new BorderLayout());

        boxParentPane.add(boxPane);

        boxPane.add(profilePane, BorderLayout.WEST);
        boxPane.add(contentPane);
        profilePane.add(profileSubPane, BorderLayout.NORTH);

        contentPane.add(activityPane, BorderLayout.SOUTH);

        btnUpload.setFont(btnUpload.getFont().deriveFont(12f).deriveFont(Font.BOLD));
        lblImgCount.setFont(lblImgCount.getFont().deriveFont(Font.BOLD));
        /* 프로필 이미지 설정 */
        JRoundedImageView rivProfile = new JRoundedImageView();
        String profileUrl = repository.getUserAccount().getProfileImage();
        ImageIcon icon = null;

        try {
            if (profileUrl != null) {
                icon = new ImageIcon(new URL(profileUrl));
            } else {
                throw new MalformedURLException("No valid URL");
            }
        } catch (MalformedURLException e) {
            icon = new ImageIcon("resources/images/anonymous.jpg");
        }
        rivProfile.setMaximumSize(new Dimension(50, 50));
        rivProfile.setPreferredSize(new Dimension(50, 50));
        rivProfile.setIcon(icon);

        /**/
        JImageView imvImageIcon = new JImageView();
        ImageIcon subIcon = new ImageIcon("resources/images/image.png");
        imvImageIcon.setMaximumSize(new Dimension(20,20));
        imvImageIcon.setPreferredSize(new Dimension(20,20));
        imvImageIcon.setIcon(subIcon);

        ImageIcon deleteIcon = new ImageIcon("resources/images/delete.png");

        imvImageIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & PNG Images",
                        "jpg","png");
                chooser.setMultiSelectionEnabled(true);
                chooser.setFileFilter(filter);

                int result = chooser.showOpenDialog(getParent());

                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] files = chooser.getSelectedFiles();
                    filesBox = files;
                    if (files.length > 4) {
                        JOptionPane.showMessageDialog(getParent(), "파일은 총 4개까지 업로드할 수 있습니다.");
                    } else {
                        lblImgBox.setVisible(true);
                        lblImgCount.setVisible(true);
                        loadCountText();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                imvImageIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imvImageIcon.setCursor(Cursor.getDefaultCursor());
            }
        });

        rivProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mgr.changeProfileContext(loginController.getUser(repository.getUserId()));
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

        lblImgBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblImgBox.setVisible(false);
                lblImgCount.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblImgBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblImgBox.setCursor(Cursor.getDefaultCursor());
            }
        });

        loadText();

        /* 패널 혹은 컴포넌트 속성 조정 */

        txaFeedText.setPreferredSize(new Dimension(340, 80));

        btnImageUpload.setPreferredSize(new Dimension(20, 20));
        btnImageUpload.setMaximumSize(new Dimension(20, 20));
        btnImageUpload.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblImgBox.setPreferredSize(new Dimension(20, 20));
        lblImgBox.setMaximumSize(new Dimension(20, 20));

        /* 여백 공간 추가 */
        boxPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boxParentPane.setBorder(BorderFactory.createMatteBorder(0,0, 2, 0, Color.LIGHT_GRAY));

        contentPane.add(Box.createRigidArea(new Dimension(0,10)),BorderLayout.NORTH);
        contentPane.add(Box.createRigidArea(new Dimension(10,0)),BorderLayout.EAST);

        activityPane.setBorder(BorderFactory.createEmptyBorder(10,0,0,10));

        activeImgPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        activityPane.add(imvImageIcon, BorderLayout.WEST);
        activityPane.add(activeImgPane, BorderLayout.CENTER);
        activityPane.add(btnUpload, BorderLayout.EAST);

        lblImgBox.setIcon(deleteIcon);

        activeImgPane.add(lblImgCount, BorderLayout.WEST);
        activeImgPane.add(lblImgBox, BorderLayout.CENTER);
        activeImgPane.add(Box.createGlue(), BorderLayout.EAST);

        btnUpload.addActionListener(e -> {
            String content = txaFeedText.getText();

            if(!content.isEmpty()) {
                selectedFiles = Arrays.stream(Optional.ofNullable(filesBox).orElse(new File[0])).map(file -> {
                    String userId = repository.getUserId();
                    String contentType = "I";
                    String contentAddress = DOWNLOAD_LINK + uploadImage(file.toPath());
                    String contentCensoredType = "N";
                    return new MediaBean(-1, userId, contentType, contentAddress, contentCensoredType, null);
                }).collect(Collectors.toCollection(Vector::new));

                int result = activityController.addFeed(repository.getUserAccount(), content, selectedFiles);

                FeedBean feedBean = feedController.getFeed(result);
                feedPanel = new FeedPanel(currentLocale, feedBean);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.weightx = 1.0;
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                mainPane.add(feedPanel, gbc, 0);
                txaFeedText.setText("");
                System.out.println(feedBean.getId());
                revalidate();
                repaint();
                filesBox = null;
                lblImgBox.setVisible(false);
                lblImgCount.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, messageEmptyError);
            }
        });

        /* 패널에 컴포넌트 추가 */
        profileSubPane.add(rivProfile);

        contentPane.add(txaFeedText);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        feedController.getRecentFeeds().forEach(bean -> createFeed(bean, gbc));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
//        mainPane.add(new JLabel(), gbc);

        scrollBar.setValue(-200);

        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                Adjustable adjustable = adjustmentEvent.getAdjustable();
                adjustable.setValue(adjustable.getMinimum());
                scrollBar.removeAdjustmentListener(this);
            }
        });


        mainPane.add(boxParentPane, gbc);

        boardPane.add(boxParentPane, BorderLayout.NORTH);
        boardPane.add(scrollPane);
        add(boardPane);

    }

    private void loadCountText() { lblImgCount.setText(filesBox.length + "개 선택됨 "); }

    private void loadText(){
        btnUpload.setText("게시하기");

        txaFeedText.setHint("적고 싶은거");

        lblImgBox.setVisible(false);
        lblImgCount.setVisible(false);

        messageEmptyError = "작성할 글이 비어있습니다.";
    }

    protected void createFeed(FeedBean bean, GridBagConstraints gbc){
//        if (!isBlocked(repository.getUserId(), bean.getUserId())){
//
//        }
        feedPanel = new FeedPanel(currentLocale, bean);
        mainPane.add(feedPanel, gbc);
    }
}