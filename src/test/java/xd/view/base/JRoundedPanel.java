package xd.view.base;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public class JRoundedPanel extends JPanel {
    /**
     * Stroke size. it is recommended to set it to 1 for better view
     */
    protected int strokeSize;
    /**
     * Color of shadow
     */
    protected Color shadowColor;
    /**
     * Sets if it drops shadow
     */
    protected boolean shady;
    /**
     * Sets if it has a High Quality view
     */
    protected boolean highQuality;
    /**
     * Double values for Horizontal and Vertical radius of corner arcs
     */
    //protected Dimension arcs = new Dimension(0, 0);
    protected Dimension arcs;//creates curved borders and panel
    /**
     * Distance between shadow border and opaque panel border
     */
    protected int shadowGap;
    /**
     * The offset of shadow.
     */
    protected int shadowOffset;
    /**
     * The transparency value of shadow. ( 0 - 255)
     */
    protected int shadowAlpha;
    int width = 300, height = 300;

    public JRoundedPanel() {
        this.shady = true;
        this.strokeSize = 1;
        this.shadowColor = Color.BLACK;
        this.highQuality = true;
        this.shadowGap = 10;
        this.shadowOffset = 4;
        this.shadowAlpha = 150;
        this.arcs = new Dimension(20, 20);
    }

    public JRoundedPanel(boolean shady) {
        this();
        this.shady = shady;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color shadowColorA = new Color(shadowColor.getRed(),
                shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        //Sets antialiasing if HQ.
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //Draws shadow borders if any.
        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(
                    shadowOffset,// X position
                    shadowOffset,// Y position
                    width - strokeSize - shadowOffset, // width
                    height - strokeSize - shadowOffset, // height
                    arcs.width, arcs.height);// arc Dimension
        } else {
            shadowGap = 1;
        }

        //Draws the rounded opaque panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.drawRoundRect(0, 0, width - shadowGap,
                height - shadowGap, arcs.width, arcs.height);

        //Sets strokes to default, is better.
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
