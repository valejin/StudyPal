package com.example.studypal.pattern.state;

public class StateMachineImpl implements StateMachine {

    private AbstractState statoCorrente;

    /* macchina a stati concreta, rappresenta l'effettiva pagina in cui ci troviamo. Deve contenere tutte le informazioni
    *  e i metodi necessari al funzionamento di una qualsiasi pagina dell'app.
    *  Le caratteristiche comuni a tutte le pagine sono:
    *       - menu sidebar
    *       - LoggedInUserBean user (in particolare il ruolo)
    *       - metodo per tornare indietro
    * */

    @Override
    public void goNext() {
        //realizza l'operazione presente nell'interfaccia
    }
}
