package com.dita.xd.util.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IDFilter extends DocumentFilter {
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        int docLength = fb.getDocument().getLength();

        if (docLength > 14) {
            return;
        }
        for (int n = text.length(); n > 0; --n) {
            char c = text.charAt(n - 1);

            if (isAlphabetic(c) || Character.isDigit(c)) {
                super.replace(fb, offset, length, String.valueOf(c), attrs);
            }
        }
    }

    private boolean isAlphabetic(char c) {
        return (0x41 <= c && c <= 0x5A) || (0x61 <= c && c <= 0x7A);
    }
}
