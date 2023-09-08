package com.dita.xd.view.manager;

import javax.swing.*;
import java.awt.*;

public class ProfileLayoutMgr {
    private static volatile ProfileLayoutMgr instance = null;

    private CardLayout mainLayout;
    private JDialog mainDialog;
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

    public void setMainDialog(JDialog mainDialog) {
        this.mainDialog = mainDialog;
    }

    public void setMainPane(JPanel mainPane) {
        this.mainPane = mainPane;
    }

    public void callAlertDialog(String message) {
        JOptionPane.showMessageDialog(mainDialog, message);
    }

    public void dispose() {
        mainDialog.dispose();
    }

    public void show(String name) {
        mainLayout.show(mainPane, name);
    }
}
