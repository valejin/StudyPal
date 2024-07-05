package com.example.studypal.pattern.state;

public class StateMachineImpl implements StateMachine {

    private AbstractState statoCorrente;

    private AbstractState statoPrecedente;

    /* macchina a stati concreta, rappresenta l'effettiva pagina in cui ci troviamo. Deve contenere tutte le informazioni
    *  e i metodi necessari al funzionamento di una qualsiasi pagina dell'app.
    *  Le caratteristiche comuni a tutte le pagine sono:
    *       - menu sidebar
    *       - LoggedInUserBean user (in particolare il ruolo)
    *       - metodo per tornare indietro
    * */

    @Override
    public void goNext( ) {
        //realizza l'operazione presente nell'interfaccia, permette il passaggio di stato
        //dobbiamo fare uno switch case sulla lista degli stati raggiungibili dello stato attuale

        /*nell'esempio del professore facciamo uno switch case sull'evento ricevuto, distinguendo i casi:
            - cambiamento di stato
            - esecuzione dell'azione specifica dello stato

        */


    }

    public void setState( AbstractState stato){
        this.statoCorrente = stato;
    }

}
