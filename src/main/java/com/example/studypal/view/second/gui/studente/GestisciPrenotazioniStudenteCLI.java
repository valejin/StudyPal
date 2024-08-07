package com.example.studypal.view.second.gui.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;


public class GestisciPrenotazioniStudenteCLI extends AbstractState {

    private final LoggedInUserBean user;


    public GestisciPrenotazioniStudenteCLI(LoggedInUserBean user) {
        this.user = user;
    }

    @Override
    public void action(StateMachineImpl context) {

        //menu mostrato da funzione entry

        Scanner scanner = new Scanner(System.in);

        int choice = -1;
        int flag;

        while(choice != 0) {
            mostraMenu();

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        flag = 0;
                        goNext(context, new RichiesteStudenteCLI(user, flag));
                        break;
                    case 2:
                        flag = 1;
                        goNext(context, new RichiesteStudenteCLI(user, flag));
                        break;
                    case 3:
                        flag = 2;
                        goNext(context, new RichiesteStudenteCLI(user, flag));
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida. Scegli un'opzione tra quelle proposte.");
                        break;
                }
            } catch (InputMismatchException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scanner.nextLine(); // Consuma l'input non valido
            }
        }

        goBack(context);
    }


    @Override
    public void mostraMenu(){
        Printer.println("   1. Richieste Inviate");
        Printer.println("   2. Prenotazioni Attive");
        Printer.println("   3. Richieste Rifiutate");
        Printer.println("   0. Torna Indietro");
        Printer.print("Scegli un'opzione: ");
    }

    @Override
    public void stampaBenvenuto (){
        Printer.println(" ");
        Printer.printlnBlu("Home Studente -> Gestione Prenotazioni:");
    }

    @Override
    public void entry(StateMachineImpl context){
        stampaBenvenuto();
    }

}
