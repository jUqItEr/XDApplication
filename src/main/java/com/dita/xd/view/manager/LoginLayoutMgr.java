package com.dita.xd.view.manager;

import com.dita.xd.view.panel.login.ChangePasswordPanel;

import javax.swing.*;
import java.awt.*;

public class LoginLayoutMgr {
    private static volatile LoginLayoutMgr instance = null;

    private JFrame mainFrame;
    private CardLayout mainLayout;
    private JPanel mainPane;
    private ChangePasswordPanel chgPane;

    private LoginLayoutMgr() {
    }

    public static LoginLayoutMgr getInstance() {
        if (instance == null) {
            synchronized (LoginLayoutMgr.class) {
                if (instance == null) {
                    instance = new LoginLayoutMgr();
                }
            }
        }
        return instance;
    }

    public void setMainLayout(CardLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void setMainPane(JPanel mainPane) {
        this.mainPane = mainPane;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setChgPane(ChangePasswordPanel chgPane) {
        this.chgPane = chgPane;
    }

    public void setId(String id) {
        chgPane.setId(id);
    }

    public void dispose() {
        mainFrame.dispose();
    }

    public void show(String name) {
        mainLayout.show(mainPane, name);
    }

}
