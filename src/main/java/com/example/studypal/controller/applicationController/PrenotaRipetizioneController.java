package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.RipetizioneInfoDAO;
import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.exceptions.MateriaNonTrovataException;
import com.example.studypal.model.BaseInfoModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;

public class PrenotaRipetizioneController extends HomeStudenteController{

    /*
    controller applicativo che gestisce il caso d'uso prenota ripetizione da parte dello studente
    */
    String materie;

    public void prenotaRipetizioneMethod(BaseInfoBean baseInfoBean) {
        // prende un BaseinfoBean dal controller grafico, e carica i dati in model

        BaseInfoModel baseInfoModel = new BaseInfoModel();

        try {
        //popolo model con BEAN
        baseInfoModel.setMateria(baseInfoBean.getMateria());

        //passo model a DAO
        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();

        ripetizioneInfoDAO.ricercaMateria(baseInfoModel);

        }catch(MateriaNonTrovataException e){
            Printer.errorPrint("controller Applicativo: materia non trovata");

        }

    }



    public void prenotaRipetizioneMethod(RipetizioneInfoBean ripetizioneInfoBean){
        //prendo RipetizioneInfoBean dal controller Gui che contiene i dati per la ricerca con filtri
        //e poi passo questi dati al model

        //istanzio un model
        RipetizioneInfoModel ripetizioneInfoModel = new RipetizioneInfoModel();

        try{
            //adesso popolo il model con info di bean
            ripetizioneInfoModel.setEmail(ripetizioneInfoBean.getEmail());
            ripetizioneInfoModel.setMateria(ripetizioneInfoBean.getMateria());
            ripetizioneInfoModel.setInPresenza(ripetizioneInfoBean.getInPresenza());
            ripetizioneInfoModel.setOnline(ripetizioneInfoBean.getOnline());
            ripetizioneInfoModel.setLuogo(ripetizioneInfoBean.getLuogo());
            ripetizioneInfoModel.setGiorni(ripetizioneInfoBean.getGiorni());
            ripetizioneInfoModel.setTariffa(ripetizioneInfoBean.getTariffa());

            //System.out.println("Clt prenotaRipetizione 1");

            //passo il model con i dati al DAO
            RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();

            //System.out.println("Clt prenotaRipetizione 2");

            ripetizioneInfoDAO.ricercaFiltri(ripetizioneInfoModel);

            //System.out.println("Clt prenotaRipetizione 3");

        }catch(MateriaNonTrovataException e){
            Printer.println("Non produce risultato");

        }



    }




}
