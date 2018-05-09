import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MyJFrame extends JFrame{
    private int diraction = 2;
    private Timer timer;

    public MyJFrame(){
        super("Timer");

        JPanel panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 30);
        String[] action = { "->        ", "<-      ", "-       "};
        JList<String> list = new JList<>(action);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(2);
        list.addListSelectionListener(e -> diraction = list.getSelectedIndex());

        setLayout(new BorderLayout());
        add(slider, BorderLayout.NORTH);
        add(list, BorderLayout.EAST);

        CircleImage circlePanel = new CircleImage();

        add(circlePanel , BorderLayout.CENTER);

        ActionListener listener = e -> {
            circlePanel.setAngle(slider.getValue(), diraction);
            repaint();
        };

        timer = new Timer(10, listener);
        timer.start();

        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}