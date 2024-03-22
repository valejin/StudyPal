package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.UserDAO;
import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.UserBean;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;

public class LoginController {
   public UserBean loginMethod(CredenzialiBean credenzialiBean){
        CredenzialiModel credenzialiModel = new CredenzialiModel();
        UserBean userBean = new UserBean();

        credenzialiModel.setEmail(credenzialiBean.getEmail());
        credenzialiModel.setPassword(credenzialiBean.getPassword());

       UserDAO userDAO = new UserDAO();

       UserModel userModel = userDAO.loginMethod(credenzialiModel);

       if(userModel.getEmail().isEmpty()) {
           return null;
       }

       userBean.setNome(userModel.getNome());
       userBean.setCognome(userModel.getCognome());
       userBean.setEmail(userModel.getEmail());

       return userBean;
    }


}
