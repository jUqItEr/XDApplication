package xd.view.base;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public class JRoundedImagePanel extends JRoundedPanel {
    protected BufferedImage image;
    protected BufferedImage roundedImage;

    public JRoundedImagePanel() {
        super();
        setOpaque(false);
        try {
            image = ImageIO.read(new URL(""));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        roundedImage = makeRoundedCorner(image, 20);//make image round

        width = roundedImage.getWidth() + arcs.width / 2;//set width and height of panel accordingly
        height = roundedImage.getHeight() + arcs.height / 2;
    }

    public JRoundedImagePanel(boolean shady) {
        super(shady);
    }

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        graphics.drawImage(roundedImage, 0, 0, this);//draw the rounded image
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
