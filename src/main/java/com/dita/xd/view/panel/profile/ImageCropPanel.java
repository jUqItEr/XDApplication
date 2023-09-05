package com.dita.xd.view.panel.profile;

import com.dita.xd.listener.LocaleChangeListener;
import com.dita.xd.view.manager.ProfileLayoutMgr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ImageCropPanel extends JPanel implements LocaleChangeListener {
    private static final double ZOOM_AMOUNT = 1.1;
    private final ProfileLayoutMgr mgr;

    private ResourceBundle localeBundle;

    private BufferedImage image;
    private int zoom = 0;
    private String imagePath;

    public ImageCropPanel(Locale locale, String path) {
        localeBundle = ResourceBundle.getBundle("language", locale);
        mgr = ProfileLayoutMgr.getInstance();

        this.imagePath = path;

        initialize();
        onLocaleChanged(locale);
    }

    private void initialize() {
        setLayout(new BorderLayout());

        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int steps = e.getWheelRotation();;
                zoom += steps;
                sizeToZoom();
            }
        });
    }

    private void loadText() {

    }

    private void sizeToZoom() {
        double factor = Math.pow(ZOOM_AMOUNT, zoom);
        setSize((int)(image.getWidth() * factor), (int)(image.getHeight() * factor));
    }

    @Override
    public void onLocaleChanged(Locale newLocale) {
        localeBundle = ResourceBundle.getBundle("language", newLocale);
        LocaleChangeListener.broadcastLocaleChanged(newLocale, this);
        loadText();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
