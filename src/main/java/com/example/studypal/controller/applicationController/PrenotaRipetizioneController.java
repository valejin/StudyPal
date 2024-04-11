package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.RipetizioneInfoDAO;
import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.exceptions.MateriaNonTrovataException;
import com.example.studypal.model.BaseInfoModel;
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








}
