package com.example.studypal.other;

import java.sql.SQLOutput;


//classe che si occupa la stampa di messaggi
public class Printer {

    private Printer(){}

    public static void print(String message){
        System.out.print(message);
    }

    //stampa messaggio di errore
    public static void errorPrint(String message) {
        System.out.print(message);
    }
}
