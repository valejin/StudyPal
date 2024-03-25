package com.example.studypal.exceptions;

public class LoginDBException extends Exception{

    private static final long serialVersioneUID = 1L;
    private final int code;

    public LoginDBException(int code) { this.code = code; }

    public int getCode() { return this.code; }
}
