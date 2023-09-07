//package com.dita.xd.driver;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.geom.Ellipse2D;
//
//class CircularComboBox extends JPanel {
//    private JComboBox<String> comboBox;
//    private final String[]items = {"Item 1", "Item 2", "Item 3"};
//
//    public CircularComboBox() {
//        setLayout(new BorderLayout());
//
//        comboBox = new JComboBox<>(items);
//        comboBox.setUI(new CircularComboBoxUI());
//        comboBox.setPreferredSize(new Dimension(100, 100));
//
//        add(comboBox, BorderLayout.CENTER);
//    }
//
//    private class CircularComboBoxUI extends BasicComboBoxUI {
//        @Override
//        protected JButton createArrowButton() {
//            JButton button = new JButton() {
//                @Override
//                protected void paintComponent(Graphics g) {
//                    super.paintComponent(g);
//                    Graphics2D g2 = (Graphics2D) g;
//                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//                    int width = getWidth();
//                    int height = getHeight();
//                    Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, width, height);
//                    g2.setColor(Color.BLACK);
//                    g2.fill(circle);
//                }
//            };
//            button.setContentAreaFilled(false);
//            button.setFocusPainted(false);
//            button.setBorderPainted(false);
//            return button;
//        }
//    }
//}
//
