package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.InitialState;
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

        int choice;

        while((choice = scanner.nextInt()) != 0) {

            scanner.nextLine(); // Consuma newline

            switch (choice) {
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
        }

        goNext(context, new InitialState());
    }

    @Override
    public void mostraMenu(){
        Printer.println("1. Gestisci Profilo");
        Printer.println("2. Gestisci Prenotazioni");
        Printer.println("0. Logout");
        Printer.print("Scegli un'opzione: ");
    }

    @Override
    public void stampaBenvenuto() {
        Printer.println(" ");
        Printer.println("-------------- HOME TUTOR --------------");
        Printer.println("Ciao " + this.user.getNome() + ", scegli un'opzione:");
    }

    @Override
    public void entry(StateMachineImpl cli){
        stampaBenvenuto();
        mostraMenu();
    }

}

