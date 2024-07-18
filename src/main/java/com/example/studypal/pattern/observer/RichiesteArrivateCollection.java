package com.example.studypal.pattern.observer;

import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;
import java.util.ArrayList;
import java.util.List;
public class RichiesteArrivateCollection extends Subject {

    /*
     *   Classe che rappresenta la lista di richieste arrivate, è il ConcreteSubject del pattern Observer
     *   Quando viene modificata la collezione, gli osservatori vengono notificati tramite update().
     *   Permette di aggiornare immediatamente il contenuto della tabella delle richieste arrivate a un certo tutor,
     *   in quanto il controller grafico RichiesteTutorGui è il nostro ConcreteObserver.
     *
     *   Note:
     *       -la classe è un singleton (istanza unica) in modo tale da avere il corretto funzionamento del pattern.
     *       -richiesteList rappresenta lo stato del ConcreteSubject
     */

    private static RichiesteArrivateCollection richiesteCollection = null;

    private List<PrenotazioneModel> richiesteList = new ArrayList<>();

    private RichiesteArrivateCollection(){}


    public static RichiesteArrivateCollection getInstance() { //Pattern Singleton
        if (richiesteCollection == null) {
            richiesteCollection = new RichiesteArrivateCollection();
        }
        return richiesteCollection;
    }


    /* metodi per la modifica della collezione di richieste*/

    public void aggiungiRichiesta(PrenotazioneModel richiesta){
        richiesteList.add(richiesta);
        notifyObservers();

        //debug
        Printer.println("RICHIESTE COLLECTION:");
        for (PrenotazioneModel richiestaModel: richiesteList){
            Printer.println("id: " + richiestaModel.getIdRichiesta());
        }
    }

    public void rimuoviRichiesta( Integer idRichiesta){

        richiesteList.removeIf(richiesta -> richiesta.getIdRichiesta() == idRichiesta);

        notifyObservers();
    }

    public List<PrenotazioneModel> ottieniStato(){
        return richiesteList;
    }

    public void nuovaLista(List<PrenotazioneModel> nuoveRichieste){
        /*questo metodo viene invocato dal controller applicativo della gestione delle prenotazioni ogni volta
         che viene caricata la lista delle richieste arrivate a un tutor dal DB, in modo tale da permettere il corretto
         aggiornamento della lista tramite observer*/

        richiesteList = nuoveRichieste;
    }
}