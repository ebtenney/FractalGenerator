package com.fractal.fractalgenerator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("canvas-view.fxml"));
        FXMLLoader boxLoader = new FXMLLoader(getClass().getResource("box-view.fxml"));
        System.out.println(Application.class.getResource("canvas-view.fxml").toString());
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        Scene scene2 = new Scene(boxLoader.load(), 800, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
//        stage.setScene(scene2);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}