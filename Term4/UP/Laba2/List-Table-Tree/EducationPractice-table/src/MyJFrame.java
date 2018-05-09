import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class MyJFrame extends JFrame{

    public MyJFrame(){
        super();

        HashMap<String, Pair<String, ImageIcon>> map = new HashMap<>();
        try {
            FileReader reader = new FileReader("countries.txt");
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNext()) {
                String country = scanner.next();
                String capital = scanner.next();
                String file = "plain/flag_" + scanner.next() + ".png";
                ImageIcon flag = new ImageIcon(file);
                map.put(country, new Pair<String, ImageIcon>(capital, flag));
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Exception");
        }

        JPanel panel = new JPanel();
        add(panel);

        TourPanel tours = new TourPanel(map);
        panel.add(tours);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu tour = new JMenu("Options");
        menuBar.add(tour);

        JMenuItem cost = new JMenuItem("Get Cost");
        cost.addActionListener(e -> {
            tours.getCost();
        });
        tour.add(cost);

        JMenuItem add = new JMenuItem("Add new tour");
        add.addActionListener(e -> {
            tours.addNewTour();
        });
        tour.add(add);

        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
