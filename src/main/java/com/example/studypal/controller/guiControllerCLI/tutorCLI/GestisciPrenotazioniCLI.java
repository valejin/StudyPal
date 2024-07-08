package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.Scanner;
import java.util.logging.Logger;

public class GestisciPrenotazioniCLI extends AbstractState {

    private final LoggedInUserBean user;


    public GestisciPrenotazioniCLI(LoggedInUserBean user) {
        this.user = user;
    }

    @Override
    public void action(StateMachineImpl context) {

        //menu mostrato da funzione entry

        Scanner scanner = new Scanner(System.in);

        int choice;

        if ((choice = scanner.nextInt()) != 0) {
            scanner.nextLine(); // Consuma newline

            switch (choice) {
                case 1:
                    goToRichiesteArrivate(context);
                    mostraMenu();
                    break;
                case 2:
                    goToPrenotazioniAttive(context);
                    mostraMenu();
                    break;
                case 3:
                    goToRichiesteRifiutate(context);
                    mostraMenu();
                    break;
                default:
                    Printer.println("Scelta non valida. Riprova.");
                    break;
            }
        }


        goBack(context);
    }



    public void goToRichiesteArrivate(StateMachineImpl context) {
        goNext(context, new RichiesteTutorCLI(user, 0));

    }

    public void goToPrenotazioniAttive(StateMachineImpl context) {
        goNext(context, new RichiesteTutorCLI(user, 1));
    }

    public void goToRichiesteRifiutate(StateMachineImpl context) {
        goNext(context, new RichiesteTutorCLI(user, 1));
    }

    @Override
    public void mostraMenu(){
        Printer.println(" ");
        Printer.print("Home Tutor -> ");
        Printer.println("Gestione Prenotazioni:");
        Printer.println("1. Richieste Arrivate");
        Printer.println("2. Prenotazioni Attive");
        Printer.println("3. Richieste Rifiutate");
        Printer.println("0. Torna Indietro");
        Printer.println("Scegli un'opzione: ");
    }

    @Override
    public void entry(StateMachineImpl context){
        mostraMenu();
    }

}
