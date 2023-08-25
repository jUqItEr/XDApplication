package com.dita.xd.util.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IDFilter extends DocumentFilter {
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        int docLength = fb.getDocument().getLength();

        if (text.isEmpty()) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }
        if (docLength > 14) {
            return;
        }
        for (int n = text.length(); n > 0; --n) {
            char c = text.charAt(n - 1);

            if (isLowerAlphabetic(c) || Character.isDigit(c)) {
                super.replace(fb, offset, length, String.valueOf(c), attrs);
            }
        }
    }

    private boolean isLowerAlphabetic(char c) {
        return 0x61 <= c && c <= 0x7A;
    }
}
