package com.example.studypal.controller.applicationController.studente;

import com.example.studypal.DAO.PrenotazioneDAO;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.observer.RichiesteArrivateCollection;

import java.util.ArrayList;
import java.util.List;

public class GestisciPrenotazioniStudenteController {

    private LoggedInUserBean user;

    /* todo: questo quasi sicuramente possiamo unirlo al controller applicativo del tutor e farne uno solo,
    *   modificando la query in modo da cercare una volta nella colonna emailTutor e un'altra nella colonna emailStudente*/

    public GestisciPrenotazioniStudenteController(LoggedInUserBean user){
        this.user = user;
    }

    public GestisciPrenotazioniStudenteController(){}

    public List<PrenotazioneBean> richiesteInviate (String email, Integer flag) {
        /* metodo che viene invocato dal controller grafico per ricevere la lista di richieste inviate/rifiutate/accettate dello studente*/

        List<PrenotazioneBean> listaRichiesteBean = new ArrayList<>();

        try{
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            List<PrenotazioneModel> listaRichieste = prenotazioneDAO.richiesteInviate(email, flag);

            /* converto i model a bean per restituirli al controller grafico*/
            for (PrenotazioneModel richiesta: listaRichieste){
                PrenotazioneBean richiestaBean = new PrenotazioneBean(richiesta.getIdRichiesta(), richiesta.getNome(), richiesta.getCognome(),  richiesta.getEmailTutor(),
                        richiesta.getEmailStudente(), richiesta.getMateria(), richiesta.getModLezione(),
                        richiesta.getTariffa(), richiesta.getGiorno(), richiesta.getNote(), richiesta.getStato());
                listaRichiesteBean.add(richiestaBean);
            }

        } catch (NonProduceRisultatoException e){
            Printer.println("Non sono presenti richieste in attesa di conferma per l'utente " + user.getEmail());
        }
        return listaRichiesteBean;
    }


    /*----------------------------------------------------------------------------------------------------------------*/

    public void cancellaRichiesta(Integer idRichiesta){

        /* istanzio il DAO e gli passo l'id della richiesta da eliminare.
        Nota: non c'è bisogno di try catch, la richiesta è sicuramente presente nel DB dato che l'abbiamo caricata noi in precedenza*/

        PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
        prenotazioneDAO.cancellaRichiesta(idRichiesta);

        Printer.println("DEBUG ID: " + idRichiesta);

        Printer.println("DEBUG RICHIESTE COLLECTION:");
        for (PrenotazioneModel richiestaModel: RichiesteArrivateCollection.getInstance().ottieniStato()){
            Printer.println("id: " + richiestaModel.getIdRichiesta());
        }


        RichiesteArrivateCollection.getInstance().rimuoviRichiesta(idRichiesta);

        //debug
        Printer.print("DEBUG RICHIESTE COLLECTION:");
        for (PrenotazioneModel richiestaModel: RichiesteArrivateCollection.getInstance().ottieniStato()){
            Printer.println("id: " + richiestaModel.getIdRichiesta());
        }

    }


    /*----------------------------------------------------------------------------------------------------------------*/

    public void recensioneMethod(Integer idRichiesta, Integer recensione){

        PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
        prenotazioneDAO.recensioneMethod(idRichiesta, recensione);
    }

}
