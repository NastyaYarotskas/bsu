package com.bsu.yarotskas;

import javax.swing.*;

public class CircleJFrame extends JFrame{

    public CircleJFrame(){
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
