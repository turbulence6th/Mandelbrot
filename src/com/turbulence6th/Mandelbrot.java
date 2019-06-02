package com.turbulence6th;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mandelbrot {

    BufferedImage generate(int width, int height, double magnification, double startX, double startY, int loss) {
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        ComplexNumber start = new ComplexNumber(startX, startY);

        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < image.getWidth(); i = i + loss) {
            for (int j = 0; j < image.getHeight(); j = j + loss) {
                int finalI = i;
                int finalJ = j;
                executor.execute(() -> {
                   ComplexNumber c = start.add(new ComplexNumber(finalI / magnification, finalJ / magnification));
                   ComplexNumber z = ComplexNumber.ZERO;

                   int iteration = 0;
                   while (z.length() < 2 && iteration < 255) {
                       z = z.multiply(z).add(c);
                       iteration++;
                   }

                   for (int k = 0; k < loss; k++) {
                       for (int m = 0; m < loss; m++) {
                           if (finalI + k < width && finalJ + m < height) {
                               image.setRGB(finalI + k, finalJ + m, 255 - iteration);
                           }
                       }
                   }
               });
            }
        }

        executor.shutdown();

        return image;
    }
}