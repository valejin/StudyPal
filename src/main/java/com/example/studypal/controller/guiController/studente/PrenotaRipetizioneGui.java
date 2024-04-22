package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

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
    @FXML
    private ImageView backButton;

    RipetizioneInfoBean informazioni, filtri;
    List<RipetizioneInfoBean> risultati;
    public PrenotaRipetizioneGui(LoggedInUserBean user, RipetizioneInfoBean tutor, List<RipetizioneInfoBean> risultatiRicercaBean, RipetizioneInfoBean filtriRicerca){
        this.user = user;
        this.informazioni = tutor;
        this.risultati = risultatiRicercaBean;
        this.filtri = filtriRicerca;
    }
    private static final Logger logger = Logger.getLogger(PrenotaRipetizioneGui.class.getName());

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

    public void goToRisultati(){
        try {
            FXMLLoader loader = new FXMLLoader(PrenotaRipetizioneGui.class.getResource("/com/example/studypal/view/studente/risultatoRicerca.fxml"));
            loader.setControllerFactory(c -> new RisultatiRicercaGuiController(this.user, this.risultati, this.filtri));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) nomeLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in PrenotaRipetizioneGui (caricamento pagina) " + e.getMessage());
            e.printStackTrace();
        }
    }

}
