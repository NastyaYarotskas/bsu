package com.bsu.yarotskas;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MyJFrame extends JFrame{

    private CountriesPanel panel1;

    public MyJFrame(){
        super("List of countries");

        Map<String, Pair<String, ImageIcon>> map = new HashMap<>();
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
            JOptionPane.showMessageDialog(null, "Incorrect file");
        }

        JPanel panel = new JPanel();
        add(panel);

        panel.add(new CountriesPanel(map));

        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
