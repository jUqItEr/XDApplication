package com.dita.xd.view.panel.main.search;

import com.dita.xd.controller.FeedController;
import com.dita.xd.controller.HashtagController;
import com.dita.xd.model.FeedBean;
import com.dita.xd.model.HashtagBean;
import com.dita.xd.view.base.JVerticalScrollPane;
import com.dita.xd.view.panel.main.FeedPanel;
import com.dita.xd.view.panel.main.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

public class ContentSearchPanel extends JPanel{
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private final FeedController controller;
    private final JPanel objectPane = new JPanel(new GridLayout(0,1,2,0));
    private FeedPanel feedPanel;

    public ContentSearchPanel(Locale locale){
        localeBundle = ResourceBundle.getBundle("language", locale);
        controller = new FeedController();
        currentLocale = locale;

        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());

        JPanel mainPane = new JPanel();

        JScrollPane scrollPane = new JScrollPane(new JVerticalScrollPane(mainPane));
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        scrollBar.setPreferredSize(new Dimension(0, 0));
        scrollBar.setUnitIncrement(16);
        scrollPane.setVerticalScrollBar(scrollBar);

        mainPane.setLayout(new BorderLayout());
        mainPane.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));

        objectPane.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        loadText();

        mainPane.add(objectPane, BorderLayout.NORTH);
        mainPane.add(Box.createGlue(), BorderLayout.CENTER);
        add(mainPane);
    }

    public void clear() {
        for (Component c : objectPane.getComponents()) {
            if (c instanceof FeedPanel) {
                objectPane.remove(c);
            }
        }
        revalidate();
        repaint();
    }
    private void loadText() {

    }

    public void setFeed(Vector<FeedBean> beans) {
        for(FeedBean bean : beans) {
            feedPanel = new FeedPanel(currentLocale, bean);
            objectPane.add(feedPanel);
        }
        revalidate();
        repaint();
    }
}
