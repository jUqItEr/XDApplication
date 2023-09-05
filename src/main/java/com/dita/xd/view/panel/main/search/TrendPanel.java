package com.dita.xd.view.panel.main.search;

import com.dita.xd.controller.FeedController;
import com.dita.xd.controller.HashtagController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.HashtagBean;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class TrendPanel extends JPanel{
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private HashtagController controller;
    private final JPanel objectPane = new JPanel(new GridLayout(0,1,2,0));
    private JLabel lblTitle;
    private ContentPane contentPane;


    public TrendPanel(Locale locale){
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new HashtagController();

        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JPanel mainPane = new JPanel();
        JPanel headPane = new JPanel();

        lblTitle = new JLabel();

        mainPane.setLayout(new BorderLayout());
        mainPane.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        headPane.setLayout(new BorderLayout());
        headPane.setBorder(BorderFactory.createEmptyBorder(20,10,0,0));

        objectPane.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        loadText();


        /* 현재 해쉬태그 데이터가 없음, 피드를 올릴때 같이 포함하도록 수정 */

        for(HashtagBean bean : controller.getTrendHashtags(1)){
            String hash = bean.getContent();
            int hashCount = bean.getHashtagId();
            contentPane = new ContentPane(hash, hashCount + "");
            objectPane.add(contentPane);
        }

//        contentPane = new ContentPane("엄청난 트렌드", "1,000");
//        objectPane.add(contentPane);
//        contentPane = new ContentPane("신기한 트렌드", "543,904");
//        objectPane.add(contentPane);
//        contentPane = new ContentPane("이상한 트렌드", "100,000");
//        objectPane.add(contentPane);

        headPane.add(lblTitle, BorderLayout.WEST);
        headPane.add(Box.createGlue());

        mainPane.add(objectPane, BorderLayout.NORTH);
        mainPane.add(Box.createGlue(), BorderLayout.CENTER);
        add(headPane, BorderLayout.NORTH);
        add(mainPane);


    }

    private void loadText(){ // TrendPanel 컴포넌트의 텍스트 설정
        lblTitle.setText("추천 트렌드");
        lblTitle.setFont(lblTitle.getFont().deriveFont(24f).deriveFont(Font.BOLD));
    }

    public class ContentPane extends JPanel{
        private JLabel lblHash;
        private JLabel lblHashCount;
        private JLabel lblIssue;


        public ContentPane(String hashName, String hashCount){
            lblHash = new JLabel(hashName);
            lblHashCount = new JLabel(hashCount + " posts");

            initialize();
        }

        private void initialize() {
            setLayout(new BorderLayout());

            JPanel mainPane = new JPanel();

            lblIssue = new JLabel();

            mainPane.setLayout(new GridLayout(0,1,0,3));
            mainPane.setBorder(BorderFactory.createEmptyBorder(15,10,0,0));

            loadText();

            mainPane.add(lblIssue);
            mainPane.add(lblHash);
            mainPane.add(lblHashCount);

            add(mainPane);
        }

        private void loadText() { // ContentPane 컴포넌트의 텍스트 설정
            lblIssue.setText("지구에서 트렌드 중");
            lblIssue.setFont(lblIssue.getFont().deriveFont(12f));
            lblIssue.setForeground(Color.lightGray);

            lblHash.setFont(lblHash.getFont().deriveFont(15f));
            lblHash.setForeground(Color.DARK_GRAY);

            lblHashCount.setFont(lblHash.getFont().deriveFont(11f));
            lblHashCount.setForeground(Color.lightGray);
        }
    }
}
