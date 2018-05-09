import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image img;

    ImagePanel(Image img){
        setImage(img);
    }

    void setImage(Image img){
        this.img = img;
        setPreferredSize(new Dimension(img.getWidth(null),img.getHeight(null)));
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img,0,0,null);
    }
}
