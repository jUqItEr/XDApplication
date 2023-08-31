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
            private static final String USER_REGEX = "(@[a-zA-Z0-9]{14})";

            private final Pattern hashtagPattern;

            protected final Highlighter.HighlightPainter hashtagPainter;

            protected final JTextPane textPane;

            public XdDocumentFilter(JTextPane textPane) {
                this.hashtagPattern = Pattern.compile(HASHTAG_REGEX);

                this.hashtagPainter = new XdHashtagPainter(new Color(0x004d86f7));
                this.textPane = textPane;
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                super.replace(fb, offset, length, text, attrs);
                removeHighlights();

                try {
                    Highlighter highlighter = textPane.getHighlighter();
                    Document doc = textPane.getDocument();
                    String currentText = doc.getText(0, doc.getLength());
                    SimpleAttributeSet hashtagColor = new SimpleAttributeSet();
                    StyleConstants.setForeground(hashtagColor, new Color(0x004d86f7));
                    Matcher matcher = hashtagPattern.matcher(currentText);

                    while (matcher.find()) {
                        //highlighter.addHighlight(matcher.start(), matcher.end(), hashtagPainter);
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

            public void removeHighlights() {
                Highlighter highlighter = textPane.getHighlighter();
                Highlighter.Highlight[] highlights = highlighter.getHighlights();

                for (Highlighter.Highlight highlight : highlights) {
                    if (highlight.getPainter() instanceof XdHashtagPainter) {
                        highlighter.removeHighlight(highlight);
                    }
                }
            }
        }

        static class XdHashtagPainter extends DefaultHighlighter.DefaultHighlightPainter {
            public XdHashtagPainter(Color color) {
                super(color);
            }
        }
    }
}
