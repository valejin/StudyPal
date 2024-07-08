package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.Scanner;
import java.util.logging.Logger;

public class GestisciPrenotazioniCLI extends AbstractState {

    private static final Logger logger = Logger.getLogger(GestisciPrenotazioniCLI.class.getName());
    private final LoggedInUserBean user;


    public GestisciPrenotazioniCLI(LoggedInUserBean user) {
        this.user = user;
    }

    @Override
    public void action(StateMachineImpl context) {

        //menu mostrato da funzione entry

        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consuma newline

        if (choice != 0) {
            switch (choice) {
                case 1:
                    goToRichiesteArrivate();
                    break;
                case 2:
                    goToPrenotazioniAttive();
                    break;
                case 3:
                    goToRichiesteRifiutate();
                    break;
                default:
                    Printer.println("Scelta non valida. Riprova.");
                    break;
            }
        }


        goBack(context);
    }



    public void goToRichiesteArrivate() {
        System.out.println("Navigando a: Richieste Arrivate");
        // Implementa la logica per visualizzare le richieste arrivate
        // Ad esempio: RichiesteTutorCLI richiesteTutorCLI = new RichiesteTutorCLI(user, 0);
    }

    public void goToPrenotazioniAttive() {
        System.out.println("Navigando a: Prenotazioni Attive");
        // Implementa la logica per visualizzare le prenotazioni attive
        // Ad esempio: PrenotazioniAttiveCLI prenotazioniAttiveCLI = new PrenotazioniAttiveCLI(user);
    }

    public void goToRichiesteRifiutate() {
        System.out.println("Navigando a: Richieste Rifiutate");
        // Implementa la logica per visualizzare le richieste rifiutate
        // Ad esempio: RichiesteRifiutateCLI richiesteRifiutateCLI = new RichiesteRifiutateCLI(user);
    }

    @Override
    public void mostraMenu(){
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
