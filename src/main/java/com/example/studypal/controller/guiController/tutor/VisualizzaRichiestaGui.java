package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.guiController.studente.PrenotaRipetizioneGui;
import com.example.studypal.controller.guiController.studente.RisultatiRicercaGuiController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class VisualizzaRichiestaGui extends HomeTutorGui {

    /*
    Controller grafico per mostrare i dettagli di una particolare richiesta arrivata al tutor quando il tutor clicca il
    bottone "Visualizza"
     */

    @FXML
    private Label emailStudente;
    @FXML
    private Label materia;
    @FXML
    private Label modLezione;
    @FXML
    private Label giorni;
    @FXML
    private Label note;

    PrenotazioneBean dettagliRichiesta;

    List<PrenotazioneBean> listRisultati;
    String email, materiaRichiesta, giorno, noteAggiuntive;
    int modalita;

    private static final Logger logger = Logger.getLogger(VisualizzaRichiestaGui.class.getName());

    public VisualizzaRichiestaGui(LoggedInUserBean user, PrenotazioneBean prenotazioneBean, List<PrenotazioneBean> list){
        this.user = user;
        this.dettagliRichiesta = prenotazioneBean;
        this.listRisultati = list;
    }


    public void initialize(){
        email = dettagliRichiesta.getEmailStudente();
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
        emailStudente.setText(email);
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


/*------------------tasto di ritorno alla pagina delle richieste arrivate-----------------*/
    public void goToRichiesteArrivate(){
        try {
            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/tutor/richiesteArrivate.fxml"));
            loader.setControllerFactory(c -> new RichiesteArrivateGui(this.user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) emailStudente.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in VisualizzaRichiesteGui (caricamento pagina) " + e.getMessage());
            e.printStackTrace();
        }


    }




    //todo: creare la nuova tabella in DB per tenere conto delle prenotazioni attive




}
