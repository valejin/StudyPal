package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.guiController.tutor.RichiesteArrivateGui;
import com.example.studypal.controller.guiController.tutor.VisualizzaRichiestaGui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class VisualizzaRichiesteStudenteGui extends HomeStudenteGui {

    @FXML
    Label emailTutor;
    @FXML
    private Label materia;
    @FXML
    private Label modLezione;
    @FXML
    private Label giorni;
    @FXML
    private Label note;
    PrenotazioneBean dettagliRichiesta;
    List <PrenotazioneBean> listaRichieste;
    String email, materiaRichiesta, giorno, noteAggiuntive;
    int modalita;

    private static final Logger logger = Logger.getLogger(VisualizzaRichiesteStudenteGui.class.getName());

    public VisualizzaRichiesteStudenteGui(LoggedInUserBean user, PrenotazioneBean prenotazioneSelezionata, List<PrenotazioneBean> listaRichieste){
        this.user = user;
        this.dettagliRichiesta = prenotazioneSelezionata;
        this.listaRichieste = listaRichieste;
    }

    public void initialize(){

        email = dettagliRichiesta.getEmailTutor();
        materiaRichiesta = dettagliRichiesta.getMateria();
        modalita = dettagliRichiesta.getModLezione();

        giorno = dettagliRichiesta.getGiorno();
        if (giorno != null && !giorno.isEmpty()) {
            giorni.setText(giorno);
        }else{
            giorni.setText("Non specificato");
        }

        noteAggiuntive = dettagliRichiesta.getNote();


        /* -------------------setto i label nell'interfaccia utente---------------------------------*/
        emailTutor.setText(email);
        materia.setText(materiaRichiesta);

        if(modalita == 0) {
            modLezione.setText("Non specificato");
        } else if (modalita == 1) {
            modLezione.setText("In presenza");
        } else if (modalita == 2) {
            modLezione.setText("Online");
        }

        note.setText(noteAggiuntive);
    }

    public void goToRichiesteInviate(){
        try {
            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/studente/richiesteInviate.fxml"));
            loader.setControllerFactory(c -> new RichiesteInviateGui(this.user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) emailTutor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in VisualizzaRichiesteStudenteGui (caricamento pagina) " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancellaRichiesta() {

    }

}