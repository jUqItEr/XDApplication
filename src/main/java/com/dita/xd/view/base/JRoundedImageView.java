package com.dita.xd.view.base;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JRoundedImageView extends JComponent {
    private int borderSize;
    private Icon icon;

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (icon != null) {
            int width = getWidth();
            int height = getHeight();
            int diameter = Math.min(width, height);
            int x = width / 2 - diameter / 2;
            int y = height / 2 - diameter / 2;
            int border = borderSize << 1;

            diameter -= border;

            Rectangle size = getAutoSize(icon, diameter);
            BufferedImage img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fillOval(0, 0, diameter, diameter);

            Composite composite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.SrcIn);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(toImage(icon), size.x, size.y, size.width, size.height, null);
            g2d.setComposite(composite);
            g2d.dispose();

            g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (borderSize > 0) {
                diameter += border;
                g2d.setColor(getForeground());
                g2d.fillOval(x, y, diameter, diameter);
            }
            if (isOpaque()) {
                g2d.setColor(getBackground());
                diameter -= border;
                g2d.fillOval(x + borderSize, y + borderSize, diameter, diameter);
            }
            g2d.drawImage(img, x + borderSize, y + borderSize, null);
        }
        super.paintComponent(g);
    }

    private Rectangle getAutoSize(Icon image, int size) {
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) size / iw;
        double yScale = (double) size / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);

        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }

        int x = (size - width) / 2;
        int y = (size - height) / 2;

        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
}
