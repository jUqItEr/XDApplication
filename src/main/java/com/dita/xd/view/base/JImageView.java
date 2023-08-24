package com.dita.xd.view.base;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JImageView extends JLabel {
    public JImageView() {
        super();
    }

    public JImageView(ImageIcon icon) {
        super(icon);
    }

    public JImageView(String path) {
        Image original = loadImage(path);
        ImageIcon icon = new ImageIcon(original);

        setIcon(icon);
        setHorizontalAlignment(CENTER);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    private Image loadImage(String path) {
        Image result = null;

        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path))) {
            result = ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
