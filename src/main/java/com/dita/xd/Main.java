package com.dita.xd;

import com.dita.xd.view.frame.LoginFrame;
import com.dita.xd.view.frame.MainFrame;
import com.dita.xd.view.theme.XdMaterialTheme;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.utils.MaterialColors;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new MaterialLookAndFeel(new XdMaterialTheme()));

        setUIFont(new FontUIResource("Nixgon.ttf", Font.PLAIN, 14));

        SwingUtilities.invokeLater(() -> {
            try {
                LoginFrame frame = new LoginFrame(Locale.getDefault());
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
