package com.dita.xd.view.panel.main.home;

import com.dita.xd.controller.FeedController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.repository.UserRepository;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.base.JVerticalScrollPane;
import com.dita.xd.view.base.JXdTextPane;
import com.dita.xd.view.panel.main.FeedPanel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomePagePanel extends JPanel{
    private final FeedController controller;
    private final UserRepository repository;
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private FeedPanel feedPanel;

    private JButton btnUpload;
    private JButton btnImageUpload;
    private JXdTextPane txaFeedText;

    //private final JPanel objectPane = new JPanel(new GridLayout(0, 1, 4, 4));
    private JPanel mainPane;

    private String messageEmptyError;

    public HomePagePanel(Locale locale){
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);
        controller = new FeedController();

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

        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(mainPane));
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        btnUpload = new JButton();
        btnImageUpload = new JButton();

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

        boxPane.add(profilePane, BorderLayout.WEST);
        boxPane.add(contentPane);
        profilePane.add(profileSubPane, BorderLayout.NORTH);

        contentPane.add(activityPane, BorderLayout.SOUTH);

        /* 프로필 이미지 설정 */
        JRoundedImageView rivProfile = new JRoundedImageView();
        ImageIcon icon = new ImageIcon("resources/images/anonymous.jpg");
        rivProfile.setMaximumSize(new Dimension(50, 50));
        rivProfile.setPreferredSize(new Dimension(50, 50));
        rivProfile.setIcon(icon);

        /**/
        JImageView imvImageIcon = new JImageView();
        ImageIcon subIcon = new ImageIcon("resources/images/image.png");
        imvImageIcon.setMaximumSize(new Dimension(20,20));
        imvImageIcon.setPreferredSize(new Dimension(20,20));
        imvImageIcon.setIcon(subIcon);

        imvImageIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        loadText();

        /* 패널 혹은 컴포넌트 속성 조정 */

        txaFeedText.setPreferredSize(new Dimension(340, 80));

        btnImageUpload.setPreferredSize(new Dimension(20, 20));
        btnImageUpload.setMaximumSize(new Dimension(20, 20));
        btnImageUpload.setAlignmentX(Component.LEFT_ALIGNMENT);

        /* 여백 공간 추가 */
        boxPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boxParentPane.setBorder(BorderFactory.createMatteBorder(0,0, 2, 0, Color.LIGHT_GRAY));

        boxParentPane.add(boxPane);

        contentPane.add(Box.createRigidArea(new Dimension(0,10)),BorderLayout.NORTH);
        contentPane.add(Box.createRigidArea(new Dimension(10,0)),BorderLayout.EAST);

        activityPane.setBorder(BorderFactory.createEmptyBorder(10,0,0,10));
        activityPane.add(Box.createRigidArea(new Dimension(10, 0)));
        activityPane.add(imvImageIcon);
        activityPane.add(Box.createRigidArea(new Dimension(300,0)));
        activityPane.add(btnUpload);

        btnUpload.addActionListener(e-> {
            if(!txaFeedText.getText().isEmpty()) {
                boolean result = controller.create(repository.getUserId(), txaFeedText.getText());

                if (result) {
                    FeedBean bean = controller.getFeeds("123").firstElement();
                    feedPanel = new FeedPanel(currentLocale, bean);
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.weightx = 1.0;
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    mainPane.add(feedPanel, gbc, 0);
                    revalidate();
                    repaint();
                }
            } else {
                JOptionPane.showMessageDialog(null, messageEmptyError);
            }
        });

//        btnTranslation.addActionListener(e -> {
//            TranslationController transController = new TranslationController();
//            String text = txaFeedText.getText();
//            text = transController.translate(text, "en");
//            System.out.println(text);
//            txaFeedText.setText(text);
//        });
        /* 패널에 컴포넌트 추가 */
        profileSubPane.add(rivProfile);

        contentPane.add(txaFeedText);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for(FeedBean bean : controller.getFeeds("123")) {
            createFeed(bean, gbc);
        }

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
//        mainPane.add(objectPane);
//        mainPane.add(Box.createGlue(), BorderLayout.SOUTH);

        boardPane.add(boxParentPane, BorderLayout.NORTH);
        boardPane.add(scrollPane);
        add(boardPane);

    }

    private void loadText(){
        btnUpload.setText("게시하기");
        btnUpload.setFont(btnUpload.getFont().deriveFont(12f).deriveFont(Font.BOLD));

        txaFeedText.setHint("적고 싶은거");

        messageEmptyError = "작성할 글이 비어있습니다.";
    }

    protected void createFeed(FeedBean bean, GridBagConstraints gbc){

        feedPanel = new FeedPanel(currentLocale, bean);
        mainPane.add(feedPanel, gbc);
    }
}