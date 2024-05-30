package com.example.studypal.exceptions;

public class EmailNonValidaException extends Exception{
    public EmailNonValidaException() {
        super("L'email non è valida.");
    }
}
