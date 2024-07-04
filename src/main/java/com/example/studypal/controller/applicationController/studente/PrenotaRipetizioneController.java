package com.example.studypal.controller.applicationController.studente;

import com.example.studypal.DAO.PrenotazioneDAO;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.observer.RichiesteArrivateCollection;

import java.sql.SQLException;

public class PrenotaRipetizioneController {


    /* costruttore che riceve come parametri la sessione utente e le informazioni relative alla ripetizione da prenotare*/
    public PrenotaRipetizioneController(){}

    public void prenota(PrenotazioneBean prenotazioneBean) {
        /*
        prende le informazioni di prenotazione dal bean passatogli dal controller grafico PrenotaRipetizioneGUI
        le mette in un prenotazioneModel per passare al DAO
        istanzia e chiama il PrenotazioneDAO per caricare la richiesta nel database
         */

        /*istanzio model e ci inserisco i dati della prenotazione------------------*/
        PrenotazioneModel prenotazioneModel = new PrenotazioneModel(prenotazioneBean.getIdRichiesta(), prenotazioneBean.getNome(), prenotazioneBean.getCognome(), prenotazioneBean.getEmailTutor(), prenotazioneBean.getEmailStudente(), prenotazioneBean.getMateria(),
                prenotazioneBean.getModLezione(), prenotazioneBean.getTariffa(), prenotazioneBean.getGiorno(), prenotazioneBean.getNote(), prenotazioneBean.getStato());


        /*istanzio DAO e gli passo il model----------------------------------------*/
        try{
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            prenotazioneDAO.prenota(prenotazioneModel);
            RichiesteArrivateCollection.getInstance().aggiungiRichiesta(prenotazioneModel); //pattern Observer

        } catch (SQLException e){
            System.out.println("prenota controller errore");
        }


    }

}
