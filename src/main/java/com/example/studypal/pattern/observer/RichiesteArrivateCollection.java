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


    public static RichiesteArrivateCollection getInstance() { //Pattern Singleton
        if (richiesteCollection == null) {
            richiesteCollection = new RichiesteArrivateCollection();
        }
        return richiesteCollection;
    }

    private RichiesteArrivateCollection(){}

    /* metodi per la modifica della collezione di richieste*/

    public void aggiungiRichiesta(PrenotazioneModel richiesta){
        richiesteList.add(richiesta);
        notifyObservers();

        //debug
        Printer.print("RICHIESTE COLLECTION:");
        for (PrenotazioneModel richiestaModel: richiesteList){
            Printer.println("id: " + richiestaModel.getIdRichiesta());
        }
    }

    public void rimuoviRichiesta( Integer idRichiesta){

        for (PrenotazioneModel richiesta: richiesteList){
            if (richiesta.getIdRichiesta() == idRichiesta){
                richiesteList.remove(richiesta);
            }
        }

        notifyObservers();
    }

    public List<PrenotazioneModel> ottieniStato(){
        return richiesteList;
    }
}
