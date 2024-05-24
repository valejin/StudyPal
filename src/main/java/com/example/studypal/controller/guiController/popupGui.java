package com.example.studypal.controller.guiController;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class popupGui {

    @FXML
    private Slider recensioneSlider;

    LoggedInUserBean user;

    public void popupGui(LoggedInUserBean user){
        this.user = user;
    }

    public void recensioneMethod(){
        int recensione;
        recensione = (int) Math.round(this.recensioneSlider.getValue());

        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController(user);


    }

}
