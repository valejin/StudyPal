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
            -funzione di cambiamento di stato (entry/exit)
    * */

    private Stack<AbstractState> stateHistory;
    private AbstractState currentState;
    private LoggedInUserBean user;


    public StateMachineImpl() {
        this.stateHistory = new Stack<>();
        this.currentState = new LoginCLI(); // Stato iniziale
    }

    @Override
    public void goNext( ) {

        if (currentState != null) {
            currentState.action(this); // Esegue la logica dello stato corrente e va al prossimo stato
        }

    }

    @Override
    public void goBack() {
        if (!stateHistory.isEmpty()) {
            this.currentState = stateHistory.pop();
            goNext(); // Esegue l'azione dello stato precedente
        }
    }




    @Override
    public void transition(AbstractState nextState) {

        this.currentState.exit(this);

        if (currentState != null) {
            stateHistory.push(currentState);
        }
        this.currentState = nextState;
        this.currentState.entry(this);

    }




    /* --------------ALTRE FUNZIONI DELLA CLI --------------------------*/


    public LoggedInUserBean getUser() {
        return user;
    }


    public void setUser(LoggedInUserBean user) {
        this.user = user;
    }

    public AbstractState getState() {
        return currentState;
    }

    public void setState(){
        this.currentState = null;
    }

}
