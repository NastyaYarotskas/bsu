import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

class CircleImage extends JPanel {
        private double angle = 0;
        private int radius;
        private int xSize ;
        private int ySize;
        private Image image;
        private Image image1 = new ImageIcon("circle.png").getImage();



    public CircleImage() {

    }

    @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            xSize = this.getWidth();
            ySize = this.getHeight();
            radius = Math.min(xSize / 2, ySize / 2) * 4 / 5;
            int xOffset = (xSize - 2 * radius) / 2;
            int yOffset = (ySize - 2 * radius) / 2;
            g.setColor(Color.BLACK);
            g.drawOval(xOffset, yOffset, 2 * radius, 2 * radius);
            int x = (int) (xOffset + radius  + Math.sin(angle)*radius);
            int y = (int) (yOffset + radius  - Math.cos(angle)*radius);
            image = getScaledImage(image1, getHeight()/5,getHeight()/5);
            g.drawImage(image, x-50, y-50, null);
        }

        public void setAngle(int param, int action) {
            if (action == 0) {
                this.angle += ((double) param) / radius ;
            } else if (action == 1) {
                this.angle  -= ((double) param) / radius;
            }
        }


    static public Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.drawImage(srcImg, 0, 0, w, h, null);
        return resizedImg;
    }
}

