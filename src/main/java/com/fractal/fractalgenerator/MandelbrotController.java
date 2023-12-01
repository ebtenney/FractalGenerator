package com.fractal.fractalgenerator;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class MandelbrotController {

    @FXML private Canvas mandelbrotCanvas;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int MAX_ITERATIONS = 1000;

    private PixelWriter gc;
    @FXML
    public void initialize() {
        drawMandelbrot();
    }

    private void drawMandelbrot() {
        gc = mandelbrotCanvas.getGraphicsContext2D().getPixelWriter();

        for (double y = 1; y >= -1; y -= 0.05) {
            for (double x = -2; x <= 0.5; x += 0.025) {

                int n = mandelbrotIteration(x, y);
                // int color = fd.getColor(n, MAX_ITERATIONS);
                // pixelColors[y + x * WIDTH] = color;
                if (n == MAX_ITERATIONS) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                double zx = map(x, 0, WIDTH, -2.0, 1.0);
                double zy = map(y, 0, HEIGHT, -1.5, 1.5);

                int iteration = mandelbrotIteration(zx, zy);

                //set color
                if (iteration == MAX_ITERATIONS) {
                    gc.setColor(x, y, Color.BLACK);
//                    System.out.printf("coloring pixel: (%d,%d) to black\n",x,y);
                } else {
                    double hue = (iteration % 256) / 255.0;
                    gc.setColor(x, y, Color.hsb(hue * 360, 1.0, 1.0));
//                    System.out.printf("coloring pixel: (%d,%d) to %f\n",x,y,hue);
                }
            }
        }
    }

    private int mandelbrotIteration(double zx, double zy) {
        double x = zx;
        double y = zy;

        int iteration = 0;

        while (iteration < MAX_ITERATIONS && x * x + y * y < 4.0) {
            double xTemp = x * x - y * y + zx;
            y = 2.0 * x * y + zy;
            x = xTemp;
            iteration++;
        }

        return iteration;
    }

    private double map(double value, double start1, double stop1, double start2, double stop2) {
        return start2 + (value - start1) * ((stop2 - start2) / (stop1 - start1));
    }
}
