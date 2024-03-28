package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.UserDAO;
import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.controller.guiController.RegistrazioneGuiController;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.model.UserModel;

import java.util.logging.Logger;

public class RegistrazioneController {

    /*
    controller applicativo della registrazione
    prende il bean registrazioneUserBean e popola il model che poi viene mandato al dao
    */

    public void registrazioneMethod(RegistrazioneUserBean registrazioneUserBean){

        UserModel userModel = new UserModel();


            userModel.setEmail(registrazioneUserBean.getEmail());
            userModel.setNome(registrazioneUserBean.getNome());
            userModel.setCognome(registrazioneUserBean.getCognome());
            userModel.setPassword(registrazioneUserBean.getPassword());
            userModel.setRuolo(registrazioneUserBean.getRuolo());

            UserDAO registrazioneDao = new UserDAO();

            /*controllo email
            try{
                registrazioneDao.controllaEmailMethod(userModel);

            } catch (EmailAlreadyInUseException e) {
                System.out.println("email già presente nel sistema");
            }*/

             registrazioneDao.registrazioneMethod(userModel);

        }

        /*
        gestisce la registrazione, riceve RegistrazioneUserBean
        controlla se password e confermaPassword sono uguali (se non lo sono lancia eccezione)
        crea model, popola model, chiama il metodo di registrazione del RegistrazioneDao
        */


        //ricorda di catchare l'eccezione email already in use
    }

