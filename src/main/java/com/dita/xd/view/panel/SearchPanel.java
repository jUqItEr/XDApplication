package com.dita.xd.view.panel;

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

    public SearchPanel(Locale locale){
        //controller = new ?Controller();
        localeBundle = ResourceBundle.getBundle("language", locale);

        initialize();

    }

    private void initialize(){
        setLayout(new BorderLayout());

        CardLayout clmain = new CardLayout();

        JPanel searchPane = new JPanel();
        JPanel trendPane = new JPanel();
        JPanel mainPane = new JPanel();

        

        this.add(searchPane, BorderLayout.NORTH);
        this.add(mainPane);
    }

    private void loadText(){

    }
}