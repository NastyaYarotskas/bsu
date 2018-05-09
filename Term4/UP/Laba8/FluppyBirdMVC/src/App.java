import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class App {

    public static int WIDTH = 500;
    public static int HEIGHT = 500;

    public App() {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);

        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);

        GamePanel panel = new GamePanel();
        frame.add(panel);


    }
}
