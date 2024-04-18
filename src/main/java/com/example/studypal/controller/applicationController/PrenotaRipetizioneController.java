package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.RipetizioneInfoDAO;
import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.exceptions.MateriaNonTrovataException;
import com.example.studypal.model.BaseInfoModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;

import java.util.ArrayList;
import java.util.List;

public class PrenotaRipetizioneController extends HomeStudenteController{

    /*
    controller applicativo che gestisce il caso d'uso prenota ripetizione da parte dello studente
    */

    String materie;
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

        }catch(MateriaNonTrovataException e){
            Printer.errorPrint("controller Applicativo: materia non trovata");
        }

        //carico nella lista di ripetizioneInfoBean i risultati della ricerca

        for ( RipetizioneInfoModel risultato: risultatiRicerca){
            RipetizioneInfoBean risultatoBean = new RipetizioneInfoBean(risultato.getNome(), risultato.getCognome(),risultato.getMateria(),
                    risultato.getInPresenza(), risultato.getOnline(), risultato.getLuogo(),
                    risultato.getGiorni(), risultato.getTariffa(), risultato.getEmail());

            risultatiRicercaBean.add(risultatoBean);

        }

        /*DEBUG
        System.out.println("Controller applicativo ha ricevuto questi risultati:");
        for (RipetizioneInfoBean risultatoBean: risultatiRicercaBean) {
            System.out.println("    nome: " + risultatoBean.getNome());
            System.out.println("    cognome: " + risultatoBean.getCognome());
            System.out.println("    materie: " + risultatoBean.getMaterie());
            System.out.println("    lezioni in presenza: " + risultatoBean.getInPresenza());
            System.out.println("    lezioni online: " + risultatoBean.getOnline());
            System.out.println("    luogo: " + risultatoBean.getLuogo());
            System.out.println("    giorni disponibili: " + risultatoBean.getGiorni());
            System.out.println("    tariffa: " + risultatoBean.getTariffa() + "€/h");
            System.out.println("    email: " + risultatoBean.getEmail());
            System.out.println("------------------------------------------------");
        }

         */

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

                System.out.println("MATERIE CON CUI CREO IL RIPETIZIONEINFOBEAN, VENGONO DAL MODEL " + risultato.getMateria());

                risultatiRicercaBean.add(risultatoBean);
            }

        }catch(MateriaNonTrovataException e){
            Printer.println("Non produce risultato");
        }


        /*DEBUG
        System.out.println("Controller applicativo ha ricevuto questi risultati:");
        for (RipetizioneInfoBean risultatoBean: risultatiRicercaBean) {
            System.out.println("    nome: " + risultatoBean.getNome());
            System.out.println("    cognome: " + risultatoBean.getCognome());
            System.out.println("    materie: " + risultatoBean.getMaterie());
            System.out.println("    lezioni in presenza: " + risultatoBean.getInPresenza());
            System.out.println("    lezioni online: " + risultatoBean.getOnline());
            System.out.println("    luogo: " + risultatoBean.getLuogo());
            System.out.println("    giorni disponibili: " + risultatoBean.getGiorni());
            System.out.println("    tariffa: " + risultatoBean.getTariffa() + "€/h");
            System.out.println("    email: " + risultatoBean.getEmail());
            System.out.println("------------------------------------------------");
        }

         */

        return risultatiRicercaBean;
    }

}
