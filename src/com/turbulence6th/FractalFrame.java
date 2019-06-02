package com.turbulence6th;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class FractalFrame extends JFrame implements MouseWheelListener, MouseListener {

    private Mandelbrot mandelbrot = new Mandelbrot();
    private FractalPanel fractalPanel = new FractalPanel();
    private double magnification = 480;
    private double startX = 0;
    private double startY = 0;
    private int loss = 1;

    private int mouseStartX;
    private int mouseStartY;

    public FractalFrame(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        addMouseWheelListener(this);
        addMouseListener(this);
        add(fractalPanel);
        draw();
    }

    private void draw() {
        BufferedImage image = mandelbrot.generate(getWidth(), getHeight(), magnification, startX, startY, loss);
        fractalPanel.draw(image);
        invalidate();
        validate();
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        if (e.getWheelRotation() < 0) {
            magnification *= 1.5;
            startX += (0.4) / (magnification * 0.0025);
            startY += (0.2) / (magnification * 0.0025);
        }
        else {
            startX -= (0.4) / (magnification * 0.0025);
            startY -= (0.2) / (magnification * 0.0025);
            magnification /= 1.5;

        }

        draw();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseStartX = e.getX();
        mouseStartY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startX -= (double) (e.getX() - mouseStartX) / (getWidth() * magnification * 0.0025);
        startY -= (double) (e.getY() - mouseStartY) / (getHeight() * magnification * 0.0025);
        draw();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
