package com.dita.xd.view.panel;

import com.dita.xd.controller.FeedController;
import com.dita.xd.listener.LocaleChangeListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class SearchPanel extends JPanel{
    //private final ?Controller controller;
    private ResourceBundle localeBundle;
    private Locale currentLocale;
    private FeedController controller;

    public SearchPanel(Locale locale){
        controller = new FeedController();
        localeBundle = ResourceBundle.getBundle("language", locale);

        initialize();

    }

    private void initialize(){
        setLayout(new BorderLayout());

        CardLayout clMain = new CardLayout();

        JPanel trendPane = new JPanel();
        JPanel searchPane = new JPanel();
        JPanel mainPane = new JPanel();

        mainPane.setLayout(clMain);

        add(searchPane, BorderLayout.NORTH);
        add(mainPane);
    }

    private void loadText(){

    }


}