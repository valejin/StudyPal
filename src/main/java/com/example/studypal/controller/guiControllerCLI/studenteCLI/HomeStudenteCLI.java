package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.InitialState;
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
        int choice;

        while((choice = scan.nextInt()) != 0) {

            switch(choice){
                case(1):
                    //transizione a prenotaRipetizione
                    goNext(context, new PrenotaRipetizioneCLI(user));
                    break;
                case(2):
                    //transizione a gestisciPrenotazioni
                    System.out.println("goToGestisciPrenotazioni");
                    break;
                default:
                    Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                    break;
            }
        }

        goNext(context, new InitialState());

    }

    @Override
    public void mostraMenu(){
        Printer.println("   1. Prenota Ripetizione");
        Printer.println("   2. Gestisci Prenotazioni");
        Printer.println("   0. Logout");
        Printer.print("Opzione scelta: ");
    }

    @Override
    public void stampaBenvenuto(){
        Printer.println(" ");
        Printer.println("-------------- HOME STUDENTE --------------");
        Printer.println("Ciao " + this.user.getNome() + ", scegli un'opzione:");
    }

    @Override
    public void entry(StateMachineImpl cli){
        stampaBenvenuto();
        mostraMenu();
    }

}
