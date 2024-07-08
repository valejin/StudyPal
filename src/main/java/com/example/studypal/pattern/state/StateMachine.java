package com.example.studypal.pattern.state;

public interface StateMachine {

    //equivale al context

    /* interfaccia della macchina a stati, contiene:
        -l' operazione goNext() che viene scatenata da una scelta dell'utente (ovvero un cambiamento di pagina)
        -
    * */

    public void goNext();

    public void goBack();

    public void transition(AbstractState nuovoStato);


    //public void start();

}
