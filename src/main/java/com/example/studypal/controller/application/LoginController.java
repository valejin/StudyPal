package com.example.studypal.controller.application;

import com.example.studypal.dao.UserDAO;
import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.exceptions.PersistenzaNonValida;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.other.FactoryDAO;

public class LoginController {

    String email;
    String nome;
    String cognome;

    boolean ruolo;

   public LoggedInUserBean loginMethod(CredenzialiBean credenzialiBean) throws CredenzialiSbagliateException, UtenteInesistenteException{
        CredenzialiModel credenzialiModel = new CredenzialiModel();
        LoggedInUserBean loggedInUserBean = new LoggedInUserBean(email, nome, cognome, ruolo);

        try {
            //CtrGUI prende input da utente e metto nel model
            credenzialiModel.setEmail(credenzialiBean.getEmail());
            credenzialiModel.setPassword(credenzialiBean.getPassword());

            UserDAO userDAO = FactoryDAO.getUserDAO();

            //utente effettua login (controllare l'esistenza di utente da DB)
            UserModel userModel = userDAO.loginMethod(credenzialiModel);

            //memorizzo oggetti da DB in loggedInUserBean per la persistenza
            loggedInUserBean.setNome(userModel.getNome());
            loggedInUserBean.setCognome(userModel.getCognome());
            loggedInUserBean.setEmail(userModel.getEmail());
            loggedInUserBean.setRuolo(userModel.getRuolo());


            //al momento non usiamo il valore di ritorno: restituisce il bean contenente le informazioni dell'utente che ha effettuato l'accesso
            return loggedInUserBean;

        } catch (CredenzialiSbagliateException e) {
            throw new CredenzialiSbagliateException("");
        } catch (UtenteInesistenteException u) {
            throw new UtenteInesistenteException();
        } catch (PersistenzaNonValida e){
            System.exit(1);
        }
        return null;
   }





}
