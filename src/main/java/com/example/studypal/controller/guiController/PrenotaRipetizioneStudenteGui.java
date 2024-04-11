package com.example.studypal.controller.guiController;

import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.controller.applicationController.PrenotaRipetizioneController;
import com.example.studypal.other.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.ls.LSOutput;

public class PrenotaRipetizioneStudenteGui extends HomeStudenteGui {

    @FXML
    private TextField cercaMateria;

    @FXML
    private ComboBox luogo;
    @FXML
    private MenuButton giorno;
    @FXML
    private Slider tariffaSlider;
    @FXML
    private Label tariffaValue;
    @FXML
    private CheckBox inPresenza;
    @FXML
    private CheckBox online;


    @FXML
    public void initialize() {
        //materia textfield ----------------------------------------------------------------------


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

        //luogo----------------------------------------------
        luogo.getItems().addAll("Roma", "Milano", "Palermo");


        //combobox giorni disponibili -------------------------------------------------------
        giorno.setOnAction(event -> {
            if (giorno.isShowing()) {
                giorno.hide();
            } else {
                giorno.show();
            }
        });

    }


    public void ricercaMethod(){


        if(this.luogo.getSelectionModel().isEmpty() &&
                this.giorno.getItems().isEmpty() &&
                (!this.inPresenza.isSelected() || !this.online.isSelected()) &&
                !this.tariffaSlider.isValueChanging()
        ){
            //se tutti i campi aggiuntivi sono vuoti, allora la ricerca va fatta solo per materia
            ricercaMateria();
        }else{
            //altrimenti la ricerca avviene anche con i filtri aggiunti
            Printer.println("fai ricerca con filtri");
        }

    }

    public void ricercaMateria(){
        /*
        prende la materia da BEAN
         */

        String materia;

        //da fare il controllo se il campo isEmpty
        materia = this.cercaMateria.getText();

        Printer.println("La materia inserita è: " + materia);

        //prendo un BEAN base e inserisco info
        BaseInfoBean baseInfoBean = new BaseInfoBean(materia);

        PrenotaRipetizioneController prenotaRipetizioneController = new PrenotaRipetizioneController();

        //chiama il controller applicativo e gli passa il BEAN che contiene la materia
        prenotaRipetizioneController.prenotaRipetizioneMethod(baseInfoBean);

    }






}
