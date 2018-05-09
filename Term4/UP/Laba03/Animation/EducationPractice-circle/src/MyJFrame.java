import javax.swing.*;

public class MyJFrame extends JFrame{

    public MyJFrame(){
        super("Timer");

        JPanel panel = new JPanel();
        add(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.add(new Clock());

        setResizable(false);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
