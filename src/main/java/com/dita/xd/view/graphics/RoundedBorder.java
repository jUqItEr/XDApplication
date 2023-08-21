package com.dita.xd.view.graphics;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * RoundedBorder
 * */
public class RoundedBorder extends AbstractBorder {
    private Color color;
    private int gap;

    public RoundedBorder(Color color, int gap) {
        this.color = color;
        this.gap = gap;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, gap, gap));
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return getBorderInsets(c, new Insets(gap, gap, gap, gap));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        final int dividedGap = gap >> 1;
        insets.bottom = dividedGap;
        insets.left = dividedGap;
        insets.right = dividedGap;
        insets.top = dividedGap;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }
}
