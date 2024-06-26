package com.example.studypal.controller.applicationController.tutor;

import com.example.studypal.DAO.PrenotazioneDAO;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.guiController.tutor.GestisciPrenotazioniGui;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;

import java.util.ArrayList;
import java.util.List;

public class RichiesteArrivateController extends GestisciPrenotazioniGui {


    //funzione chiamata da Controller grafico corrispondente per ottenere le info per stampare a schermo
    public List<PrenotazioneBean> richiesteArrivate(LoggedInUserBean user, int flag) {

        //creo una lista di BEAN che prende i dati
        List<PrenotazioneBean> prenotazioneBean = new ArrayList<>();


        try {
            //istanzio il DAO
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            List<PrenotazioneModel> risultatiRicerca;

            String email = user.getEmail();

            //chiamo la funzione di DAO che si occupa estrazione di dati dal DB
            //dove passare email del tutor loggato come argomento
            risultatiRicerca = prenotazioneDAO.richiesteArrivate(email, flag);


            //passo la lista di model alla lista di BEAN
            for (PrenotazioneModel risultato : risultatiRicerca) {
                PrenotazioneBean risultatoBean = new PrenotazioneBean(risultato.getIdRichiesta(), risultato.getNome(), risultato.getCognome(), risultato.getEmailTutor(), risultato.getEmailStudente(), risultato.getMateria(), risultato.getModLezione(), risultato.getTariffa(), risultato.getGiorno(), risultato.getNote(), risultato.getStato());

                prenotazioneBean.add(risultatoBean);
            }


        } catch (NonProduceRisultatoException e) {
            Printer.println("Non produce risultato da DB.");
        }

        return prenotazioneBean;
    }


    public void modificaStatoRichiesta(Integer richiesta, Integer stato){
        //metodo che modifica lo stato di una richiesta, a seconda dei parametri ricevuti la conferma oppure la rifiuta

        PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
        prenotazioneDAO.modificaStatoRichiesta(richiesta, stato);
    }

}
