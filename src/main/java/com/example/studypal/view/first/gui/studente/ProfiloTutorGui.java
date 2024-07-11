package com.example.studypal.view.first.gui.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ProfiloTutorGui extends HomeStudenteGui {

    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label materieLabel;
    @FXML
    private Label modLabel;
    @FXML
    private Label giorniLabel;
    @FXML
    private Label tariffaLabel;
    @FXML
    private Label luogoLabel;

    RipetizioneInfoBean tutor;
    RipetizioneInfoBean filtri;
    List<RipetizioneInfoBean> risultatiRicerca;

    private static final Logger logger = Logger.getLogger(ProfiloTutorGui.class.getName());

    public ProfiloTutorGui(LoggedInUserBean user, RipetizioneInfoBean tutorSelezionato,
                           List<RipetizioneInfoBean> risultatiRicerca, RipetizioneInfoBean filtri){
        this.user = user;
        this.tutor = tutorSelezionato;
        this.risultatiRicerca = risultatiRicerca;
        this.filtri = filtri;
    }

    public void initialize(){

        /* mostro i dati del profilo selezionato*/
        nomeLabel.setText(tutor.getNome());
        cognomeLabel.setText(tutor.getCognome());
        emailLabel.setText(tutor.getEmail());
        materieLabel.setText(tutor.getMaterie());
        giorniLabel.setText(tutor.getGiorni());
        tariffaLabel.setText(tutor.getTariffa() + "€/h");
        luogoLabel.setText(tutor.getLuogo());


        if ((tutor.getInPresenza()!= null) || (tutor.getOnline() != null)) {
            if (Boolean.TRUE.equals(tutor.getInPresenza()) && Boolean.TRUE.equals(tutor.getOnline())) {
                modLabel.setText("In presenza & Online");
            } else if (Boolean.TRUE.equals(tutor.getOnline())) {
                modLabel.setText("Online");
            } else if (Boolean.TRUE.equals(tutor.getInPresenza())) {
                modLabel.setText("In presenza");
            } else if (Boolean.FALSE.equals(tutor.getInPresenza()) && Boolean.FALSE.equals(tutor.getOnline())) {
                modLabel.setText("In presenza o Online");
            }
        }
    }

    public void goToRisultati(){
        try {
            FXMLLoader loader = new FXMLLoader(CercaRipetizioneGui.class.getResource("/com/example/studypal/view/studente/risultatoRicerca.fxml"));
            loader.setControllerFactory(c -> new RisultatiRicercaGuiController(user, risultatiRicerca, filtri));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) nomeLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RisultatiRicercaGuiController " + e.getMessage());

        }
    }

}
