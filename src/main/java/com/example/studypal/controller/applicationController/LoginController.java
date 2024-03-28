package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.UserDAO;
import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.LoginUserBean;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.LoginDBException;

public class LoginController {

    String email;
    String nome;
    String cognome;

   public LoginUserBean loginMethod(CredenzialiBean credenzialiBean) throws CredenzialiSbagliateException {
        CredenzialiModel credenzialiModel = new CredenzialiModel();
        LoginUserBean loginUserBean = new LoginUserBean(email, nome, cognome);

        try {
            credenzialiModel.setEmail(credenzialiBean.getEmail());
            credenzialiModel.setPassword(credenzialiBean.getPassword());

            UserDAO userDAO = new UserDAO();

            UserModel userModel = userDAO.loginMethod(credenzialiModel);
     /*  if(userModel.getEmail().isEmpty()) {
           return null;
       }*/
            loginUserBean.setNome(userModel.getNome());
            loginUserBean.setCognome(userModel.getCognome());
            loginUserBean.setEmail(userModel.getEmail());

            return loginUserBean;

        } catch (LoginDBException e) {
            System.out.println("controller applicativo credenziali sbagliate");
            throw new CredenzialiSbagliateException("Credenziali sbagliate,");
        }
   }
}
