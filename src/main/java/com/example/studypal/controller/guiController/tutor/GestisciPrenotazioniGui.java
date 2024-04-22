package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class GestisciPrenotazioniGui extends HomeTutorGui {

    //controller grafico che contiene i metodi di gestione delle prenotazioni

    @FXML
    private TableColumn<RipetizioneInfoBean, String> email;
    @FXML
    private TableColumn<RipetizioneInfoBean, String> materia;
    @FXML
    private TableColumn<RipetizioneInfoBean, Button> visualizza;


    public GestisciPrenotazioniGui(LoggedInUserBean user){ this.user = user;}






}
