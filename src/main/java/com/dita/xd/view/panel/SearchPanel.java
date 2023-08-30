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

        JPanel pnlMain = new JPanel();
        JPanel pnlSearch = new JPanel();
        JPanel pnlResult = new JPanel();

        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

        pnlSearch.setBorder(new TitledBorder(new LineBorder(Color.GREEN,2)));
        pnlResult.setBorder(new TitledBorder(new LineBorder(Color.PINK, 2)));

        pnlSearch.setPreferredSize(new Dimension(400, 200));
        pnlSearch.setMaximumSize(new Dimension(400,200));
        pnlResult.setPreferredSize(new Dimension(400,350));
        pnlResult.setMaximumSize(new Dimension(400,350));

        pnlMain.add(pnlSearch);
        pnlMain.add(pnlResult);

        this.add(pnlMain);
    }
}