module com.fractal.fractalgenerator {
    requires javafx.controls;
    requires javafx.fxml;
//    requires kotlin.stdlib;
    requires com.almasb.fxgl.all;

    opens com.fractal.fractalgenerator to javafx.fxml;
    exports com.fractal.fractalgenerator;
}