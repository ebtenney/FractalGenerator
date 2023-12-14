package com.fractal.fractalgenerator;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BoxController {
    @FXML

    private double initialX, initialY;
    private Rectangle resizableBox;

    @FXML
    private Pane rootPane;

    @FXML
    private void handleMousePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();
        resizableBox = new Rectangle(event.getSceneX(), event.getSceneY(), 10,10);
        resizableBox.setStroke(Paint.valueOf("black"));
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

            resizableBox.setWidth(Math.abs(width));
            resizableBox.setHeight(Math.abs(height));

            resizableBox.setX(width < 0 ? currentX : initialX);
            resizableBox.setY(height < 0 ? currentY : initialY);

            System.out.println("Mouse Dragged");
        }
    }

    @FXML
    private void handleMouseReleased(MouseEvent event) {
        System.out.println("Mouse released");
        resizableBox.setStroke(Paint.valueOf("transparent"));
        resizableBox = null;
    }

//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}