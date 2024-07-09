package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.InitialState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
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

            try{
                switch(choice){
                    case(1):
                        goNext(context, new CercaRipetizioneCLI(user));
                        break;
                    case(2):
                        goNext(context, new GestisciPrenotazioniStudenteCLI(user));
                        break;
                    default:
                        Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                        break;
                }
            } catch (InputMismatchException e){
                Printer.errorPrint("Input non valido. Per favore, inserisci un numero intero: ");
                scan.nextLine(); // Consuma l'input non valido)
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
        Printer.printlnBlu("-------------- HOME STUDENTE --------------");
        Printer.println("Ciao " + this.user.getNome() + ", scegli un'opzione:");
    }

    @Override
    public void entry(StateMachineImpl cli){
        stampaBenvenuto();
        mostraMenu();
    }

}
