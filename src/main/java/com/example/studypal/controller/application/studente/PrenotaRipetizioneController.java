package com.example.studypal.controller.application.studente;

import com.example.studypal.DAO.PrenotazioneDAO;
import com.example.studypal.DAO.RipetizioneInfoDAO;
import com.example.studypal.bean.*;
import com.example.studypal.model.BaseInfoModel;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.observer.RichiesteArrivateCollection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrenotaRipetizioneController extends HomeStudenteController {

    /*
    controller applicativo che gestisce il caso d'uso prenota ripetizione da parte dello studente
    */

    private List<RipetizioneInfoModel> risultatiRicerca;
    private final List<RipetizioneInfoBean> risultatiRicercaBean = new ArrayList<>();


  /*---------------------------------------------- PER MATERIA -------------------------------------------------------*/
    public List<RipetizioneInfoBean> ricercaMethod(BaseInfoBean baseInfoBean) {
        // prende un BaseinfoBean dal controller grafico, e carica i dati in model

        //metodo per la ricerca

        BaseInfoModel baseInfoModel = new BaseInfoModel();

        //popolo model con BEAN
        baseInfoModel.setMateria(baseInfoBean.getMateria());

        //passo model a DAO
        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();

        risultatiRicerca = ripetizioneInfoDAO.ricercaMateria(baseInfoModel);

        //carico nella lista di ripetizioneInfoBean i risultati della ricerca

        for ( RipetizioneInfoModel risultato: risultatiRicerca){
            LoggedInUserBean user = new LoggedInUserBean(risultato.getEmail(), risultato.getNome(), risultato.getCognome());
            RipetizioneInfoBean risultatoBean = new RipetizioneInfoBean(user, risultato.getMateria(),
                    risultato.getInPresenza(), risultato.getOnline(), risultato.getLuogo(), risultato.getGiorni(),
                    risultato.getTariffa());

        risultatiRicercaBean.add(risultatoBean);
        }
        return risultatiRicercaBean;
    }


/*------------------------------------------------- CON FILTRI -------------------------------------------------------*/
    public List<RipetizioneInfoBean> ricercaMethod(RipetizioneInfoBean ripetizioneInfoBean){

        /*
            gli viene restituita una lista di RipetizioneInfoModel dal DAO
            Usa quelle informazioni per popolare una lista di RipetizioneInfoBean da passare al controller grafico
        */

        //istanzio un model
        RipetizioneInfoModel ripetizioneInfoModel = new RipetizioneInfoModel();

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
            LoggedInUserBean user = new LoggedInUserBean(risultato.getEmail(), risultato.getNome(), risultato.getCognome());

            RipetizioneInfoBean risultatoBean = new RipetizioneInfoBean(user, risultato.getMateria(),
                    risultato.getInPresenza(), risultato.getOnline(), risultato.getLuogo(), risultato.getGiorni(),
                    risultato.getTariffa());

            risultatiRicercaBean.add(risultatoBean);
        }


        return risultatiRicercaBean;
    }


    /*------------------------------------------------PRENOTAZIONE----------------------------------------------------*/
    public void prenota(PrenotazioneBean prenotazioneBean) {

        /*
        prende le informazioni di prenotazione dal bean passatogli dal controller grafico PrenotaRipetizioneGUI
        le mette in un prenotazioneModel per passare al DAO
        istanzia e chiama il PrenotazioneDAO per caricare la richiesta nel database
         */

        /*istanzio model e ci inserisco i dati della prenotazione------------------*/

        LoggedInUserBean userTutor = new LoggedInUserBean( prenotazioneBean.getNome(), prenotazioneBean.getCognome(), prenotazioneBean.getEmailTutor());

        List<Integer> valori = new ArrayList<>();
        valori.add(prenotazioneBean.getModLezione());
        valori.add(prenotazioneBean.getTariffa());
        valori.add(prenotazioneBean.getStato());


        PrenotazioneModel prenotazioneModel = new PrenotazioneModel(prenotazioneBean.getIdRichiesta(), userTutor,
                prenotazioneBean.getEmailStudente(), prenotazioneBean.getMateria(), prenotazioneBean.getGiorno(),
                prenotazioneBean.getNote(), valori);


        /*istanzio DAO e gli passo il model----------------------------------------*/
        try{
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            prenotazioneDAO.prenota(prenotazioneModel);

            /*todo  IL PROBLEMA è CHE AGGIUNGO ALLA COLLECTION PRIMA CHE SIA ARRIVATA AL DB QUINDI IDRICHIESTA NON ESISTE ANCORA! QUINDI BISOGNA PRIMA SALVARLE NEL DATABASE E POI CARICARLE NELLA COLLECTION*/
            RichiesteArrivateCollection.getInstance().aggiungiRichiesta(prenotazioneModel); //pattern Observer

        } catch (SQLException e){
            Printer.errorPrint("Errore nel DataBase");
        }
    }


}
