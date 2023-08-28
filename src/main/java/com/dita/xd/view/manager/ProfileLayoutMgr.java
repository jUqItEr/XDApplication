package com.dita.xd.view.manager;

import javax.swing.*;
import java.awt.*;

public class ProfileLayoutMgr {
    private static volatile ProfileLayoutMgr instance = null;

    private CardLayout mainLayout;
    private JFrame mainFrame;
    private JPanel mainPane;

    private ProfileLayoutMgr(){
    }

    public static ProfileLayoutMgr getInstance() {
        if (instance == null) {
            synchronized (ProfileLayoutMgr.class) {
                if (instance == null) {
                    instance = new ProfileLayoutMgr();
                }
            }
        }
        return instance;
    }   // -- End of function (getInstance)

    public void setMainLayout(CardLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setMainPane(JPanel mainPane) {
        this.mainPane = mainPane;
    }

    public void dispose() {
        mainFrame.dispose();
    }

    public void show(String name) {
        mainLayout.show(mainPane, name);
    }
}
