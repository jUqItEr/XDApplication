package com.dita.xd.view.panel;

import com.dita.xd.controller.FeedController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.HashtagBean;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class TrendPanel extends JPanel{
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private FeedController controller;
    private final JPanel objectPane = new JPanel(new GridLayout(0,1,2,0));

    private JLabel lblTitle;
    private ContentPane contentPane;


    public TrendPanel(Locale locale){
        localeBundle = ResourceBundle.getBundle("language", locale);

        controller = new FeedController();

        initialize();
    }

    private void initialize() {

        JPanel mainPane = new JPanel();

        lblTitle = new JLabel();

        mainPane.setLayout(new BorderLayout());
        mainPane.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));

        objectPane.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        loadText();

        objectPane.add(lblTitle);
//        objectPane.add(contentPane("신기한 트렌드","53,498"));
//        objectPane.add(contentPane("엄청난 트렌드","25,382"));
//        objectPane.add(contentPane("이상한 트렌드","100,000"));



//        for(int i = 0; i < 3; i++){
//            objectPane.add(new contentPane("엄청난 트렌드","10"));
//        }

        mainPane.add(objectPane, BorderLayout.NORTH);
        mainPane.add(Box.createGlue(), BorderLayout.CENTER);
        add(mainPane);


    }

    private void loadText(){ // TrendPanel 컴포넌트의 텍스트 설정
        lblTitle.setText("추천 트렌드");
        lblTitle.setFont(lblTitle.getFont().deriveFont(16f).deriveFont(Font.BOLD));
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

            mainPane.setLayout(new GridLayout(0,1,0,5));
            mainPane.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));

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

            lblHashCount.setFont(lblHash.getFont().deriveFont(11f));
            lblHashCount.setForeground(Color.lightGray);

        }
    }
}
