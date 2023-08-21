package xd.view.base;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class JRoundedTextField extends JTextField {
    private Shape shape;
    private int gap;

    public JRoundedTextField(int columns) {
        super(columns);
        setOpaque(false);
        this.gap = 15;
    }

    public JRoundedTextField(int columns, int gap) {
        this(columns);
        this.gap = gap;
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, gap, gap);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, gap, gap);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, gap, gap);
        }
        return shape.contains(x, y);
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }
}
