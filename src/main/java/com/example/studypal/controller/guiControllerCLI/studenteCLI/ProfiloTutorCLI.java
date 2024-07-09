package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

public class ProfiloTutorCLI extends AbstractState {

    LoggedInUserBean user;
    RipetizioneInfoBean tutor;

    public ProfiloTutorCLI(LoggedInUserBean user, RipetizioneInfoBean tutorSelezionato){
        this.user = user;
        this.tutor = tutorSelezionato;
    }

    @Override
    public void action(StateMachineImpl context){
        Printer.errorPrint("DA IMPLEMENTARE");
    }
}
