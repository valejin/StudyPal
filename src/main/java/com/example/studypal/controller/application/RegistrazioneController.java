package com.example.studypal.controller.application;

import com.example.studypal.dao.UserDAO;
import com.example.studypal.dao.UserDAOMySQL;
import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.PersistenzaNonValida;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.FactoryDAO;
import com.example.studypal.other.Printer;


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


            try{
                UserDAO registrazioneDao = FactoryDAO.getUserDAO();
                registrazioneDao.controllaEmailMethod(userModel);
                registrazioneDao.registrazioneMethod(userModel);

                if (registrazioneUserBean.getRuolo()){
                    registrazioneDao.registraTutorMethod(userModel.getEmail(), userModel.getNome(), userModel.getCognome());
                }

            } catch (EmailAlreadyInUseException e) {
                throw new EmailAlreadyInUseException();
                //il controller applicativo si limita a propagarla al controller grafico
            } catch (PersistenzaNonValida e){
                Printer.errorPrint("Persistenza non valida.");

                //System.exit(1);
            }

        }
}

