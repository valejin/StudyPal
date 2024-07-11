package com.example.studypal.pattern.state;

import com.example.studypal.bean.LoggedInUserBean;

import java.util.Stack;

public class StateMachineImpl implements StateMachine {

    /* Dall'esempio del professore deve contenere:
            -costruttore per settare gli attributi
            -implementazione del metodo goNext che fa partire la transizione/azione dello stato corrente
                    NOTA: possiamo fare che il metodo azione() dell'abstract state prenda in input la scelta dell'utente
                    letta dalla CLI! Poi all'interno del metodo dello stato controlliamo il valore ed eseguiamo con switch case l'azione corretta
            -funzioni di cambiamento di stato (entry/exit)
    * */


    private Stack<AbstractState> stateHistory;

    private AbstractState currentState;

    private LoggedInUserBean user;


    public StateMachineImpl(){
        this.stateHistory = new Stack<>();
        this.currentState = new InitialState(); //oppure start()
    }

    @Override
    public void start(){
        /* metodo che prepara lo stato iniziale*/
        currentState = new InitialState();
        goNext(); //lo stato iniziale è l'unico che viene fatto partire "manualmente" dalla state machine
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
            this.currentState.exit(this);
            this.currentState = stateHistory.pop();
            this.currentState.entry(this);
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

    public void setState(){
        this.currentState = null;
    }

}
