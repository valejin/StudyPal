package com.example.studypal.other;

import java.sql.SQLOutput;


//classe che si occupa la stampa di messaggi
public class Printer {

    private Printer(){}

    //stampa
    public static void print(String message){
        System.out.print(message);
    }

    //stampa e va a capo
    public static void println(String message){
        System.out.println(message);
    }


    //stampa messaggio di errore
    public static void errorPrint(String message) {
        System.out.println(message);
    }
}
