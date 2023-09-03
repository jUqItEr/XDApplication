package com.dita.xd.view.panel;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.view.base.JImageView;
import com.dita.xd.view.base.JRoundedImageView;
import com.dita.xd.view.base.JVerticalScrollPane;
import com.dita.xd.view.base.JXdTextPane;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomePagePanel extends JPanel{
    private final FeedController controller;
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private FeedPanel feedPanel;

    private JButton btnUpload;
    private JButton btnImageUpload;
    private JXdTextPane txaFeedText;

    private final JPanel objectPane = new JPanel(new GridLayout(0, 1, 2, 0));

    public HomePagePanel(Locale locale){
        currentLocale = locale;
        localeBundle = ResourceBundle.getBundle("language", locale);
        controller = new FeedController();

        initialize();

    }

    private void initialize(){
        setLayout(new BorderLayout());

        JPanel mainPane = new JPanel();
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

        mainPane.setLayout(new BorderLayout());
//        boxPane.setLayout(new GridLayout(1,2));
        boxPane.setLayout(new BorderLayout());
        profilePane.setLayout(new BorderLayout());
        profileSubPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.setLayout(new BorderLayout());
        activityPane.setLayout(new BoxLayout(activityPane, BoxLayout.X_AXIS));

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
        boxPane.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));

        contentPane.add(Box.createRigidArea(new Dimension(0,10)),BorderLayout.NORTH);
        contentPane.add(Box.createRigidArea(new Dimension(10,0)),BorderLayout.EAST);

        activityPane.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        activityPane.add(Box.createRigidArea(new Dimension(10, 0)));
        activityPane.add(imvImageIcon);
        activityPane.add(Box.createRigidArea(new Dimension(300,0)));
        activityPane.add(btnUpload);

        /* 패널에 컴포넌트 추가 */
        profileSubPane.add(rivProfile);



        contentPane.add(txaFeedText);

        objectPane.add(boxPane);

        for(FeedBean bean : controller.getFeeds("123")) {
            createFeed(bean);
        }

        mainPane.add(objectPane, BorderLayout.NORTH);
        mainPane.add(Box.createGlue(), BorderLayout.CENTER);

        add(scrollPane);

    }

    private void loadText(){
        btnUpload.setText("게시하기");
        btnUpload.setFont(btnUpload.getFont().deriveFont(12f).deriveFont(Font.BOLD));

        txaFeedText.setText("");
        try{
            txaFeedText.getStyledDocument().insertString(0, "적고 싶은거", null);
        } catch (BadLocationException e){
            e.printStackTrace();
        }
    }

    protected void createFeed(FeedBean bean){

        feedPanel = new FeedPanel(currentLocale, bean);
        objectPane.add(feedPanel);

        revalidate();
        repaint();
    }
}