package com.dita.xd.view.base;

import javax.swing.*;
import java.awt.*;

public class JVerticalScrollPane extends JPanel implements Scrollable {

    public JVerticalScrollPane() {
        this(new GridLayout(0, 1));
    }

    public JVerticalScrollPane(LayoutManager lm) {
        super(lm);
    }

    public JVerticalScrollPane(Component comp) {
        this();
        add(comp);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 8;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 100;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
