package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;


public class HomeTutorCLI extends AbstractState {

    private final LoggedInUserBean user;

    public HomeTutorCLI(LoggedInUserBean user) {
        this.user = user;
    }


    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);

        int choice = -1;
        //nel while: (choice = scanner.nextInt()) != 0

        while(choice != 0) {

            mostraMenu();
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        goNext(context, new GestisciProfiloCLI(user));
                        break;
                    case 2:
                        goNext(context, new GestisciPrenotazioniCLI(user));
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida. Riprova: ");
                        break;
                }
            } catch(InputMismatchException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scanner.nextLine(); // Consuma l'input non valido
            }
        }

        goBack(context);
    }

    @Override
    public void mostraMenu(){
        Printer.println(" ");
        Printer.println("1. Gestisci Profilo");
        Printer.println("2. Gestisci Prenotazioni");
        Printer.println("0. Logout");
        Printer.print("Scegli un'opzione: ");
    }

    @Override
    public void stampaBenvenuto() {
        Printer.println(" ");
        Printer.printlnBlu("-------------- HOME TUTOR --------------");
        Printer.println("Ciao " + this.user.getNome() + ", scegli un'opzione:");
    }

    @Override
    public void entry(StateMachineImpl cli){
        stampaBenvenuto();
    }

}

