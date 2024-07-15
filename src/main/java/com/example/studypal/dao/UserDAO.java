package com.example.studypal.dao;

import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;

public interface UserDAO {

    UserModel loginMethod(CredenzialiModel credenzialiModel) throws CredenzialiSbagliateException, UtenteInesistenteException;

    void registrazioneMethod(UserModel registrazioneModel);

    void controllaEmailMethod(UserModel registrazioneModel) throws EmailAlreadyInUseException;

    public void registraTutorMethod(String email, String nome, String cognome);



    }