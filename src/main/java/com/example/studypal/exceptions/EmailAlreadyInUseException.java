package com.example.studypal.exceptions;

public class EmailAlreadyInUseException extends Exception {

    public EmailAlreadyInUseException() {
        super("Email already in use.");
    }


}
