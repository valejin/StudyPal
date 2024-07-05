package com.example.studypal.pattern.state;

public interface StateMachine {

    /* interfaccia della macchina a stati, contiene la sola operazione goNext() che viene scatenata da una scelta dell'utente
    * (ovvero un cambiamento di pagina)
    * */

    public void goNext();

    public void setState(AbstractState s);

}
