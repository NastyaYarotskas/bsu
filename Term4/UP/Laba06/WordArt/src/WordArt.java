import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class WordArt extends JFrame{

    private ImagePanel panel;
    private double textAngle;
    private int textDeep;
    private String text ="Hello, word";
    private Color textColor=Color.RED;
    private double lightAngle;

    private WordArt(){
        super("Wod Art");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BufferedImage textImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        panel = new ImagePanel(textImage);

        add(panel);
        textAngle = Math.toRadians(50);
        textDeep = 25;
        applyInput();


        JTextField tex = new JTextField(text);
        tex.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                process();
            }
            void process(){
                text = tex.getText();
                applyInput();
            }
        });

        JSlider lit = new JSlider(0,360);
        lit.addChangeListener(e -> {
            lightAngle = lit.getValue();
            applyInput();
        });

        JButton col = new JButton("Choose Color");
        col.addActionListener(e -> {
            Color temp = JColorChooser.showDialog(null,"Choose Color", textColor);
            if (temp != null) {
                textColor = temp;
            }
            applyInput();
        });

        tex.getKeyListeners()[0].keyReleased(null);
        lit.getChangeListeners()[0].stateChanged(null);
        applyInput();

        JPanel toolsPanel = new JPanel(new GridLayout(2,2));
        toolsPanel.add(new JLabel("Text"));
        toolsPanel.add(new JLabel("Color"));
        toolsPanel.add(new JLabel("Light"));
        toolsPanel.add(tex);
        toolsPanel.add(col);
        toolsPanel.add(lit);
        add(toolsPanel,BorderLayout.SOUTH);
    }

    private void applyInput(){
        panel.setImage(makeVolume());//сделать объёмным
    }

    static private BufferedImage writeText(Color color, String text, int size, double angle){
        BufferedImage textImage = new BufferedImage(size*4+size * text.length(), size *2, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = textImage.createGraphics();
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, size));
        g.drawString(text,200, size);
        g.dispose();
        applyFrame(textImage, color, angle);
        return textImage;
    }

    private BufferedImage makeVolume(){
        int SIZE = 50;
        Image textImage = writeText(textColor, text, SIZE, lightAngle);
        int size = textImage.getHeight(null);
        BufferedImage img = new BufferedImage(textImage.getWidth(null),size*2,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = img.createGraphics();

        for(int i = 0; i < textDeep; i++)
            g.drawImage(textImage,size+(int)Math.round(i*Math.cos(textAngle)),size+(int)Math.round(i*Math.sin(textAngle)),null);
        g.dispose();
        return img;
    }

    static private void applyFrame(BufferedImage img, Color color, double angle){
        int [][] pix = convertTo2DUsingGetRGB(img);
        int mainCol = color.getRGB();
        int darkCol = color.darker().darker().getRGB();
        int brightCol = color.brighter().brighter().getRGB();
        int topCol = mixColor(brightCol, darkCol, getPercent(0,angle)*2);
        int rightCol = mixColor(brightCol, darkCol, getPercent(90,angle)*2);
        int botCol = mixColor(brightCol, darkCol, getPercent(180,angle)*2);
        int leftCol = mixColor(brightCol, darkCol, getPercent(270,angle)*2);

        for(int i = 1; i < pix[0].length - 1; i++){
            for(int j = 1; j < pix.length - 1;j++)
                if(pix[j][i] == 0){
                    if(pix[j][i-1] == mainCol) img.setRGB(i,j,leftCol);
                    if(pix[j][i+1] == mainCol) img.setRGB(i,j,rightCol);
                    if(pix[j-1][i] == mainCol) img.setRGB(i,j,topCol);
                    if(pix[j+1][i] == mainCol) img.setRGB(i,j,botCol);
                }
        }
    }
    private static double getPercent(double a, double b){
        return (a < b) ? (b - a)/360 : (a - b)/360;
    }

    private static int mixColor(int brightColor, int darkColor, double percent){
        Color x=new Color(brightColor);
        Color y=new Color(darkColor);
        int r=(int)Math.round(x.getRed()*percent+y.getRed()*(1-percent))/2;
        int g=(int)Math.round(x.getGreen()*percent+y.getGreen()*(1-percent))/2;
        int b=(int)Math.round(x.getBlue()*percent+y.getBlue()*(1-percent))/2;
        return new Color(r,g,b).getRGB();
    }

    private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getRGB(col, row);
            }
        }
        return result;
    }

    public static void main(String[] args){
        WordArt app = new WordArt();
        app.setSize(800,600);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}
