package com.bsu.yarotskas;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

class Clock extends JPanel {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int current = 0;
    private BufferedImage buffer;
    private Random random = new Random();
    private Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

    public Clock() {
        setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        Timer timer = new Timer(1000, e -> {
            current = (current + 1) % 60;
            buffer = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(screenSize.width / 3, 0, 500, 500);
        g.setColor(Color.BLACK);
        g.drawLine(250 + screenSize.width / 3, 250, (int) (250 + screenSize.width / 3 + 250 * Math.sin(Math.toRadians(current * 6))),
                (int) (250 - 250 * Math.cos(Math.toRadians(current * 6))));

    }
}
