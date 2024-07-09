package com.example.studypal.controller.applicationController.studente;

import com.example.studypal.DAO.RipetizioneInfoDAO;
import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.exceptions.MateriaNonTrovataException;
import com.example.studypal.model.BaseInfoModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;

import java.util.ArrayList;
import java.util.List;

public class CercaRipetizioneController extends HomeStudenteController {

    /*
    controller applicativo che gestisce il caso d'uso prenota ripetizione da parte dello studente
    */

    List<RipetizioneInfoModel> risultatiRicerca;
    List<RipetizioneInfoBean> risultatiRicercaBean = new ArrayList<>();


  /*------------------------------------------------------------------------------------------------------------------*/
    public List<RipetizioneInfoBean> prenotaRipetizioneMethod(BaseInfoBean baseInfoBean) {
        // prende un BaseinfoBean dal controller grafico, e carica i dati in model

        BaseInfoModel baseInfoModel = new BaseInfoModel();

        try {
        //popolo model con BEAN
        baseInfoModel.setMateria(baseInfoBean.getMateria());

        //passo model a DAO
        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();

        risultatiRicerca = ripetizioneInfoDAO.ricercaMateria(baseInfoModel);

        //carico nella lista di ripetizioneInfoBean i risultati della ricerca

        for ( RipetizioneInfoModel risultato: risultatiRicerca){
        RipetizioneInfoBean risultatoBean = new RipetizioneInfoBean(risultato.getNome(), risultato.getCognome(),risultato.getMateria(),
                risultato.getInPresenza(), risultato.getOnline(), risultato.getLuogo(),
                risultato.getGiorni(), risultato.getTariffa(), risultato.getEmail());

        risultatiRicercaBean.add(risultatoBean);
        }

        }catch(MateriaNonTrovataException e){
            Printer.errorPrint("controller Applicativo: materia non trovata");
        }

        return risultatiRicercaBean;
    }


/*--------------------------------------------------------------------------------------------------------------------*/
    public List<RipetizioneInfoBean> prenotaRipetizioneMethod(RipetizioneInfoBean ripetizioneInfoBean){

        /*
            gli viene restituita una lista di RipetizioneInfoModel dal DAO
            Usa quelle informazioni per popolare una lista di RipetizioneInfoBean da passare al controller grafico
        */

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

            //passo il model con i dati al DAO
            RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();

            risultatiRicerca = ripetizioneInfoDAO.ricercaFiltri(ripetizioneInfoModel);

            for ( RipetizioneInfoModel risultato: risultatiRicerca){
                RipetizioneInfoBean risultatoBean = new RipetizioneInfoBean(risultato.getNome(), risultato.getCognome(), risultato.getMateria(),
                        risultato.getInPresenza(), risultato.getOnline(), risultato.getLuogo(),
                        risultato.getGiorni(), risultato.getTariffa(), risultato.getEmail());

                risultatiRicercaBean.add(risultatoBean);
            }

        }catch(MateriaNonTrovataException e){
            Printer.println("Non produce risultato");
        }


        return risultatiRicercaBean;
    }

}
