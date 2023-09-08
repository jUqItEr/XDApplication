package com.dita.xd.view.panel.main.search;

import com.dita.xd.controller.ActivityController;
import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.UserBean;
import com.dita.xd.util.filter.IDFilter;
import com.dita.xd.view.base.*;
import com.dita.xd.view.manager.SearchPanelLayoutMgr;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collectors;

public class SearchPanel extends JPanel{
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
        JPanel headSubPane = new JPanel();
        JPanel mainPane = new JPanel();

        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(mainPane));

        JImageView imvSearchIcon = new JImageView();
        ImageIcon SearchIcon = new ImageIcon("resources/images/search.png");
        imvSearchIcon.setMaximumSize(new Dimension(30,30));
        imvSearchIcon.setPreferredSize(new Dimension(30,30));
        imvSearchIcon.setIcon(SearchIcon);

        JImageView imvRefreshIcon = new JImageView();
        ImageIcon refreshIcon = new ImageIcon("resources/images/refresh.png");
        imvRefreshIcon.setMaximumSize(new Dimension(30,30));
        imvRefreshIcon.setPreferredSize(new Dimension(30,30));
        imvRefreshIcon.setIcon(refreshIcon);

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

        imvRefreshIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txaSearch.setText("");
                userSearchPanel.clear();
                contentSearchPanel.clear();
                mgr.show("trend");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                imvRefreshIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imvRefreshIcon.setCursor(Cursor.getDefaultCursor());
            }
        });

        imvSearchIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = txaSearch.getText();
                search(text);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                imvSearchIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imvSearchIcon.setCursor(Cursor.getDefaultCursor());
            }
        });

        headSubPane.add(imvSearchIcon);
        headSubPane.add(Box.createRigidArea(new Dimension(10,0)));
        headSubPane.add(imvRefreshIcon);

        headPane.add(txaSearch, BorderLayout.CENTER);
        headPane.add(headSubPane, BorderLayout.EAST);
        add(headPane, BorderLayout.NORTH);
        add(mainPane);
    }

    private void loadText(){
        txaSearch.setFont(txaSearch.getFont().deriveFont(20f));
    }

    public void search(String text){
        Vector<Object> vec = Optional.ofNullable(controller.search(text)).orElse(new Vector<>());

        if (!vec.isEmpty()) {
            if (vec.firstElement() instanceof FeedBean) {
                contentSearchPanel.clear();
                contentSearchPanel.setFeed(vec.stream().map(bean -> (FeedBean) bean)
                        .collect(Collectors.toCollection(Vector::new)));
                mgr.show("content");
                txaSearch.setText(text);
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
}