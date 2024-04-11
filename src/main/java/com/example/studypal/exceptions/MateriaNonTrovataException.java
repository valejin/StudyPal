package com.example.studypal.exceptions;

public class MateriaNonTrovataException extends Exception{
    public MateriaNonTrovataException() {
        super("Materia non trovata.");
    }
}
