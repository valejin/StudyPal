package com.example.studypal.pattern.state;

public interface StateMachine {

    /* interfaccia della macchina a stati, contiene:
        -l' operazione goNext() che viene scatenata da una scelta dell'utente (ovvero un cambiamento di pagina)
        -
    * */

    public void goNext();

    public void goBack();

    public void transition(AbstractState nuovoStato);

    public void action(Integer option);

    public void setState(AbstractState s);

}
