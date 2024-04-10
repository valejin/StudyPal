package com.example.studypal.controller.guiController;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.w3c.dom.ls.LSOutput;

public class PrenotaRipetizioneStudenteGui extends HomeStudenteGui {

    @FXML
    private TextField cercaMateria;
    @FXML
    private ComboBox materia;
    @FXML
    private ComboBox modalitaLez;
    @FXML
    private ComboBox luogo;
    @FXML
    private ComboBox giorno;
    @FXML
    private ComboBox fasciaOraria;
    @FXML
    private Slider tariffaSlider;
    @FXML
    private Label tariffaValue;

    @FXML
    public void initialize() {
        // Imposta il valore minimo e massimo del Slider

        tariffaSlider.setMin(5);
        tariffaSlider.setMax(50);
        tariffaSlider.setValue(5); // Imposta un valore predefinito(minimo)
        tariffaSlider.setShowTickLabels(true);
        tariffaSlider.setShowTickMarks(true);

        // Per visualizzare dinamicamente il valore di Slider
        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            tariffaValue.setText(Integer.toString(newValue.intValue()) + "€");

        });
    }




}
