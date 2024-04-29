package com.example.studypal.controller.applicationController.studente;

import com.example.studypal.DAO.PrenotazioneDAO;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.model.PrenotazioneModel;

import java.util.List;

public class GestisciPrenotazioniStudenteController {

    private LoggedInUserBean user;
    private List<PrenotazioneModel> listaRichieste;
    private List<PrenotazioneBean> listaRichiesteBean;
    //todo: ma non è che sto user ce lo possiamo passare in modo più efficiente?

    /* todo: questo quasi sicuramente possiamo unirlo al controller applicativo del tutor e farne uno solo,
    *   modificando la query in modo da cercare una volta nella colonna emailTutor e un'altra nella colonna emailStudente*/

    public GestisciPrenotazioniStudenteController(LoggedInUserBean user){
        this.user = user;
    }

    public List<PrenotazioneBean> richiesteInviate (String email) {
        /* metodo che viene invocato dal controller grafico per ricevere la lista di richieste inviate dallo studente*/

        PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();

        listaRichieste = prenotazioneDAO.richiesteInviate(email);

        /* converto i model a bean per restituirli al controller grafico*/

        for (PrenotazioneModel richiesta: listaRichieste){
            PrenotazioneBean richiestaBean = new PrenotazioneBean(richiesta.getIdRichiesta(), richiesta.getEmailTutor(),
                    richiesta.getEmailStudente(), richiesta.getMateria(), richiesta.getModLezione(),
                    richiesta.getTariffa(), richiesta.getGiorno(), richiesta.getNote());

            listaRichiesteBean.add(richiestaBean);
        }

        //todo blocco try catch per gestione errori provenienti dalla ricerca (es. eccezione assenza di richieste attive)
        return listaRichiesteBean;

    }
}
