package com.example.studypal.query;

public class Query {

    public Query(){}


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

    /*Query*/


}
