package com.example.studypal.exceptions;

public class CredenzialiSbagliateException extends Exception{
    //eccezione lanciata dal controller quando riceve eccezione proveniente dal DAO

   /* @Serial
    private static final long serialVersionUID = 1L;*/

    public CredenzialiSbagliateException(String message) { super(message); }
}
