package com.dita.xd.driver;

import com.dita.xd.view.dialog.PlainDialog;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @deprecated For testing
 * */
public class DialogDriver {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));

        setUIFont(new FontUIResource("Nixgon.ttf", Font.PLAIN, 14));

        SwingUtilities.invokeLater(() -> {
            Locale currentLocale = Locale.JAPAN;
            ResourceBundle bundle = ResourceBundle.getBundle("language", currentLocale);
            PlainDialog dialog = new PlainDialog(currentLocale, bundle.getString("login.field.hint.password"), PlainDialog.MessageType.ERROR);
            dialog.setVisible(true);
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
