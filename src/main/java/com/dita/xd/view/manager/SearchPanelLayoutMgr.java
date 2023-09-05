package com.dita.xd.view.manager;

import com.dita.xd.view.panel.ProfilePanel;

import javax.swing.*;
import java.awt.*;

public class SearchPanelLayoutMgr {
    public static volatile SearchPanelLayoutMgr instance = null;

    private CardLayout childLayout;
    private ProfilePanel mainPane;
    private JPanel subPane;

    private SearchPanelLayoutMgr() {
    }

    public static SearchPanelLayoutMgr getInstance() {
        if (instance == null) {
            synchronized (SearchPanelLayoutMgr.class) {
                if (instance == null) {
                    instance = new SearchPanelLayoutMgr();
                }
            }
        }
        return instance;
    }

    public CardLayout getChildLayout() {
        return childLayout;
    }

    public void setChildLayout(CardLayout childLayout) {
        this.childLayout = childLayout;
    }

    public ProfilePanel getMainPane() {
        return mainPane;
    }

    public void setMainPane(ProfilePanel mainPane) {
        this.mainPane = mainPane;
    }

    public JPanel getSubPane() {
        return subPane;
    }

    public void setSubPane(JPanel subPane) {
        this.subPane = subPane;
    }

    public void show(String name) {
        childLayout.show(subPane, name);
    }
}
