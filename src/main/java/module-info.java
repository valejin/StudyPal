module com.example.studypal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studypal to javafx.fxml;
    exports com.example.studypal;
    exports com.example.studypal.controller;
    opens com.example.studypal.controller to javafx.fxml;
    exports com.example.studypal.controller.guiController;
    opens com.example.studypal.controller.guiController to javafx.fxml;
}