package com.dita.xd.view.base;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JRoundedImageView extends JLabel {
    private BufferedImage image = null;

    public JRoundedImageView(String path) {
        setImage(path);

        if (image != null) {
           setSize(image.getWidth(), image.getHeight());
           revalidate();
        }
    }

    public JRoundedImageView(String path, Dimension dim) {
        setImage(path);

        if (image != null) {
            setSize(dim);
            revalidate();
        }
    }

    public JRoundedImageView(String path, int width, int height) {
        this(path, new Dimension(width, height));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(String path) {
        this.image = loadImage(path);
    }

    private BufferedImage loadImage(String path) {
        BufferedImage result = null;

        if (new File(path).exists()) {
            try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(path))) {
                result = ImageIO.read(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dimension = this.getSize();
        Ellipse2D.Double border = new Ellipse2D.Double();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        border.setFrame(0, 0, dimension.width, dimension.height);
        g2d.setClip(border);
        g2d.drawImage(image, 0, 0, dimension.width, dimension.height, this);
    }
}
