package com.example.studypal.controller.guiController;

import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class PopupGui {

    @FXML
    private Slider recensioneSlider;

    int idRichiesta;
    int recensione;

    public PopupGui(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public void initialize(){
        recensioneSlider.setMinorTickCount(0);
        recensioneSlider.setSnapToTicks(true);
        recensioneSlider.setBlockIncrement(1);
    }

    public void recensioneMethod(){

        recensione = (int) Math.round(this.recensioneSlider.getValue());
        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController();
        gestisciPrenotazioniStudenteController.recensioneMethod(idRichiesta, recensione);
        Stage popup = (Stage) recensioneSlider.getScene().getWindow();
        popup.close();
    }

}
