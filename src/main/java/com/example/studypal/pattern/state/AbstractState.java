package com.example.studypal.pattern.state;

public abstract class AbstractState {

    /* classe astratta che viene estesa dalle sottoclassi ConcreteStates che sono i controller grafici della CLI
    * Contiene:
    *      - un'operazione astratta per il passaggio di stato e degli attributi che vengono ereditati dalle classi che
    *       la concretizzano.
    *      - un'operazione mostraMenu che viene implementata in maniera diveresa a seconda della pagina in cui ci troviamo
    *      - un attributo che identifica la sessione (utente loggato)
    * */

    public void goNext(){
    }

    public void mostraMenu(){}

}

