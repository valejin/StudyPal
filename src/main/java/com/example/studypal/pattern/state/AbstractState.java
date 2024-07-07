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
    *      - una funzione di stampaBenvenuto (poi con ovverride ogni stato sceglie cosa fare)
    * */

    protected AbstractState(){
        //costruttore, utilizzabile solo dalle classi figlie
    }

    public List<AbstractState> statiRaggiungibili;


    /* per la transizione di stato--------------------------------------------------*/

    public void entry(StateMachineImpl contextSM){
        //metodo per far entrare la CLI (concrete state machine) nello stato corrente

    };
    public void exit(StateMachineImpl contextSM){};

    public void statiDisponibili(){
        /*restituisce gli stati che possiamo raggiungere tramite una transizione*/
    }

    public void goNext(StateMachineImpl SM, AbstractState newState){
        SM.transition(newState);
    }

    /*per l'azione---------------------------------------------------------------------*/

    public void action(StateMachineImpl SM){
        //rappresenta l'azione principale dello stato
    }

    public void mostraMenu(){}

    public void stampaBenvenuto(){}

}

