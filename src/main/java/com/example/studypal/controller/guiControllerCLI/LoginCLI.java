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
import java.util.logging.Logger;

public class LoginCLI extends AbstractState {

    /* controller grafico che gestisce il login per l'interfaccia a riga di comando */

    //todo: correggere tutte le stampe a terminale che avevamo prima per il debug!

    LoggedInUserBean user;
    private static final Logger logger = Logger.getLogger(LoginCLI.class.getName());

    public LoginCLI(){}
    public LoginCLI(LoggedInUserBean user){ this.user = user;}

    private Scanner scanner = new Scanner(System.in);

    public void start(){
        scanner = new Scanner(System.in);
        boolean esci = false;

        while (!esci){
            stampaBenvenuto();

            mostraMenu();
            int scelta = scanner.nextInt();

            switch(scelta){
                case(0):
                    Printer.println("Arrivederci!");
                    esci = true;
                    break;
                case (1):
                    //login, chiama il metodo principale dello stato attuale (doAction)
                    //action();
                    esci=true;
                    break;
                case (2):
                    //dobbiamo cambiare stato!! (transition)
                    registrazione();
                    esci=true;
                    break;
                default:
                    Printer.println("L'opzione scelta non è valida.");
            }
        }
    }


    /*-----------------------------------------AZIONE----------------------------------------------*/
    @Override
    public void action(StateMachineImpl context){

        Scanner scanner = new Scanner(System.in);
        Printer.println("Inserisci email: ");
        String email = scanner.nextLine();
        Printer.println("Inserisci password: ");
        String password = scanner.nextLine();


        /*ora istanziamo il controller applicativo e chiamiamo il suo metodo login così come
        nel controller grafico della prima interfaccia*/

        try {
            CredenzialiBean credenzialiBean = new CredenzialiBean();
            credenzialiBean.setEmail(email);
            credenzialiBean.setPassword(password);

            LoginController loginController = new LoginController();
            LoggedInUserBean loggedInUserBean = loginController.loginMethod(credenzialiBean);

            context.setUser(loggedInUserBean);
            caricaHome(context, loggedInUserBean.getRuolo());

        } catch (CredenzialiSbagliateException e) {
            Printer.println("Credenziali sbagliate.");
        } catch (UtenteInesistenteException u) {
            Printer.println("Utente inesistente.");
        }



        //}



    }



    /*--------------------------------------------- PATTERN STATE ---------------------------------------------*/
    @Override
    public void entry(StateMachineImpl contextSM){
        //viene chiamata da mostraHome(?) per cambiare pagina (ovvero cambiare stato nella macchina a stati)
    }

    @Override
    public void exit(StateMachineImpl contextSM){
        //qui dobbiamo specificare azioni particolari relative all'uscita da questo stato
        Printer.println("Verrete reindirizzati alla prossima pagina");
    }


    @Override
    public void stampaBenvenuto() {
        Printer.println("--------------Benvenuto a StudyPal!--------------");
        Printer.println("E' necessario accedere al sistema.");
    }

    @Override
    public void mostraMenu(){
        Printer.println("   1. Login");
        Printer.println("   2. Registrazione");
        Printer.println("   0. Esci");
        Printer.print("Opzione scelta: ");
    }


    /*------------------------------------ PASSAGGIO------------------------------*/


    private void registrazione(){
        //in realtà questo metodo non serve, dobbiamo passare all'altro stato (transition)
    }

    private void caricaHome(StateMachineImpl context, boolean isTutor) {
        if (isTutor) {
            context.transition(new HomeTutorCLI(context.getUser()));
        } else {
            context.transition(new HomeStudenteCLI(context.getUser()));
        }
    }
}
