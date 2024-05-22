package com.example.studypal.Query;

public class Query {

    public Query(){}


/*--------------------Queries Login-----------------------------*/

    //Query per verificare se l'email inserita è già stata registrata
    public static final String RICERCA_EMAIL = "SELECT * FROM utente WHERE email = '%s' ";

    //Query per fare login con i credenziali
    public static final String VERIFICA_USER = "SELECT * FROM utente WHERE email ='%s' AND password='%s' ";



    /*Queries Registrazione*/

    /*Queries Ricerca materia*/
    public static final String RICERCA_MATERIA = "SELECT * FROM tutor WHERE materia= '%s' ";

    /*Query*/


}
