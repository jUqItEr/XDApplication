package com.dita.xd.view.base;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JXdTextPane extends JTextPane {
    private String hint;

    public JXdTextPane() {
        super();
        hint = "";
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new XdDocumentFilter(this));
    }


    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (hint.length() == 0 || getText().length() > 0) {
            return;
        }
        final Graphics2D graphics = (Graphics2D) g;
        final int offset = 2;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setFont(getFont());
        graphics.drawString(hint, getInsets().left, g.getFontMetrics().getMaxAscent() + offset);

        revalidate();
    }

    static class XdDocumentFilter extends DocumentFilter {
        private static final String HASHTAG_REGEX = "(#[a-zA-Zㄱ-ㅎ가-힣0-9(_)]+)";
        private static final String USER_REGEX = "(@[a-zA-Z0-9]{1,15})";

        private final Pattern hashtagPattern;
        private final Pattern userPattern;

        private final SimpleAttributeSet hashtagColor;
        private final SimpleAttributeSet userColor;

        protected final JTextPane textPane;

        public XdDocumentFilter(JTextPane textPane) {
            this.hashtagColor = new SimpleAttributeSet();
            this.hashtagPattern = Pattern.compile(HASHTAG_REGEX);
            this.userColor = new SimpleAttributeSet();
            this.userPattern = Pattern.compile(USER_REGEX);
            this.textPane = textPane;

            StyleConstants.setForeground(this.hashtagColor, new Color(0x00_4D_86_F7));
            StyleConstants.setForeground(this.userColor, new Color(0x00_F2_75_21));
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, string, attr);

            highlight();
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            super.replace(fb, offset, length, text, attrs);

            highlight();
        }   // -- End of function (replace: @Override)

        private void highlight() {
            try {
                StyledDocument doc = textPane.getStyledDocument();
                String currentText = doc.getText(0, doc.getLength());
                Matcher hashtagMatcher = hashtagPattern.matcher(currentText);
                Matcher userMatcher = userPattern.matcher(currentText);

                while (hashtagMatcher.find()) {
                    doc.setCharacterAttributes(
                            hashtagMatcher.start(),
                            hashtagMatcher.end() - hashtagMatcher.start(),
                            hashtagColor,
                            false
                    );
                    textPane.getInputAttributes().removeAttributes(hashtagColor);
                }   // -- End of while
                while (userMatcher.find()) {
                    doc.setCharacterAttributes(
                            userMatcher.start(),
                            userMatcher.end() - userMatcher.start(),
                            userColor,
                            false
                    );
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }   // -- End of try-catch
        }
    }
}
