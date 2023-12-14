package com.fractal.fractalgenerator;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MandelbrotController {

    @FXML private Canvas mandelbrotCanvas;
    @FXML

    private double initialX, initialY;



    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static double[] range = {-2.0, 1.0, 1.5, -1.5}; // stores the current render bounds in complex plane
    // format { leftbound, rightbound, upperbound, lowerbound}

    private static final int MAX_ITERATIONS = 1000;

    private PixelWriter gc;
    @FXML
    public void initialize() {
        drawMandelbrot(WIDTH, HEIGHT);
    }
    private void drawMandelbrot(int w, int h) {
        drawMandelbrot(1, w, 1, h);
    }

    private void drawMandelbrot(double left, double right, double upper, double lower) {
        gc = mandelbrotCanvas.getGraphicsContext2D().getPixelWriter();

        System.out.printf("Rendering: %f, %f, %f, %f", left, right, upper, lower);
        // console print for sanity
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

        //save bounds of new range
        range[0] = map(left,0, 800, range[0], range[1]);
        range[1] = map(right,0, 800, range[0], range[1]);
        range[2] = map(upper,0, 800, range[2], range[3]);
        range[3] = map(lower,0, 800, range[2], range[3]);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {



                //map from 0 -800 to given pixels bounds
                double zx = map(x, 0, WIDTH, range[0], range[1]);
                double zy = map(y, 0, HEIGHT, range[2], range[3]);


//                //map from 0 -800 to given pixels bounds
//                double tempx = map(x, 0, WIDTH, left, right);
//                double tempy = map(y, 0, HEIGHT, upper, lower);
////                System.out.printf("Rendering: %f, %f\n", tempx, tempy);
//
//
//                //map given pixel bounds to range in complex plane
//                double zx = map(tempx,left, right, range[0], range[1]);
//                double zy = map(tempy,upper, lower, range[2], range[3]);
////                System.out.printf("Rendering: %f, %f\n", zx, zy);

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


        System.out.printf("Rendering: %f, %f, %f, %f", range[0], range[1], range[2], range[3]);
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

    private Rectangle resizableBox;

    @FXML
    private Pane rootPane;

    @FXML
    private void handleMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();
        resizableBox = new Rectangle(event.getSceneX(), event.getSceneY(), 10,10);
        resizableBox.setStroke(Paint.valueOf("white"));
        resizableBox.setFill(Paint.valueOf("transparent"));
//        resizableBox.setVisible(true);
        rootPane.getChildren().add(resizableBox);

        System.out.println("Mouse Clicked!");
    }

    @FXML
    private void handleMouseDragged(MouseEvent event) {
        if (resizableBox != null) {
            double currentX = event.getX();
            double currentY = event.getY();

            double width = currentX - initialX;
            double height = currentY - initialY;
            if (Math.abs(width) > Math.abs(height)) {
                resizableBox.setWidth(Math.abs(height));
                resizableBox.setHeight(Math.abs(height));
                resizableBox.setX(width < 0 ? currentX : initialX);
                resizableBox.setY(height < 0 ? currentY : initialY);
            } else {

                resizableBox.setWidth(Math.abs(width));
                resizableBox.setHeight(Math.abs(width));

                resizableBox.setX(width < 0 ? currentX : initialX);
                resizableBox.setY(height < 0 ? currentY : initialY);
            }
            System.out.println("Mouse Dragged");
        }
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        System.out.println("Mouse released");
        resizableBox.setStroke(Paint.valueOf("transparent"));
        resizableBox = null;

        double currentX = event.getX();
        double currentY = event.getY();

        double width = currentX - initialX;
        double height = currentY - initialY;

        if (width > height) {
            drawMandelbrot(width < 0 ? initialX - height : initialX, width < 0 ? initialX : initialX+ height,
                    height < 0 ? currentY : initialY, height < 0 ? initialY : currentY);
        } else {
            drawMandelbrot(width < 0 ? currentX : initialX, width < 0 ? initialX : currentX,
                    height < 0 ? initialY - width: initialY, height < 0 ? initialY : initialY+width);
        }
        /*
        if (Math.abs(width) > Math.abs(height)) {
//            resizableBox.setWidth(Math.abs(height));
//            resizableBox.setHeight(Math.abs(height));
            drawMandelbrot(width < 0 ? currentX : initialX, width < 0 ? initialX : currentX,
                    width < 0 ? currentY : initialY, width < 0 ? initialY : currentY);
            resizableBox.setX(width < 0 ? currentX : initialX);
            resizableBox.setY(height < 0 ? currentY : initialY);
        } else {

            resizableBox.setWidth(Math.abs(width));
            resizableBox.setHeight(Math.abs(width));

            resizableBox.setX(width < 0 ? currentX : initialX);
            resizableBox.setY(height < 0 ? currentY : initialY);
        }

        //Quadrant 4
        if(initialX < currentX && initialY < currentY) {
            drawMandelbrot(initialX, currentX, initialY, currentY);
        } else if (initialX > currentX && initialY < currentY) { // quadrant 3
            drawMandelbrot(currentX, initialX, initialY, currentY);
        } else if (initialX < currentX && initialY > currentY) { // quadrant 1

            drawMandelbrot(initialX, currentX, currentY, initialY);
        } else {
            drawMandelbrot(currentX, initialX, currentY, initialY);
        }

         */
    }

    private double map(double value, double start1, double stop1, double start2, double stop2) {
        return start2 + (value - start1) * ((stop2 - start2) / (stop1 - start1));
    }
}
