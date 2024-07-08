package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.Scanner;


public class HomeStudenteCLI extends AbstractState {

    /* Stato HOME*/
    LoggedInUserBean user;

    public HomeStudenteCLI(LoggedInUserBean user){ this.user = user;}

    @Override
    public void action(StateMachineImpl context){

        /* l'azione della Home sta nel presentare le opzioni disponibili, quindi appare molto semplice*/

        Scanner scan = new Scanner(System.in);
        int scelta;

        while((scelta = scan.nextInt()) < 4) {

            switch(scelta){
                case(0):
                    Printer.println("Arrivederci!");
                    context.setState();
                    return;
                case(1):
                    //transizione a prenotaRipetizione
                    System.out.println("goToPrenotaRipetizione");
                    break;
                case(2):
                    //transizione a gestisciPrenotazioni
                    System.out.println("goToGestisciPrenotazioni");
                    break;
                case(3):
                    LoginCLI loginCLI = new LoginCLI();
                    this.goNext(context, loginCLI);
                    break;
                default:
                    Printer.println("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                    break;
            }
        }
    }

    @Override
    public void mostraMenu(){
        Printer.println("   1. Prenota Ripetizione");
        Printer.println("   2. Gestisci Prenotazioni");
        Printer.println("   0. Esci");
        Printer.print("   Opzione scelta: ");
    }

    @Override
    public void stampaBenvenuto(){
        Printer.println("-------------- HOME STUDENTE --------------");
        Printer.println("Ciao " + this.user.getNome() + ", scegli un'opzione:");
    }

    @Override
    public void entry(StateMachineImpl cli){
        stampaBenvenuto();
        mostraMenu();
    }

}
