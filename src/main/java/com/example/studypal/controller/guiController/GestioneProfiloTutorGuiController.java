package com.example.studypal.controller.guiController;

import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.GestioneProfiloTutorController;
import com.example.studypal.other.Printer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GestioneProfiloTutorGuiController extends HomeTutorGui {

    //controller grafico che contiene i metodi di gestione del profilo tutor

    @FXML
    private Label tariffaValue;

    @FXML
    private ComboBox luogoBox;

    @FXML
    private CheckBox inPresenzaBox;

    @FXML
    private CheckBox onlineBox;

    @FXML
    private Slider tariffaSlider;

    @FXML
    private TextField materieField;

    @FXML
    private MenuButton giorniMenu;


    public void initialize() {

        //combobox luogo--------------------------------------------------------------------
        luogoBox.getItems().addAll("Roma", "Milano", "Palermo");

        //combobox giorni disponibili -------------------------------------------------------
        giorniMenu.setOnAction(event -> {
            if (giorniMenu.isShowing()) {
                giorniMenu.hide();
            } else {
                giorniMenu.show();
            }
        });

        //tariffa slider----------------------------------------------------------------------

        tariffaSlider.setBlockIncrement(1);

        tariffaSlider.setMin(0);
        tariffaSlider.setMax(100);
        tariffaSlider.setValue(50); // Imposta un valore predefinito
        tariffaSlider.setShowTickLabels(true);
        tariffaSlider.setShowTickMarks(true);

        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

            tariffaValue.setText(Integer.toString(newValue.intValue()) + "€");

        });

    }

    public void gestioneProfiloMethod() {
        /*
        prende le informazioni inserite dall'utente e le inserisce in un'istanza di RipetizioneInfoBean
        invia il bean al controller applicativo che popolerà un model per mandarlo al DAO
         */

        String materie;
        Boolean inPresenza = false;
        Boolean online = false;
        String luogo;
        Integer tariffa;

        //da fare: controllo isempty

        materie = this.materieField.getText();
        luogo = (String) this.luogoBox.getValue(); //controllare se c'è un modo migliore rispetto al cast!!

        //tariffa---------------------------------------------------------------------------------------
        tariffa = (int) Math.round(this.tariffaSlider.getValue());
       // tariffa = this.tariffaSlider.getValue();

        //gestione delle modalità di presenza-------------------------------------------------------------
        if (this.inPresenzaBox.isSelected()){ inPresenza = true;}
        if (this.onlineBox.isSelected()) { online = true;}

        //gestione del menubutton dei giorni---------------------------------------
        //carichiamo nella stringa giorni tutti i giorni selezionati dal tutor

        ObservableList<MenuItem> items = giorniMenu.getItems();
        StringBuilder selectedValues = new StringBuilder();

        for (MenuItem item : items) {
            if (item instanceof CheckMenuItem) {
                CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                if (checkMenuItem.isSelected()) {
                    if (selectedValues.length() > 0) {
                        selectedValues.append(", "); // Aggiungi una virgola tra i valori
                    }
                    selectedValues.append(checkMenuItem.getText());
                }
            }
        }
        String giorni = selectedValues.toString();


        //stampo i valori a terminale------------------------------------------------------------------
        System.out.println("Le materie sono: " + materie);
        System.out.println("Le modalità sono: " + inPresenza + " " + online);
        System.out.println("Il luogo è: " + luogo);
        System.out.println("I giorni sono: " + giorni );
        System.out.println("La tariffa è: " + tariffa);

        RipetizioneInfoBean ripetizioneInfoBean = new RipetizioneInfoBean(materie, inPresenza, online, luogo, giorni, tariffa);

        GestioneProfiloTutorController gestioneProfiloTutorController = new GestioneProfiloTutorController();
        gestioneProfiloTutorController.gestioneProfiloMethod(ripetizioneInfoBean);

    }
}
