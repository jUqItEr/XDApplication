package com.dita.xd.util.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NicknameFilter extends DocumentFilter {
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        int docLength = fb.getDocument().getLength();

        /* If $JHintTextField.setText("") was fired, it must be clear themselves */
        if (text.isEmpty()) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }   // -- End of if
        /* ID can be up to 50 digits in length */
        if (docLength > 49) {
            return;
        }   // -- End of if
        super.replace(fb, offset, length, text, attrs);
    }   // -- End of function (@Override: replace)

    private boolean isLowerAlphabetic(char c) {
        return 0x61 <= c && c <= 0x7A;
    }   // -- End of function (isLowerAlphabetic)
}   // -- End of class
