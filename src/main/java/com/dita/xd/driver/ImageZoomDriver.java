package com.dita.xd.driver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ImageZoomDriver extends JFrame {
    private String imagePath;

    public ImageZoomDriver() {
        initialize();

        imagePath = "resources/images/logo.png";
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setSize(800, 600);

        Icon icon = getIconFromPath("resources/images/test.jpeg");

        if (icon != null) {
            ZoomAndPanePanel imagePane = new ZoomAndPanePanel(icon);
            add(new JScrollPane(imagePane));
        }
    }

    private Icon getIconFromPath(String path) {
        Icon icon = null;

        try (InputStream is = new FileInputStream(path)) {
            icon = new ImageIcon(ImageIO.read(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icon;
    }

    public static void main(String[] args) {
        new ImageZoomDriver().setVisible(true);
    }

    class ZoomAndPanePanel extends JPanel {
        private final AffineTransform zoomTransform = new AffineTransform();
        private final transient Icon icon;
        private final Rectangle imageRect;
        private transient ZoomHandler handler;
        private transient DragScrollListener listener;

        protected ZoomAndPanePanel(Icon icon) {
            super();
            this.icon = icon;
            this.imageRect = new Rectangle(icon.getIconWidth(), icon.getIconHeight());
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            // use: AffineTransform#concatenate(...) and Graphics2D#setTransform(...)
            // https://docs.oracle.com/javase/8/docs/api/java/awt/geom/AffineTransform.html#concatenate-java.awt.geom.AffineTransform-
            AffineTransform at = g2.getTransform();
            at.concatenate(zoomTransform);
            g2.setTransform(at);
            icon.paintIcon(this, g2, 0, 0);
            // g2.drawImage(img, 0, 0, this);

            // or use: Graphics2D#drawImage(Image, AffineTransform, ImageObserver)
            // https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html#drawImage-java.awt.Image-java.awt.geom.AffineTransform-java.awt.image.ImageObserver-
            // g2.drawImage(img, zoomTransform, this);
            // or: g2.drawRenderedImage((RenderedImage) img, zoomTransform);
            // g2.fill(zoomTransform.createTransformedShape(r));

            // BAD EXAMPLE
            // g2.setTransform(zoomTransform);
            // g2.drawImage(img, 0, 0, this);

            g2.dispose();
        }

        @Override public Dimension getPreferredSize() {
            Rectangle r = zoomTransform.createTransformedShape(imageRect).getBounds();
            return new Dimension(r.width, r.height);
        }

        @Override public void updateUI() {
            removeMouseListener(listener);
            removeMouseMotionListener(listener);
            removeMouseWheelListener(handler);
            super.updateUI();
            listener = new DragScrollListener();
            addMouseListener(listener);
            addMouseMotionListener(listener);
            handler = new ZoomHandler();
            addMouseWheelListener(handler);
        }

        protected class ZoomHandler extends MouseAdapter {
            private static final double ZOOM_FACTOR = 1.2;
            private static final int MIN = -10;
            private static final int MAX = 10;
            private static final int EXT = 1;
            private final BoundedRangeModel range = new DefaultBoundedRangeModel(0, EXT, MIN, MAX + EXT);

            @Override public void mouseWheelMoved(MouseWheelEvent e) {
                double dir = e.getPreciseWheelRotation();
                int z = range.getValue();
                range.setValue(z + EXT * (dir > 0 ? -1 : 1));
                if (z != range.getValue()) {
                    Component c = e.getComponent();
                    Container p = SwingUtilities.getAncestorOfClass(JViewport.class, c);
                    if (p instanceof JViewport viewport) {
                        Rectangle ovr = viewport.getViewRect();
                        double s = dir > 0 ? 1d / ZOOM_FACTOR : ZOOM_FACTOR;
                        zoomTransform.scale(s, s);
                        // double s = 1d + range.getValue() * .1;
                        // zoomTransform.setToScale(s, s);
                        AffineTransform at = AffineTransform.getScaleInstance(s, s);
                        Rectangle nvr = at.createTransformedShape(ovr).getBounds();
                        Point vp = nvr.getLocation();
                        vp.translate((nvr.width - ovr.width) / 2, (nvr.height - ovr.height) / 2);
                        viewport.setViewPosition(vp);
                        c.revalidate();
                        c.repaint();
                    }
                }
            }
        }
    }

    static class DragScrollListener extends MouseAdapter {
        private final Cursor defCursor = Cursor.getDefaultCursor();
        private final Cursor hndCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        private final Point pp = new Point();

        @Override public void mouseDragged(MouseEvent e) {
            Component c = e.getComponent();
            Container p = SwingUtilities.getUnwrappedParent(c);
            if (p instanceof JViewport vport) {
                Point cp = SwingUtilities.convertPoint(c, e.getPoint(), vport);
                Point vp = vport.getViewPosition();
                vp.translate(pp.x - cp.x, pp.y - cp.y);
                ((JComponent) c).scrollRectToVisible(new Rectangle(vp, vport.getSize()));
                pp.setLocation(cp);
            }
        }

        @Override public void mousePressed(MouseEvent e) {
            Component c = e.getComponent();
            c.setCursor(hndCursor);
            Container p = SwingUtilities.getUnwrappedParent(c);
            if (p instanceof JViewport vport) {
                Point cp = SwingUtilities.convertPoint(c, e.getPoint(), vport);
                pp.setLocation(cp);
            }
        }

        @Override public void mouseReleased(MouseEvent e) {
            e.getComponent().setCursor(defCursor);
        }
    }
}