package com.turbulence6th;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FractalPanel extends JPanel {

    private BufferedImage image;

    public FractalPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void draw(BufferedImage image) {
        this.image = image;
        repaint();
    }
}
