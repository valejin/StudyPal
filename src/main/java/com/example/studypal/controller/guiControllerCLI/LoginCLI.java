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
    public void action(StateMachineImpl SM) {

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
            Printer.println("Il Login è stato correttamente effettuato.");

            //ho finito, mi preparo al passaggio di stato
            AbstractState homeCLI;
            if (this.user.isTutor()) {
                System.out.println("caso tutor");
                homeCLI = new HomeTutorCLI(user);
            } else {
                System.out.println("caso studente");
                homeCLI = new HomeStudenteCLI(user);
            }

            goNext(SM, homeCLI); //chiamo il metodo goNext dello stato attuale che farà transizionare la stateMachine

        } catch (CredenzialiSbagliateException e) {
            Printer.errorPrint("Credenziali sbagliate.");
        } catch (UtenteInesistenteException u) {
            Printer.errorPrint("Attenzione! Utente inesistente.");
        }

    }





        /*
        Printer.print("   Email: ");
        String email = scanner.next();

        Printer.print("   Password: ");
        String password = scanner.next();

        /*ora istanziamo il controller applicativo e chiamiamo il suo metodo login così come
        nel controller grafico della prima interfaccia

        try {

            CredenzialiBean credenzialiBean = new CredenzialiBean();
            credenzialiBean.setEmail(email);
            credenzialiBean.setPassword(password);

            //istanziamo il controller applicativo che si deve occupare del login e gli passiamo il bean contenente le credenziali
            LoginController loginController = new LoginController();

            //prendiamo i dati dell'utente loggato (sessione)
            this.user = loginController.loginMethod(credenzialiBean);

            //dobbiamo cambiare stato!! (transition)

            mostraHome(user.getRuolo());

        } catch (CredenzialiSbagliateException e) {
            Printer.errorPrint("Credenziali sbagliate.");
        } catch (UtenteInesistenteException u) {
            Printer.errorPrint("Attenzione! Utente inesistente.");
        }
        */




    /*--------------------------------------------- PATTERN STATE ---------------------------------------------*/
    @Override
    public void entry(StateMachineImpl contextSM){
        //viene chiamata da mostraHome(?) per cambiare pagina (ovvero cambiare stato nella macchina a stati)
        stampaBenvenuto();
    }

    @Override
    public void exit(StateMachineImpl contextSM){
        //qui dobbiamo specificare azioni particolari relative all'uscita da questo stato
        Printer.println("Verrete reindirizzati alla prossima pagina");
    }


    @Override
    public void stampaBenvenuto() {
        Printer.println("-------------- LOGIN --------------");
    }


    /*

    non ci sono opzioni nel login!

    @Override
    public void mostraMenu(){
        Printer.println("   1. Login");
        Printer.println("   2. Registrazione");
        Printer.println("   0. Esci");
        Printer.print("Opzione scelta: ");
    }
    */

    /*------------------------------------ PASSAGGIO------------------------------*/
}
