package com.example.studypal.controller.applicationController.studente;

import com.example.studypal.DAO.PrenotazioneDAO;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;

import java.util.ArrayList;
import java.util.List;

public class GestisciPrenotazioniStudenteController {

    private LoggedInUserBean user;

    /* todo: questo quasi sicuramente possiamo unirlo al controller applicativo del tutor e farne uno solo,
    *   modificando la query in modo da cercare una volta nella colonna emailTutor e un'altra nella colonna emailStudente*/

    public GestisciPrenotazioniStudenteController(LoggedInUserBean user){
        this.user = user;
    }


    public List<PrenotazioneBean> richiesteInviate (String email, Integer flag) {
        /* metodo che viene invocato dal controller grafico per ricevere la lista di richieste inviate dallo studente*/

        List<PrenotazioneBean> listaRichiesteBean = new ArrayList<>();

        try{
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            List<PrenotazioneModel> listaRichieste = prenotazioneDAO.richiesteInviate(email, flag);

            /* converto i model a bean per restituirli al controller grafico*/
            for (PrenotazioneModel richiesta: listaRichieste){
                PrenotazioneBean richiestaBean = new PrenotazioneBean(richiesta.getIdRichiesta(), richiesta.getEmailTutor(),
                        richiesta.getEmailStudente(), richiesta.getMateria(), richiesta.getModLezione(),
                        richiesta.getTariffa(), richiesta.getGiorno(), richiesta.getNote(), richiesta.getStato());
                listaRichiesteBean.add(richiestaBean);
            }

        } catch (NonProduceRisultatoException e){
            Printer.println("Non sono presenti richieste in attesa di conferma per l'utente " + user.getEmail());
        }
        return listaRichiesteBean;
    }

}
