package com.example.studypal.query;

public class Query {

    public Query(){
        /* vuoto*/
    }


    /*--------------------Queries Login-----------------------------*/

    //Query per verificare se l'email inserita è già stata registrata (nel momento di LOGIN)
    public static final String RICERCA_EMAIL = "SELECT * FROM utente WHERE email = '%s' ";

    //Query per fare login con i credenziali
    public static final String VERIFICA_USER = "SELECT * FROM utente WHERE email ='%s' AND password='%s' ";



    /*--------------------Queries Registrazione-------------------*/
    public static final String REGISTRAZIONE = "INSERT INTO utente (email, nome, cognome, password, isTutor) VALUES ('%s', '%s', '%s', '%s', %b)";

    public static final String REGISTRAZ_TUTOR = "INSERT INTO tutor (email, tariffa, luogo, materie, inPresenza, webCam, giorni, nome, cognome) VALUES ('%s', NULL, NULL, NULL, NULL, NULL, NULL, '%s', '%s')";


    /*Queries Ricerca materia*/
    public static final String RICERCA_MATERIA = "SELECT * FROM tutor WHERE materia= '%s'";


    /*Query per inserire la richiesta dello studente in DB */
    public static final String PRENOTA = "INSERT INTO richieste (emailTutor, emailStudente, materia, modLezione, tariffa, giorni, note, nomeTutor, cognomeTutor) VALUES ('%s', '%s', '%s', '%d', '%d', '%s', '%s', '%s', '%s')";


    /*Query per Gestisci Prenotazioni (lato TUTOR) per prendere le richieste da DB*/
    public static final String RICHIESTE_IN_ATTESA = "SELECT * FROM richieste WHERE emailTutor = '%s' AND stato = 0";
    public static final String PRENOTAZIONI_ATTIVE = "SELECT * FROM richieste WHERE emailTutor = '%s' AND stato = 1";
    public static final String RICHIESTE_RIFIUTATE = "SELECT * FROM richieste WHERE emailTutor = '%s' AND stato = 2";

    /* Query per Gestisci Prenotazioni (lato STUDENTE) per prendere le richieste dal DB*/
    public static final String RICHIESTE_INVIATE = "SELECT * FROM richieste WHERE emailStudente = '%s' AND stato = 0";
    public static final String PRENOTAZIONI_ATTIVE_S = "SELECT * FROM richieste WHERE emailStudente = '%s' AND stato = 1";
    public static final String RICHIESTE_RIFIUTATE_S = "SELECT * FROM richieste WHERE emailStudente = '%s' AND stato = 2";

    public static final String CONFERMA_RICHIESTA = "UPDATE richieste SET stato = 0 WHERE idrichieste = '%s'";
    public static final String RIFIUTA_RICHIESTA = "UPDATE richieste SET stato = 1 WHERE idrichieste = '%s'";

    public static final String CANCELLA_RICHIESTA = "DELETE FROM richieste WHERE idrichieste = '%s'";

    public static final String RECENSIONE = "UPDATE richieste SET recensione = '%d' WHERE idrichieste = '%d'";
}