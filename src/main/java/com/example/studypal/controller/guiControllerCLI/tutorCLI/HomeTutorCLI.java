package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.Scanner;
import java.util.logging.Logger;

public class HomeTutorCLI extends AbstractState {

    // controller grafico per mostrare le opzioni aggiungibili
    private static final Logger logger = Logger.getLogger(HomeTutorCLI.class.getName());
    private final LoggedInUserBean user;

    public HomeTutorCLI(LoggedInUserBean user) {
        this.user = user;
    }


    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);

        Printer.println(" ");
        Printer.println("Home Tutor:");
        Printer.println("1. Gestisci Profilo");
        Printer.println("2. Gestisci Prenotazioni");
        Printer.println("0. Logout");
        Printer.print("Scegli un'opzione: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consuma newline
        System.out.println("home - ho letto");

        switch (choice) {
            case 1:
                goNext(context, new GestisciProfiloCLI(user));
                break;
            case 2:
                goNext(context, new GestisciPrenotazioniCLI(user));
                break;
            case 0:
                //goNext(context, new LoginCLI()); // Torna alla pagina di login
                context.setState();
                return;
            default:
                Printer.println("Scelta non valida. Riprova.");
                goNext(context, new HomeTutorCLI(user));
                break;
        }
    }
}

