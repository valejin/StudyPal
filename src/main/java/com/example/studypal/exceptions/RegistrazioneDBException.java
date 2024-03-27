package com.example.studypal.exceptions;

public class RegistrazioneDBException extends Exception {

    private final int code;

    public RegistrazioneDBException(int code) { this.code = code; }

    public int getCode() { return this.code; }
}
