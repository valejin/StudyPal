package com.example.studypal.controller.guiController;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.model.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrenotaRipetizioneGui extends HomeStudenteGui {

    /*
    controller grafico che si occupa della pagina di richiesta di una ripetizione
     */

    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label materiaLabel;
    @FXML
    private Label modLabel;
    @FXML
    private Label giorniLabel;

    RipetizioneInfoBean informazioni;

    public PrenotaRipetizioneGui(LoggedInUserBean user, RipetizioneInfoBean tutor){
        this.user = user;
        this.informazioni = tutor;
    }

    public void initialize(){

        nomeLabel.setText(informazioni.getNome());
        cognomeLabel.setText((informazioni.getCognome()));
        materiaLabel.setText(informazioni.getMateria());
        giorniLabel.setText(informazioni.getGiorni());
    }

}
