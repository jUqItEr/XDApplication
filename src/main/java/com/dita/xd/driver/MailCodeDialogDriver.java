package com.dita.xd.driver;

import com.dita.xd.view.dialog.MailCodeDialog;

import java.util.Locale;

/**
 * @deprecated For testing
 * */
public class MailCodeDialogDriver {
    public static void main(String[] args) {
        MailCodeDialog dialog = new MailCodeDialog(Locale.CHINA, "aaa@aaa.com");
        dialog.setVisible(true);
    }
}
