package com.dita.xd.view;

import com.dita.xd.controller.LoginController;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private LoginController controller = null;

    public LoginFrame() {
        controller = new LoginController();
        /* Initialize components */
        initialize();
    }

    private void initialize() {

    }
}
