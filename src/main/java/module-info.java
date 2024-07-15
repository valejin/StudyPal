module com.example.studypal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.studypal to javafx.fxml;
    exports com.example.studypal;
    exports com.example.studypal.dao;
    exports com.example.studypal.model;
    exports com.example.studypal.controller.application;
    opens com.example.studypal.controller.application to javafx.fxml;
    exports com.example.studypal.view.first.gui;
    opens com.example.studypal.view.first.gui to javafx.fxml;
    exports com.example.studypal.exceptions;

    exports com.example.studypal.bean;
    opens com.example.studypal.bean to javafx.fxml;
    exports com.example.studypal.view.first.gui.studente;
    opens com.example.studypal.view.first.gui.studente to javafx.fxml;
    exports com.example.studypal.view.first.gui.tutor;
    opens com.example.studypal.view.first.gui.tutor to javafx.fxml;
    exports com.example.studypal.controller.application.studente;
    opens com.example.studypal.controller.application.studente to javafx.fxml;
    exports com.example.studypal.controller.application.tutor;
    opens com.example.studypal.controller.application.tutor to javafx.fxml;
}