package com.dita.xd.view.manager;

import com.dita.xd.view.panel.ProfilePanel;

import javax.swing.*;
import java.awt.*;

public class ProfilePanelLayoutMgr {
    public static volatile ProfilePanelLayoutMgr instance = null;

    private CardLayout childLayout;
    private ProfilePanel mainPane;
    private JPanel subPane;

    private ProfilePanelLayoutMgr() {
    }

    public static ProfilePanelLayoutMgr getInstance() {
        if (instance == null) {
            synchronized (ProfilePanelLayoutMgr.class) {
                if (instance == null) {
                    instance = new ProfilePanelLayoutMgr();
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
