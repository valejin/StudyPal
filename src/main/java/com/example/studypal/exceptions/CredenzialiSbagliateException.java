package com.example.studypal.exceptions;

public class CredenzialiSbagliateException extends Exception{
    //eccezione lanciata dal controller quando riceve eccezione proveniente dal DAO

    public CredenzialiSbagliateException(String message) { super(message); }
}
