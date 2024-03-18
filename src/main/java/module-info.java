module com.example.studypal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studypal to javafx.fxml;
    exports com.example.studypal;
}