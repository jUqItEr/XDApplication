package com.dita.xd.driver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageZoomDriver extends JFrame {
    private BufferedImage bi;

    private int zoomPointX;
    private int zoomPointY;
    private double zoom = 0;

    private static final double ZOOM_AMOUNT = 1.1;

    public static void main(String[] args) {
        new ImageZoomDriver("resources/images/logo.png").setVisible(true);
    }

    private void sizeToZoom() {
        double factor = Math.pow(ZOOM_AMOUNT, zoom);
        setSize((int) (bi.getWidth() * factor), (int) (bi.getHeight() * factor));
    }

    public ImageZoomDriver(String filename) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        try {
            bi = ImageIO.read(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //sizeToZoom();

        addMouseWheelListener(e -> {
            zoomPointX = e.getX();
            zoomPointY = getY();
            if (e.getPreciseWheelRotation() < 0) {
                zoom -= 0.1;
            } else {
                zoom += 1;
            }
            if (zoom < 0.01) {
                zoom = 0.01;
            }
            repaint();
        });
    }

    @Override
    public void paintComponents(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponents(g);

        AffineTransform at = g2d.getTransform();

        at.translate(zoomPointX, zoomPointY);
        at.scale(zoom, zoom);
        at.translate(-zoomPointX, -zoomPointY);
        g2d.setTransform(at);

    }
}
