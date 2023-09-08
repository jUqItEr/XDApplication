package com.dita.xd.view.panel.main.search;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.util.filter.IDFilter;
import com.dita.xd.view.base.JHintTextField;
import com.dita.xd.view.base.JVerticalScrollPane;
import com.dita.xd.view.base.JXdSearchPane;
import com.dita.xd.view.base.JXdTextPane;
import com.dita.xd.view.manager.SearchPanelLayoutMgr;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collectors;

public class SearchPanel extends JPanel implements ActionListener {
    private final SearchPanelLayoutMgr mgr;

    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private ActivityController controller;
    private final TrendPanel trendPane;
    private final ContentSearchPanel contentSearchPanel;
    private final UserSearchPanel userSearchPanel;

    private JXdSearchPane txaSearch;

    public SearchPanel(Locale locale){
        controller = new ActivityController();
        localeBundle = ResourceBundle.getBundle("language", locale);

        currentLocale = locale;

        trendPane = new TrendPanel(locale);
        contentSearchPanel = new ContentSearchPanel(locale);
        userSearchPanel = new UserSearchPanel(locale);

        mgr = SearchPanelLayoutMgr.getInstance();

        initialize();

    }

    private void initialize(){
        setLayout(new BorderLayout());

        CardLayout clMain = new CardLayout();

        JPanel headPane = new JPanel();
        JPanel mainPane = new JPanel();

        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(mainPane));

        JButton temp = new JButton("버튼");
        temp.setPreferredSize(new Dimension(30, 30));
        temp.setMaximumSize(new Dimension(30, 30));

        txaSearch = new JXdSearchPane();

        headPane.setLayout(new BorderLayout());
        headPane.setBorder(BorderFactory.createEmptyBorder(15,30,0,70));
        mainPane.setLayout(clMain);

        txaSearch.setPreferredSize(new Dimension(200, 30));
        txaSearch.setMaximumSize(new Dimension(200, 30));

        loadText();

        mgr.setSubPane(mainPane);
        mgr.setChildLayout(clMain);
        mgr.setMainPane(this);

        mainPane.add(trendPane, "trend");
        mainPane.add(contentSearchPanel, "content");
        mainPane.add(userSearchPanel, "user");

        temp.addActionListener(this);

        headPane.add(txaSearch, BorderLayout.CENTER);
        headPane.add(temp, BorderLayout.EAST);
        add(headPane, BorderLayout.NORTH);
        add(mainPane);
    }

    private void loadText(){
        txaSearch.setFont(txaSearch.getFont().deriveFont(20f));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = txaSearch.getText();

        Vector<Object> vec = Optional.ofNullable(controller.search(text)).orElse(new Vector<>());

        if (!vec.isEmpty()) {
            if (vec.firstElement() instanceof FeedBean) {
                contentSearchPanel.clear();
                contentSearchPanel.setFeed(vec.stream().map(bean -> (FeedBean) bean)
                        .collect(Collectors.toCollection(Vector::new)));
                mgr.show("content");
            } else if (vec.firstElement() instanceof UserBean) {
                userSearchPanel.clear();
                userSearchPanel.setUser(vec.stream().map(bean -> (UserBean) bean)
                        .collect(Collectors.toCollection(Vector::new)));
                mgr.show("user");
            }
        } else {
            userSearchPanel.clear();
            contentSearchPanel.clear();
        }
        revalidate();
        repaint();
    }

    private void setSearchText(String text){

    }
}