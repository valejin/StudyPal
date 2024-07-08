package com.example.studypal.controller.guiControllerCLI;

import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.applicationController.LoginController;
import com.example.studypal.controller.guiControllerCLI.studenteCLI.HomeStudenteCLI;
import com.example.studypal.controller.guiControllerCLI.tutorCLI.HomeTutorCLI;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.Scanner;

public class LoginCLI extends AbstractState {

    /* controller grafico che gestisce il login per l'interfaccia a riga di comando */

    LoggedInUserBean user;

    public LoginCLI() {
    }

    private final Scanner scanner = new Scanner(System.in);


    /*-----------------------------------------AZIONE----------------------------------------------*/
    @Override
    public void action(StateMachineImpl context) {

        Printer.print("   Email: ");
        String email = scanner.next();

        Printer.print("   Password: ");
        String password = scanner.next();

    /*ora istanziamo il controller applicativo e chiamiamo il suo metodo login così come
    nel controller grafico della prima interfaccia*/

        try {

            CredenzialiBean credenzialiBean = new CredenzialiBean();
            credenzialiBean.setEmail(email);
            credenzialiBean.setPassword(password);

            //istanziamo il controller applicativo che si deve occupare del login e gli passiamo il bean contenente le credenziali
            LoginController loginController = new LoginController();

            //prendiamo i dati dell'utente loggato (sessione)
            this.user = loginController.loginMethod(credenzialiBean);

            //ho finito, mi preparo al passaggio di stato
            AbstractState homeCLI;

            if (this.user.isTutor()) {
                homeCLI = new HomeTutorCLI(user);
            } else {
                homeCLI = new HomeStudenteCLI(user);
            }

            goNext(context, homeCLI); //chiamo il metodo goNext dello stato attuale che farà transizionare la stateMachine

        } catch (CredenzialiSbagliateException e) {
            Printer.errorPrint("Credenziali sbagliate.");
            action(context);
        } catch (UtenteInesistenteException u) {
            Printer.errorPrint("Utente inesistente.");
            action(context);
        }

    }



    /*--------------------------------------------- PATTERN STATE ---------------------------------------------*/
    @Override
    public void entry(StateMachineImpl contextSM){
        //viene chiamata da mostraHome(?) per cambiare pagina (ovvero cambiare stato nella macchina a stati)
        stampaBenvenuto();
    }

    @Override
    public void exit(StateMachineImpl contextSM){
        //qui dobbiamo specificare azioni particolari relative all'uscita da questo stato
        Printer.println("login -> home");
    }


    @Override
    public void stampaBenvenuto() {
        Printer.println(" ");
        Printer.println("-------------- LOGIN --------------");
    }
}
