package com.turbulence6th;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mandelbrot {

    private double RED_RADIENT = Math.PI / 3;
    private double GREEN_RADIENT = Math.PI;
    private double BLUE_RADIENT = 4 * Math.PI / 3;

    BufferedImage generate(int width, int height, double magnification, double startX, double startY, int loss) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        ComplexNumber start = new ComplexNumber(startX, startY);

        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < image.getWidth(); i = i + loss) {
            for (int j = 0; j < image.getHeight(); j = j + loss) {
                int finalI = i;
                int finalJ = j;
                executor.execute(() -> {
                    int movedI = finalI - width / 2;
                    int movedJ = finalJ - height / 2;

                    ComplexNumber c = start.add(new ComplexNumber(movedI / magnification, movedJ / magnification));
                    ComplexNumber z = ComplexNumber.ZERO;

                    double iteration = 0;
                    while (z.length() < 2 && iteration < 51) {
                        z = z.multiply(z).add(c);
                        iteration++;
                    }

                    double radiant = c.radient();

                    int redConst = colorConst(radiant, RED_RADIENT);
                    int greenConst = colorConst(radiant, GREEN_RADIENT);
                    int blueConst = colorConst(radiant, BLUE_RADIENT);

                    int red = (int) (redConst * iteration / 51);
                    int green = (int) (greenConst * iteration / 51);
                    int blue = (int) (blueConst * iteration / 51);

                    Color color = new Color(red, green, blue);

                    for (int k = 0; k < loss; k++) {
                        for (int m = 0; m < loss; m++) {
                            if (finalI + k < width && finalJ + m < height) {
                                image.setRGB(finalI + k, finalJ + m, color.getRGB());
                            }
                        }
                    }
                });
            }
        }

        executor.shutdown();

        return image;
    }

    private int colorConst(double radient, double colorRandient) {
        double diffRadient = Math.abs(radient - colorRandient);
        if (diffRadient > Math.PI) {
            diffRadient = 2 * Math.PI - diffRadient;
        }

        return (int )((1 - diffRadient / Math.PI) * 0xFF);
    }
}
