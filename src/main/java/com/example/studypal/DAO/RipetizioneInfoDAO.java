package com.example.studypal.DAO;

public class RipetizioneInfoDAO {

    /*
    DAO che gestisce le interazioni con il db relative alle informazioni di una ripetizione.
    Contiene:
        - metodo che effettua la ricerca di una ripetizione (PrenotaRipetizione)
        - metodo che effettua l'aggiornamento delle informazioni di un profilo tutor (GestioneProfiloTutor)
    In entrambi i casi d'uso viene inviato al DAO un model RipetizioneInfoModel contenente le informazioni necessarie alle query

     */

    public void ricercaInfo() {
        /*
        metodo per la ricerca di ripetizioni, restituisce tutti i tutor e rispettive informazioni di ripetizioni disponibiili
        applica i parametri di ricerca ricevuti tramite istanza di RipetizioneInfoModel

        La query cerca nella tabella Tutor
         */
    }

    public void prenotaRipetizione() {
        /*
        La query inserisce tuple nella tabella RipetizioniAttive quando viene effettuata una prenotazione di ripetizione
         */
    }

    public void modificaProfiloTutor(){
        /*
        metodo chiamato dal controller applicativo GestioneProfiloTutorController per modificare i dati di un utente tutor nel database

        Modifica le tuple nella tabella Tutor
         */

    }
}
