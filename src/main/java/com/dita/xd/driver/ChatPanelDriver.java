package com.dita.xd.driver;

import com.dita.xd.model.UserBean;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


/**
 * @deprecated For testing
 * */
public class ChatPanelDriver extends JPanel {
    private static final Dimension THUMB_SIZE = new Dimension(170, 150);
    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel thumbPanel = new JPanel(new GridLayout(0, 1, 5, 5));

    private UserBean bean;

    public ChatPanelDriver() {
        initialize();
    }

    private void initialize() {
        JPanel holderPanel = new JPanel(new BorderLayout());
        holderPanel.add(thumbPanel, BorderLayout.NORTH);
        holderPanel.add(Box.createGlue(), BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(holderPanel);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();

        scrollBar.setPreferredSize(new Dimension(0, 0));

        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBar(scrollBar);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    protected void createThumb() {
        JButton thumb = new JButton("Thumb");
        thumb.setPreferredSize(THUMB_SIZE);
        thumbPanel.add(thumb);
        revalidate();
        repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(screen.width / 10 * 6,
                screen.height / 10 * 6);
    }

    static class UserPanel extends JPanel {
        UserBean bean;

        public UserPanel(UserBean bean) {
            this.bean = bean;
        }
    }

    public static void main(String[] args) throws  Exception{
        UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));

        SwingUtilities.invokeLater(() -> {
            ChatPanelDriver mainPanel = new ChatPanelDriver();
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.getContentPane().add(mainPanel);
            f.pack();
            f.setVisible(true);
        });
    }
}