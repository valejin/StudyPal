package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.studente.PrenotaRipetizioneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private Label emailLabel;
    @FXML
    private Label materiaLabel;
    @FXML
    private Label modLabel;
    @FXML
    private Label giorniLabel;
    @FXML
    private Label tariffaLabel;
    @FXML
    private Label luogoLabel;
    @FXML
    private TextArea note;

    RipetizioneInfoBean informazioni, filtri;
    List<RipetizioneInfoBean> risultati;
    private static final Logger logger = Logger.getLogger(PrenotaRipetizioneGui.class.getName());

    PrenotazioneBean prenotazioneBean;

    public PrenotaRipetizioneGui(LoggedInUserBean user, RipetizioneInfoBean tutor, List<RipetizioneInfoBean> risultatiRicercaBean, RipetizioneInfoBean filtriRicerca){
        this.user = user;
        this.informazioni = tutor;
        this.risultati = risultatiRicercaBean;
        this.filtri = filtriRicerca;
    }

    public PrenotaRipetizioneGui(LoggedInUserBean user, RipetizioneInfoBean tutor){
        this.user = user;
        this.informazioni = tutor;
    }


    /*----------------------------------------------------------------------------------------------------------------*/
    public void initialize(){

        nomeLabel.setText(informazioni.getNome());
        cognomeLabel.setText((informazioni.getCognome()));
        emailLabel.setText(informazioni.getEmail());
        tariffaLabel.setText(informazioni.getTariffa() + "€/h");
        materiaLabel.setText(filtri.getMateria());
        luogoLabel.setText(informazioni.getLuogo());

        if (filtri.getGiorni() != null){
            giorniLabel.setText(filtri.getGiorni());
        } else {
            giorniLabel.setText("Non specificato");
        }
        if (filtri.getInPresenza()!= null && filtri.getOnline() != null){
            modLabel.setText("In presenza, online");
        } else if (filtri.getInPresenza() != null){
            modLabel.setText("In presenza");
        } else if (filtri.getOnline() != null){
            modLabel.setText("Online");
        } else {
            modLabel.setText("Non specificato");
        }

        // Aggiungi un listener per controllare il numero di caratteri
        note.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 250) {
                note.setText(oldValue); // Annulla l'input che supera il limite
            }
        });

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
        }
    }

    public void prenota(){

        /*
        metodo lanciato quando viene premuto il pulsante invio di prenotazione,
        carica le informazioni in un prenotazioneBean e le manda all'applicativo
         */
        prenotazioneBean.setEmailTutor(informazioni.getEmail());
        prenotazioneBean.setEmailStudente(user.getEmail());
        prenotazioneBean.setMateria(filtri.getMateria());

        /* todo: controllare e gestire le modalità di lezione. Come le codifichiamo? Serve enum?
        if (filtri.getInPresenza() != null && filtri.getOnline()) {
            prenotazioneBean.setModLezione();
        }
        */
        prenotazioneBean.setTariffa(informazioni.getTariffa());
        if (filtri.getGiorni() != null){
            prenotazioneBean.setGiorno(filtri.getGiorni());
        }
        prenotazioneBean.setNote(note.getText());

        PrenotaRipetizioneController prenotaRipetizioneController = new PrenotaRipetizioneController();
        prenotaRipetizioneController.prenota(prenotazioneBean);
        //todo: i parametri della prenotazione li passerò al costruttore dell'applicativo o al metodo prenota???

    }

}
