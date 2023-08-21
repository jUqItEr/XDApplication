package com.dita.xd.view.base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class JHintTextField extends JTextField {
    final Font fontDefault;
    final Font fontGain;
    final Font fontLost;
    private String hint;

    public JHintTextField() {
        fontDefault = getFont();
        fontGain = new Font(fontDefault.getFontName(), Font.PLAIN, fontDefault.getSize());
        fontLost = new Font(fontDefault.getFontName(), Font.ITALIC, fontDefault.getSize());
    }

    public JHintTextField(int columns) {
        this();
        setColumns(columns);
    }

    public JHintTextField(final String hint) {
        this();
        this.setHint(hint);
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = getText();

                if (text.equals(getHint())) {
                    setText("");
                    setFont(fontGain);
                } else {
                    setText(text);
                    setFont(fontGain);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = getText();

                if (text.equals(getHint()) || text.length() == 0) {
                    setText(getHint());
                    setFont(fontLost);
                    // setForeground(Color.GRAY);
                } else {
                    setText(text);
                    setFont(fontGain);
                    // setForeground(Color.WHITE);
                }
            }
        });
    }

    public JHintTextField(final String hint, final int columns) {
        this(hint);
        setColumns(columns);
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        this.setText(hint);
        this.setFont(fontLost);
        // this.setForeground(Color.GRAY);
    }
}
