package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.UserDAO;
import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.model.UserModel;


public class RegistrazioneController {

    /*
    controller applicativo della registrazione
    prende il bean registrazioneUserBean e popola il model che poi viene mandato al dao
    */

    public void registrazioneMethod(RegistrazioneUserBean registrazioneUserBean) throws EmailAlreadyInUseException{

        UserModel userModel = new UserModel();

            userModel.setEmail(registrazioneUserBean.getEmail());
            userModel.setNome(registrazioneUserBean.getNome());
            userModel.setCognome(registrazioneUserBean.getCognome());
            userModel.setPassword(registrazioneUserBean.getPassword());
            userModel.setRuolo(registrazioneUserBean.getRuolo());

            UserDAO registrazioneDao = new UserDAO();

            try{
                registrazioneDao.controllaEmailMethod(userModel);

            } catch (EmailAlreadyInUseException e) {
                System.out.println("email già presente nel sistema");
                throw new EmailAlreadyInUseException();
                //il controller applicativo si limita a propagarla al controller grafico
            }

            registrazioneDao.registrazioneMethod(userModel);

            if (registrazioneUserBean.getRuolo()){
                registrazioneDao.registraTutorMethod(userModel.getEmail(), userModel.getNome(), userModel.getCognome());
            }
        }
}

