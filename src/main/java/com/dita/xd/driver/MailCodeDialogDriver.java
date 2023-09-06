package com.dita.xd.driver;

import com.dita.xd.view.dialog.MailCodeDialog;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;
import java.util.Locale;

/**
 * @deprecated For testing
 * */
public class MailCodeDialogDriver {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));

        setUIFont(new FontUIResource("Nixgon.ttf", Font.PLAIN, 14));

        SwingUtilities.invokeLater(() -> {
            MailCodeDialog dialog = new MailCodeDialog(new Locale("String"), "aaa@aaa.com");

            System.out.println(dialog.showDialog());
        });
    }

    private static void setUIFont(FontUIResource resource) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof FontUIResource) {
                UIManager.put(key, resource);
            }
        }
    }
}
