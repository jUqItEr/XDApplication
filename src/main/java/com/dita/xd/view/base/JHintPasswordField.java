package com.dita.xd.view.base;

import javax.swing.*;
import java.awt.*;

public class JHintPasswordField extends JPasswordField {
    private String hint;

    public JHintPasswordField(String hint) {
        this.hint = hint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (hint.length() == 0 || getPassword().length > 0) {
            return;
        }
        final Graphics2D graphics = (Graphics2D) g;
        final int offset = 4;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString(hint, getInsets().left, g.getFontMetrics().getMaxAscent() + offset);
    }
}
