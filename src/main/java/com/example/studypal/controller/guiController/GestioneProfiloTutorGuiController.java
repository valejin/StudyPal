package com.example.studypal.controller.guiController;

import com.example.studypal.other.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class GestioneProfiloTutorGuiController extends HomeTutorGui {

    //controller grafico che contiene i metodi di gestione del profilo tutor

    @FXML
    private Label tariffaValue;

    @FXML
    private ComboBox luogoBox;

    @FXML
    private ComboBox modBox;

    @FXML
    private Slider tariffaSlider;

    @FXML
    private ComboBox materieBox;

    @FXML
    private ComboBox giorniBox;

    @FXML
    private ComboBox orarioBox;


    public void initialize() {

        //combobox luogo--------------------------------------------------------------------
        luogoBox.getItems().addAll("Roma", "Milano", "Palermo");


        //combobox modalità di lezione-------------------------------------------------------
        modBox.getItems().addAll("In presenza", "Online");

        //combobox giorni disponibili -------------------------------------------------------
        giorniBox.getItems().addAll("Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica");

        //combobox orari disponibil
        orarioBox.getItems().addAll("8:00-12:00", "12:00-17:00");


        //tariffa slider----------------------------------------------------------------------
        tariffaSlider.setMin(0);
        tariffaSlider.setMax(100);
        tariffaSlider.setValue(50); // Imposta un valore predefinito
        tariffaSlider.setShowTickLabels(true);
        tariffaSlider.setShowTickMarks(true);

        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

            tariffaValue.setText(Integer.toString(newValue.intValue()) + "€");

        });
    }

    public void confermaModificaMethod() {
        Printer.println("Conferma delle modifiche avvenuta con successo!");
    }
}
