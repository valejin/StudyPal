package com.example.studypal.controller.applicationController.tutor;

import com.example.studypal.DAO.PrenotazioneDAO;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;

import java.util.ArrayList;
import java.util.List;

public class RichiesteArrivateController {


    //funzione chiamato da Controller grafico corrispondente per ottenere le info per stampare a schermo
    public List<PrenotazioneBean> richiesteArrivate(LoggedInUserBean user) {

        //creo una lista di BEAN che prende i dati
        List<PrenotazioneBean> prenotazioneBean = new ArrayList<>();


        try {
            //istanzio il DAO
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            List<PrenotazioneModel> risultatiRicerca = new ArrayList<>();

            String email = user.getEmail();

            //chiamo la funzione di DAO che si occupa estrazione di dati dal DB
            //dove passare email del tutor loggato come argomento
            risultatiRicerca = prenotazioneDAO.richiesteArrivate(email);


            //passo la lista di model alla lista di BEAN
            for (PrenotazioneModel risultato : risultatiRicerca) {
                PrenotazioneBean risultatoBean = new PrenotazioneBean(risultato.getIdRichiesta(), risultato.getEmailTutor(), risultato.getEmailStudente(), risultato.getMateria(), risultato.getModLezione(), risultato.getTariffa(), risultato.getGiorno(), risultato.getNote());

                prenotazioneBean.add(risultatoBean);
            }


        } catch (NonProduceRisultatoException e) {
            Printer.println("Non produce risultato da DB.");
        }

        return prenotazioneBean;
    }



}
