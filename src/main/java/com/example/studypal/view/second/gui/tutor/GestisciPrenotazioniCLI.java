package com.example.studypal.view.second.gui.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;


public class GestisciPrenotazioniCLI extends AbstractState {

    private final LoggedInUserBean user;


    public GestisciPrenotazioniCLI(LoggedInUserBean user) {
        this.user = user;
    }

    @Override
    public void action(StateMachineImpl context) {

        //menu mostrato da funzione entry

        Scanner scanner = new Scanner(System.in);

        int choice = -1;


        while(choice != 0) {
            mostraMenu();

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        goToRichiesteArrivate(context);
                        break;
                    case 2:
                        goToPrenotazioniAttive(context);
                        break;
                    case 3:
                        goToRichiesteRifiutate(context);
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida. Riprova.");
                        break;
                }
            } catch (InputMismatchException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scanner.nextLine(); // Consuma l'input non valido
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
        goNext(context, new RichiesteTutorCLI(user, 2));
    }

    @Override
    public void mostraMenu(){
        Printer.println(" ");
        Printer.printlnBlu("Home Tutor -> Gestione Prenotazioni:");
        Printer.println("1. Richieste Arrivate");
        Printer.println("2. Prenotazioni Attive");
        Printer.println("3. Richieste Rifiutate");
        Printer.println("0. Torna Indietro");
        Printer.print("Scegli un'opzione: ");
    }


}
