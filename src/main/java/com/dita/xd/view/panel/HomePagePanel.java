package com.dita.xd.view.panel;

import com.dita.xd.listener.LocaleChangeListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomePagePanel extends JPanel{
    //private final ?Controller controller;
    private ResourceBundle localeBundle;
    private Locale currentLocale;

    public HomePagePanel(Locale locale){
        //controller = new ?Controller();
        localeBundle = ResourceBundle.getBundle("language", locale);

        initialize();

    }

    private void initialize(){
        setLayout(new BorderLayout());

        JPanel pnlMain = new JPanel();
        JPanel pnlTop = new JPanel();
        JPanel pnlFeed = new JPanel();

        JScrollPane pnlScl = new JScrollPane();

        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        pnlTop.setBorder(new TitledBorder(new LineBorder(Color.RED,2)));
        pnlFeed.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 2)));

        pnlTop.setPreferredSize(new Dimension(400, 200));
        pnlTop.setMaximumSize(new Dimension(400,200));
        pnlFeed.setPreferredSize(new Dimension(400,350));
        pnlFeed.setMaximumSize(new Dimension(400,350));
        pnlFeed.add(pnlScl);

        pnlMain.add(pnlTop);
        pnlMain.add(pnlFeed);

        this.add(pnlMain);
    }
}