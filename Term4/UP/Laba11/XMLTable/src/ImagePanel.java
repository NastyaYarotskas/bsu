import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImagePanel extends JPanel {

    private Image defaultImage;

    private static final String DEFAULT_IMAGE_PATH1 = "laba1112.png";

    public ImagePanel() {
        defaultImage = new ImageIcon(getClass().getClassLoader().getResource(DEFAULT_IMAGE_PATH1)).getImage();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        defaultImage = getScaledImage(defaultImage, getWidth(), getHeight());
        g.drawImage(defaultImage, 0, 0, null);
    }

    private BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}
