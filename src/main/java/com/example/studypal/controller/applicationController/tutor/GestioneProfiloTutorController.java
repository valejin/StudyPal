package com.example.studypal.controller.applicationController.tutor;

import com.example.studypal.DAO.RipetizioneInfoDAO;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.model.RipetizioneInfoModel;

import java.sql.SQLException;

public class GestioneProfiloTutorController {

    /*
    controller applicativo che gestisce il caso d'uso gestione profilo tutor
     */

    public void gestioneProfiloMethod(RipetizioneInfoBean ripetizioneInfoBean) {
       // prende un RipetizioneInfoBean dal controller grafico, carica i dati in un model e lo manda al dao per fare le query necessarie

        RipetizioneInfoModel ripetizioneInfoModel = new RipetizioneInfoModel();

        //popoliamo model
        ripetizioneInfoModel.setLuogo(ripetizioneInfoBean.getLuogo());
        ripetizioneInfoModel.setMateria(ripetizioneInfoBean.getMateria());
        ripetizioneInfoModel.setGiorni(ripetizioneInfoBean.getGiorni());
        ripetizioneInfoModel.setInPresenza(ripetizioneInfoBean.getInPresenza());
        ripetizioneInfoModel.setOnline(ripetizioneInfoBean.getOnline());
        ripetizioneInfoModel.setTariffa(ripetizioneInfoBean.getTariffa());
        ripetizioneInfoModel.setEmail(ripetizioneInfoBean.getEmail());

        //passiamo model a DAO
        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();

        //mettere try catch e gestire eccezioni
        ripetizioneInfoDAO.modificaProfiloTutor(ripetizioneInfoModel);
    }

    public RipetizioneInfoModel caricaInformazioniProfilo(String email){

        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();
        return ripetizioneInfoDAO.caricaInformazioniProfilo(email);

    }
}
