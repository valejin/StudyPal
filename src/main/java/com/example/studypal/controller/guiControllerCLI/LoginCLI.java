package com.example.studypal.controller.guiControllerCLI;

import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.applicationController.LoginController;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;

import java.util.Scanner;

public class LoginCLI extends AbstractState {

    /* controller grafico che gestisce il login per l'interfaccia a riga di comando */

    //todo: correggere tutte le stampe a terminale che avevamo prima per il debug!

    LoggedInUserBean user;

    public LoginCLI(){}
    public LoginCLI(LoggedInUserBean user){ this.user = user;}

    private Scanner scanner = new Scanner(System.in);

    public void start(){
        scanner = new Scanner(System.in);
        boolean esci = false;

        while (!esci){
            stampaBenvenuto();

            int scelta = scanner.nextInt();

            switch(scelta){
                case(0):
                    Printer.println("Arrivederci!");
                    esci = true;
                    break;
                case (1):
                    //login
                    login();
                    esci=true;
                    break;
                case (2):
                    registrazione();
                    esci=true;
                    break;
                default:
                    Printer.println("L'opzione scelta non è valida.");
            }
        }
    }

    @Override
    public void stampaBenvenuto(){
        Printer.println("--------------Benvenuto a StudyPal!--------------");
        Printer.println("   1. Login");
        Printer.println("   2. Registrazione");
        Printer.println("   0. Esci");
        Printer.print("Opzione scelta: ");
    }

    private void login(){

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

            mostraHome(user.getRuolo());

        } catch (CredenzialiSbagliateException e) {
            Printer.errorPrint("Credenziali sbagliate.");
        } catch (UtenteInesistenteException u) {
            Printer.errorPrint("Attenzione! Utente inesistente.");
        }

    }



    /*--------------------------------------------- PATTERN STATE ---------------------------------------------*/
    @Override
    public void goNext(){
        //viene chiamata da mostraHome(?) per cambiare pagina (ovvero cambiare stato nella macchina a stati)

    }

    private void registrazione(){
        //da finire
    }

    private void mostraHome(Boolean isTutor){
        //qui dovremmo passare all'altro stato (state pattern)
        Printer.println("Il Login è stato correttamente effettuato.");
        Printer.print("Ruolo: ");
        if (isTutor){
            Printer.println("Tutor");
        } else {
            Printer.println("Studente");
        }


    }

}
