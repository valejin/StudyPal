package com.example.studypal.exceptions;

public class DBException extends Exception{
    public DBException() {
        super("Errore nella connessione al database.");
    }

}
