package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.pattern.state.AbstractState;

public class HomeTutorCLI extends AbstractState {

    LoggedInUserBean user;

    public HomeTutorCLI(LoggedInUserBean user){ this.user = user; }


}
