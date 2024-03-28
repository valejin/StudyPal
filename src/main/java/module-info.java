module com.example.studypal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.studypal to javafx.fxml;
    exports com.example.studypal;
    exports com.example.studypal.controller.applicationController;
    opens com.example.studypal.controller.applicationController to javafx.fxml;
    exports com.example.studypal.controller.guiController;
    opens com.example.studypal.controller.guiController to javafx.fxml;
    exports com.example.studypal.exceptions;

    exports com.example.studypal.bean;
    opens com.example.studypal.bean to javafx.fxml;
}