package com.dita.xd.driver;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @deprecated For testing
 * */
public class HighlighterDriver extends JDialog {
    public HighlighterDriver() {
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(400, 500);

        add(new JXdTextPane());
    }
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));

        SwingUtilities.invokeLater(() -> {
            HighlighterDriver driver = new HighlighterDriver();

            driver.setVisible(true);
        });
    }

    static class JXdTextPane extends JTextPane {
        public JXdTextPane() {
            super();
            ((AbstractDocument) this.getDocument()).setDocumentFilter(new XdDocumentFilter(this));
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
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                super.replace(fb, offset, length, text, attrs);

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
                        textPane.getInputAttributes().removeAttributes(userColor);
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }   // -- End of try-catch
            }   // -- End of function (replace: @Override)
        }
    }
}
