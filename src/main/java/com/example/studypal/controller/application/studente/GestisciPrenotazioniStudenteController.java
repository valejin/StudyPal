package com.example.studypal.controller.application.studente;

import com.example.studypal.dao.PrenotazioneDAO;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.exceptions.DBException;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.observer.RichiesteArrivateCollection;

import java.util.ArrayList;
import java.util.List;

public class GestisciPrenotazioniStudenteController {

    private LoggedInUserBean user;

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
                PrenotazioneBean richiestaBean = getPrenotazioneBean(richiesta);

                listaRichiesteBean.add(richiestaBean);
            }

        } catch (NonProduceRisultatoException e){
            Printer.println("Non sono presenti richieste in attesa di conferma per l'utente " + user.getEmail());
        } catch (DBException e){
            Printer.println("Errore nel database.");
        }
        return listaRichiesteBean;
    }

    private static PrenotazioneBean getPrenotazioneBean(PrenotazioneModel richiesta) {
        LoggedInUserBean tutorInfo = new LoggedInUserBean(richiesta.getEmailTutor(), richiesta.getNome(), richiesta.getCognome());

        List<Integer> valori = new ArrayList<>();
        valori.add(richiesta.getModLezione());
        valori.add(richiesta.getTariffa());
        valori.add(richiesta.getStato());

        return new PrenotazioneBean(richiesta.getIdRichiesta(), tutorInfo,
                richiesta.getEmailStudente(), richiesta.getMateria(), richiesta.getGiorno(), richiesta.getNote(), valori);

    }


    /*----------------------------------------------------------------------------------------------------------------*/

    public void cancellaRichiesta(Integer idRichiesta){

        /* Istanzio il DAO e gli passo l'id della richiesta da eliminare.
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
