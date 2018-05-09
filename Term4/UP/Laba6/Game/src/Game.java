import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends JFrame{
    private static final int PART_SIZE = 100;
    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;
    private static final int EMPTY = X_SIZE * Y_SIZE - 1;
    private static final String IMAGE_NAME="img/image.png";

    private final Image[] arrImage = new Image[X_SIZE*Y_SIZE];
    private final int[][] data = new int[X_SIZE][Y_SIZE];

    public Game(){
        super("Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BufferedImage img= getScaledImage(new ImageIcon(IMAGE_NAME).getImage(),PART_SIZE * X_SIZE,
                PART_SIZE * Y_SIZE);

        for(int i = 0; i < X_SIZE; i++)
            for(int j = 0; j < Y_SIZE; j++){
                data[i][j] = i * X_SIZE + j;
                arrImage[data[i][j]] = img.getSubimage(i * PART_SIZE,j * PART_SIZE, PART_SIZE, PART_SIZE);
            }

        MyPanel panel=new MyPanel();
        panel.mix();
        add(panel);

        setSize(PART_SIZE * X_SIZE,PART_SIZE * Y_SIZE + 22);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class MyPanel extends JPanel{

        MyPanel(){
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    x /= PART_SIZE;
                    y /= PART_SIZE;

                    if(canGo(x,y)){
                        repaint();
                        check();
                    }
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    mouseClicked(e);
                }
            });
        }

        private void check(){
            for(int i = 0; i < X_SIZE; i++)
                for(int j = 0; j < Y_SIZE; j++)
                    if(data[i][j] != i * X_SIZE + j) return;
            JOptionPane.showMessageDialog(this,"Win");
        }

        private boolean canGo(int x, int y){
            return tryGoTo(x-1,y,x,y) ||
                            tryGoTo(x,y - 1, x, y) ||
                            tryGoTo(x + 1, y, x, y) ||
                            tryGoTo(x,y + 1, x, y);
        }

        private boolean tryGoTo(int x, int y, int a, int b){
            if(!isFrame(x,0,X_SIZE-1) || !isFrame(y,0,Y_SIZE-1)) return false;
            if(data[x][y] != EMPTY) return false;
            data[x][y] = data[a][b];
            data[a][b] = EMPTY;
            return true;
        }

        public void mix(){
            Random rnd = new Random();
            for(int i = 0; i < 100; i++)
                canGo(rnd.nextInt(X_SIZE),rnd.nextInt(Y_SIZE));
            MyPanel.this.repaint();
        }

        @Override
        public void paint(Graphics g){
            int xSize=this.getWidth();
            int ySize=this.getHeight();
            g.clearRect(0,0,xSize,ySize);
            for(int i = 0; i < X_SIZE; i++)
                for(int j = 0; j < Y_SIZE; j++)
                    g.drawImage(arrImage[data[i][j]],i * PART_SIZE,j * PART_SIZE,null);
        }
    }

    static private BufferedImage getScaledImage(Image srcImg, int w, int h){///mashtab
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }


    static private boolean isFrame(int x, int from, int to){
        return from<=x && x<=to;
    }
}
