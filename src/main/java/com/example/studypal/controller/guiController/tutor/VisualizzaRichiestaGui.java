package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


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
    String email, materiaRichiesta, giorno, noteAggiuntive;
    int modalita;

    public VisualizzaRichiestaGui(LoggedInUserBean user, PrenotazioneBean prenotazioneBean){
        this.user = user;
        this.dettagliRichiesta = prenotazioneBean;
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





    //todo: creare il metodo per ritornare alla pagina prima, ovvero le RichiesteArrivate
    //todo: creare la nuova tabella in DB per tenere conto delle prenotazioni attive




}
