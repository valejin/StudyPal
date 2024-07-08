package com.example.studypal.pattern.state;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.guiControllerCLI.LoginCLI;

import java.util.Stack;

public class StateMachineImpl implements StateMachine {

    /* Dall'esempio del professore deve contenere:
            -costruttore per settare gli attributi
            -implementazione del metodo goNext che fa partire la transizione/azione dello stato corrente
                    NOTA: possiamo fare che il metodo azione() dell'abstract state prenda in input la scelta dell'utente
                    letta dalla CLI! Poi all'interno del metodo dello stato controlliamo il valore ed eseguiamo con switch case l'azione corretta
            -funzioni di cambiamento di stato (entry/exit)
    * */

    public boolean inEsecuzione = true;

    private Stack<AbstractState> stateHistory;

    private AbstractState currentState;

    private LoggedInUserBean user;


    /* macchina a stati concreta, rappresenta l'effettiva "pagina" in cui ci troviamo. Deve contenere tutte le informazioni
    *  e i metodi necessari al funzionamento di una generica pagina dell'app.
    *  Le caratteristiche comuni a tutte le pagine sono:
    *       - menu sidebar
    *       - LoggedInUserBean user (in particolare il ruolo!)
    *       - metodo per tornare indietro
    * */

    public StateMachineImpl(){
        this.stateHistory = new Stack<>();
        this.currentState = new InitialState(); //oppure start()
    }

    @Override
    public void start(){
        /* metodo che prepara lo stato iniziale*/

        currentState = new InitialState();
        goNext(); //lo stato iniziale è l'unico che viene fatto partire "manualmente" dalla state machine
        //System.out.println("SM DONE START");
    }

    @Override
    public void goNext( ) {

        if (currentState != null){
            this.currentState.action(this);
        }
    }

    @Override
    public void goBack(){
        /*torna allo stato precedente, useremo la lista degli stati passati per tornare indietro*/
        if (!stateHistory.isEmpty()){
            this.currentState = stateHistory.pop();
            goNext();
        }
    }

    @Override
    public void transition(AbstractState nextState){
        this.currentState.exit(this);
        if(currentState != null) {
               stateHistory.push(currentState);
        }
        this.currentState = nextState;
        this.currentState.entry(this);
        goNext();
    }


    /* --------------ALTRE FUNZIONI DELLA CLI --------------------------*/

    public LoggedInUserBean getUser(){
        return user;
    }

    public void setUser(LoggedInUserBean user){
        this.user = user;
    }

    public AbstractState getState(){
        return currentState;
    }

}
