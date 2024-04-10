package com.example.studypal.Query;

public class Query {

    public Query(){}


    /*Queries Login*/
    public static final String VERIFICA_USER ="SELECT * FROM utente where email='%s' AND password='%s' ";

    /*Queries Registrazione*/

    /*Queries Ricerca materia*/
    public static final String RICERCA_MATERIA = "SELECT * FROM tutor where materia= '%s' ";


}
