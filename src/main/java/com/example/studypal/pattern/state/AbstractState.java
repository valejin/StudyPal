package com.example.studypal.pattern.state;

import java.util.List;

public abstract class AbstractState {

    /* classe astratta che viene estesa dalle sottoclassi ConcreteStates che sono i controller grafici della CLI
    * Contiene:
    *      - un'operazione astratta per il passaggio di stato e degli attributi che vengono ereditati dalle classi che
    *       la concretizzano.
    *      - un'operazione mostraMenu che viene implementata in maniera diversa a seconda della pagina in cui ci troviamo
    *      - un attributo che identifica la sessione (utente loggato)
    *
    *      - una lista degli stati verso cui ci possiamo muovere a partire da quello corrente
    *      - una funzione di stampaBenvenuto nello stato
    * */

    public List<AbstractState> statiRaggiungibili;

    public void entry(StateMachineImpl contextSM){
        //metodo per far entrare la CLI (concrete state machine) nello stato corrente

    };
    public void exit(StateMachineImpl contextSM){};


    public void mostraMenu(){}

    public void stampaBenvenuto(){}


}

