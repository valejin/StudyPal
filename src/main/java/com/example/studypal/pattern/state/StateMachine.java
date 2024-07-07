package com.example.studypal.pattern.state;

public interface StateMachine {

    /* interfaccia della macchina a stati */



    void goNext();

    void goBack();

    void transition(AbstractState nuovoStato);

    void action();

    void start();


}
