/**
 * This product currently only contains code developed by authors
 * of specific components, as identified by the source code files.
 * <p>
 * Since product implements StAX API, it has dependencies to StAX API
 * classes.
 * <p>
 * For additional credits (generally to people who reported problems)
 * see CREDITS file.
 */
package com.turbulence6th;

import com.aparapi.Kernel;
import com.aparapi.Range;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mandelbrot {

    private double RED_RADIENT = Math.PI / 3;
    private double GREEN_RADIENT = Math.PI;
    private double BLUE_RADIENT = 4 * Math.PI / 3;

    BufferedImage generate(int width, int height, double magnification, double startr, double starti, int loss) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] screen = new int[image.getWidth()][image.getHeight()];
        Color color = new Color(0, 0, 255);
        int rgb = color.getRGB();
        Kernel kernel = new Kernel(){
            @Override public void run() {
                int finalI = getGlobalId(0);
                int finalJ = getGlobalId(1);

                int movedI = finalI - width / 2;
                int movedJ = finalJ - height / 2;

                double cr = startr + movedI / magnification;
                double ci = starti + movedJ / magnification;

                double zr = 0;
                double zi = 0;

                double iteration = 0;
                while (zr * zr + zi * zi < 8 && iteration < 51) {
                    double tzr = zr * zr - zi * zi;
                    double tzi = zr * zi * 2;

                    zr = tzr + cr;
                    zi = tzi + ci;

                    iteration++;
                }

                if (iteration < 51) {
                    screen[finalI][finalJ] = rgb;
                }
            }
        };

        kernel.execute(Range.create2D(image.getWidth(), image.getHeight()));
        kernel.dispose();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.setRGB(i, j, screen[i][j]);
            }
        }


//        ExecutorService executor = Executors.newCachedThreadPool();
//        for (int i = 0; i < image.getWidth(); i = i + loss) {
//            for (int j = 0; j < image.getHeight(); j = j + loss) {
//                int finalI = i;
//                int finalJ = j;
//
//                int movedI = finalI - width / 2;
//                int movedJ = finalJ - height / 2;
//
//                ComplexNumber c = start.add(new ComplexNumber(movedI / magnification, movedJ / magnification));
//                ComplexNumber z = ComplexNumber.ZERO;
//
//                double iteration = 0;
//                while (z.length() < 2 && iteration < 51) {
//                    z = z.multiply(z).add(c);
//                    iteration++;
//                }
//
//                double radiant = c.radient();
//
//                int redConst = colorConst(radiant, RED_RADIENT);
//                int greenConst = colorConst(radiant, GREEN_RADIENT);
//                int blueConst = colorConst(radiant, BLUE_RADIENT);
//
//                int red = (int) (redConst * iteration / 51);
//                int green = (int) (greenConst * iteration / 51);
//                int blue = (int) (blueConst * iteration / 51);
//
//                Color color = new Color(red, green, blue);
//
//                for (int k = 0; k < loss; k++) {
//                    for (int m = 0; m < loss; m++) {
//                        if (finalI + k < width && finalJ + m < height) {
//                            image.setRGB(finalI + k, finalJ + m, color.getRGB());
//                        }
//                    }
//                }
//
//            }
//        }

//        executor.shutdown();

        return image;
    }

    private int colorConst(double radient, double colorRandient) {
        double diffRadient = Math.abs(radient - colorRandient);
        if (diffRadient > Math.PI) {
            diffRadient = 2 * Math.PI - diffRadient;
        }

        return (int) ((1 - diffRadient / Math.PI) * 0xFF);
    }
}
