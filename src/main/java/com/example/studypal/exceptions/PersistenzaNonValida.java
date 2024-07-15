package com.example.studypal.exceptions;

public class PersistenzaNonValida extends Exception{
    public PersistenzaNonValida() {
        super("Tipo di persistenza specificato invalido.");
    }

}
