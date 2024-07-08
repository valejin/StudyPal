package com.example.studypal.pattern.state;

public interface StateMachine {

    /* interfaccia della macchina a stati */


    public void goNext();

    public void goBack();

    public void transition(AbstractState nuovoStato);


    public void setState(AbstractState s);

    void start();
}
