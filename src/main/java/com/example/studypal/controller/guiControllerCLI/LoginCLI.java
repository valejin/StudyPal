package com.example.studypal.controller.guiControllerCLI;

import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.applicationController.LoginController;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.other.Printer;

import java.util.Scanner;

public class LoginCLI {

    /* controller grafico che gestisce il login per l'interfaccia a riga di comando */
    LoggedInUserBean user;
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
                    break;
                case (2):
                    registrazione();
                    break;
                default:
                    Printer.println("L'opzione scelta non è valida.");
            }
        }
    }


    private void stampaBenvenuto(){
        Printer.println("--------------Benvenuto a StudyPal!--------------");
        Printer.println("   1. Login");
        Printer.println("   2. Registrazione");
        Printer.println("   0. Esci");
        Printer.println("Opzione scelta: ");
    }

    private void login(){

        Printer.println("   Email: ");
        String email = scanner.next();

        Printer.print(" Password: ");
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
            Printer.errorPrint("Credenziali sbagliate");
        } catch (UtenteInesistenteException u) {
            Printer.errorPrint("Utente inesistente");
        }

    }
    private void registrazione(){
        //da finire
    }

    private void mostraHome(Boolean ruolo){
        //qui dovremmo passare all'altro stato (state pattern)
    }

}
