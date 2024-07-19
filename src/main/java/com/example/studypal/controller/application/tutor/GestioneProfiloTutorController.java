package com.example.studypal.controller.application.tutor;

import com.example.studypal.dao.RipetizioneInfoDAO;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.model.RipetizioneInfoModel;

public class GestioneProfiloTutorController {

    /*
    controller applicativo che gestisce la logica del caso d'uso gestione profilo tutor
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

        ripetizioneInfoDAO.modificaProfiloTutor(ripetizioneInfoModel);
    }

    public RipetizioneInfoBean caricaInformazioniProfilo(String email){

        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();
        RipetizioneInfoModel ripetizioneInfoModel = ripetizioneInfoDAO.caricaInformazioniProfilo(email);

        return new RipetizioneInfoBean(ripetizioneInfoModel.getMateria(),
                ripetizioneInfoModel.getInPresenza(), ripetizioneInfoModel.getOnline(), ripetizioneInfoModel.getLuogo(),
                ripetizioneInfoModel.getGiorni(), ripetizioneInfoModel.getTariffa(), ripetizioneInfoModel.getEmail());

    }
}
