package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.UserDAO;
import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.LoginDBException;
import com.example.studypal.other.Printer;

public class LoginController {

    String email;
    String nome;
    String cognome;

    boolean ruolo;

   public LoggedInUserBean loginMethod(CredenzialiBean credenzialiBean) throws CredenzialiSbagliateException {
        CredenzialiModel credenzialiModel = new CredenzialiModel();
        LoggedInUserBean loggedInUserBean = new LoggedInUserBean(email, nome, cognome, ruolo);

        try {
            credenzialiModel.setEmail(credenzialiBean.getEmail());
            credenzialiModel.setPassword(credenzialiBean.getPassword());

            UserDAO userDAO = new UserDAO();

            UserModel userModel = userDAO.loginMethod(credenzialiModel);

            loggedInUserBean.setNome(userModel.getNome());
            loggedInUserBean.setCognome(userModel.getCognome());
            loggedInUserBean.setEmail(userModel.getEmail());
            loggedInUserBean.setRuolo(userModel.getRuolo());
            System.out.println("login method, siamo:" + userModel.getRuolo());

            //al momento non usiamo il valore di ritorno: restituisce il bean contenente le informazioni dell'utente che ha effettuato l'accesso
            return loggedInUserBean;

        } catch (LoginDBException e) {
            Printer.errorPrint("controller applicativo credenziali sbagliate");
            throw new CredenzialiSbagliateException("Credenziali sbagliate,");
        }
   }
}
