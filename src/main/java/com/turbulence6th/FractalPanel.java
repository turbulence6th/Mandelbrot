/**
 * This product currently only contains code developed by authors
 * of specific components, as identified by the source code files.
 *
 * Since product implements StAX API, it has dependencies to StAX API
 * classes.
 *
 * For additional credits (generally to people who reported problems)
 * see CREDITS file.
 */
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
