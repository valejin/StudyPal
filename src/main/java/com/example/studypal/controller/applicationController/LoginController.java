package com.example.studypal.controller.applicationController;

import com.example.studypal.DAO.UserDAO;
import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;

public class LoginController {

   public void loginMethod(CredenzialiBean credenzialiBean){
        CredenzialiModel credenzialiModel = new CredenzialiModel();

        credenzialiModel.setEmail(credenzialiBean.getEmail());
        credenzialiModel.setPassword(credenzialiBean.getPassword());

       UserDAO userDAO = new UserDAO();

       userDAO.loginMethod(credenzialiModel);

    }


}
