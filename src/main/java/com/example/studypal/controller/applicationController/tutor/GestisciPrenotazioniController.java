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

public class GestisciPrenotazioniController extends GestisciPrenotazioniGui {


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
                LoggedInUserBean userBean = new LoggedInUserBean(risultato.getEmailTutor(), risultato.getNome(), risultato.getCognome());
                List<Integer> valori = new ArrayList<>();
                valori.add(risultato.getModLezione());
                valori.add(risultato.getTariffa());
                valori.add(risultato.getStato());
                PrenotazioneBean risultatoBean = new PrenotazioneBean(risultato.getIdRichiesta(), userBean,
                        risultato.getEmailStudente(), risultato.getMateria(), risultato.getGiorno(),
                        risultato.getNote(), valori);

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

    public List<PrenotazioneBean> convertiRichieste(List<PrenotazioneModel> richiesteList){
        //questo metodo serve per convertire in Bean i model della collezione per il pattern observer

        List<PrenotazioneBean> prenotazioneBean = new ArrayList<>();

        for (PrenotazioneModel risultato : richiesteList) {
            LoggedInUserBean userBean = new LoggedInUserBean(risultato.getEmailTutor(), risultato.getNome(), risultato.getCognome());

            //risultato.getModLezione(), risultato.getTariffa(), risultato.getStato()

            List<Integer> valori = new ArrayList<>();
            valori.add(risultato.getModLezione());
            valori.add(risultato.getTariffa());
            valori.add(risultato.getStato());
            PrenotazioneBean risultatoBean = new PrenotazioneBean(risultato.getIdRichiesta(), userBean, risultato.getEmailStudente(), risultato.getMateria(), risultato.getGiorno(), risultato.getNote(), valori);

            prenotazioneBean.add(risultatoBean);
        }

        return prenotazioneBean;
    }

}
