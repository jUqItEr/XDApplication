package com.dita.xd.driver;

import com.dita.xd.view.dialog.PlainDialog;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.dita.xd.util.helper.ImageTransferHelper.uploadImage;

/**
 *
 * @deprecated For testing
 * */
public class DialogDriver {
    private static final String HOST = "http://hxlab.co.kr:9001";
    private static final String DOWNLOAD_LINK = HOST + "/photo";

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
