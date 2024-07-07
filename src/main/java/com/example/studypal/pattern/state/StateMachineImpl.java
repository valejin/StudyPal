package com.example.studypal.pattern.state;

public class StateMachineImpl implements StateMachine {

    /* Dall'esempio del professore deve contenere:
            -costruttore per settare gli attributi
            -implementazione del metodo goNext che fa partire la transizione/azione dello stato corrente
                    NOTA: possiamo fare che il metodo azione() dell'abstract state prenda in input la scelta dell'utente
                    letta dalla CLI! Poi all'interno del metodo dello stato controlliamo il valore ed eseguiamo con switch case l'azione corretta
            -funzione di cambiamento di stato (entry/exit)
    * */

    private AbstractState statoCorrente;

    private AbstractState statoPrecedente;

    /* macchina a stati concreta, rappresenta l'effettiva "pagina" in cui ci troviamo. Deve contenere tutte le informazioni
    *  e i metodi necessari al funzionamento di una generica pagina dell'app.
    *  Le caratteristiche comuni a tutte le pagine sono:
    *       - menu sidebar
    *       - LoggedInUserBean user (in particolare il ruolo!)
    *       - metodo per tornare indietro
    * */

    @Override
    public void goNext( ) {
        /*realizza l'operazione presente nell'interfaccia, permette il passaggio di stato
          dobbiamo fare uno switch case sulla lista degli stati raggiungibili dello stato attuale?
          Oppure similmente all'esempio del professore dobbiamo lanciare degli eventi dal client (il nostro main che lancia la CLI)
          e catturarli qui dentro distinguendo i casi
            - cambiamento di stato
            - esecuzione dell'azione specifica dello stato
        */

        this.statoCorrente.action(this, 0);

    }

    @Override
    public void goBack(){
        /*torna allo stato precedente*/
    }
    @Override
    public void transition(AbstractState nuovoStato){
        this.statoCorrente.exit(this);
        this.statoCorrente = nuovoStato;
        this.statoCorrente.entry(this);
    }

    @Override
    public void action(Integer option){
        this.statoCorrente.action(this, option);
    }

    public void setState(AbstractState stato){
        //questa da cancellare, era un esempio
        this.statoCorrente = stato;
    }

    /* --------------ALTRE FUNZIONI DELLA CLI --------------------------*/

}
