package com.dita.xd.view.base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JHintTextField extends JTextField {
    private String hint;

    public JHintTextField() {
    }

    public JHintTextField(int columns) {
        this();
        setColumns(columns);
    }

    public JHintTextField(final String hint) {
        this();
        this.hint = hint;
    }

    public JHintTextField(final String hint, final int columns) {
        this(hint);
        setColumns(columns);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (hint.length() == 0 || getText().length() > 0) {
            return;
        }
        final Graphics2D graphics = (Graphics2D) g;
        final int offset = 4;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setFont(getFont());
        graphics.drawString(hint, getInsets().left, g.getFontMetrics().getMaxAscent() + offset);
    }
}
