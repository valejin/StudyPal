package com.example.studypal.view.second.gui.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.application.tutor.GestisciPrenotazioniController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RichiesteTutorCLI extends AbstractState {

    private final LoggedInUserBean user;
    private final Integer flag;


    public RichiesteTutorCLI(LoggedInUserBean user, Integer flag) {
        this.user = user;
        this.flag = flag;
    }



    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            mostraMenu();

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                handleChoice(context, choice);
            } catch (InputMismatchException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scanner.nextLine(); // Consuma l'input non valido
            }
        }
    }

    private void handleChoice(StateMachineImpl context, int choice) {
        switch (choice) {
            case 1:
                visualizzaRichieste(context);
                mostraMenu();
                break;
            case 0:
                goBack(context);
                break;
            default:
                Printer.errorPrint("Scelta non valida. Riprova.");
                break;
        }
    }


    public void visualizzaRichieste(StateMachineImpl context) {
        List<PrenotazioneBean> richiesteList;

        Scanner scanner = new Scanner(System.in);

        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle() + " -> Visualizza Richieste: ");

        // Creo un'istanza del controller applicativo corrispondente
        GestisciPrenotazioniController gestisciPrenotazioniController = new GestisciPrenotazioniController();

        // Chiamo la funzione nel controller applicativo per ottenere una lista di BEAN che contiene tutte le info per la tabella
        richiesteList = gestisciPrenotazioniController.richiesteArrivate(user, flag);

        if (richiesteList.isEmpty()) {
            Printer.println("Nessuna richiesta trovata.");
        } else {
            for (int i = 0; i < richiesteList.size(); i++) {
                PrenotazioneBean prenotazione = richiesteList.get(i);
                Printer.println((i + 1) + ". " + prenotazione.getEmailStudente() + " - " + prenotazione.getMateria());
            }

            int scelta = chiediScelta(scanner, richiesteList.size());
            if (scelta > 0 && scelta <= richiesteList.size()) {
                PrenotazioneBean prenotazioneSelezionata = richiesteList.get(scelta - 1);
                goNext(context, new VisualizzaRichiesteCLI(prenotazioneSelezionata, flag)); // Transizione a VisualizzaRichiesteCLI
            }
        }
    }

    private int chiediScelta(Scanner scanner, int maxSize) {
        int scelta = 0;
        boolean inputValido = false;

        while (!inputValido) {
            try {
                Printer.print("Seleziona una richiesta da visualizzare (0 per tornare indietro): ");
                scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                if (scelta >= 0 && scelta <= maxSize) {
                    inputValido = true;
                } else {
                    Printer.errorPrint("Scelta non valida. Riprova.");
                }
            } catch (InputMismatchException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scanner.nextLine(); // Consuma l'input non valido
            }
        }

        return scelta;
    }



    @Override
    public void mostraMenu(){

        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle() + ": ");
        Printer.println("1. Visualizza Richieste");
        Printer.println("0. Torna Indietro");
        Printer.print("Scegli un'opzione: ");

    }



    private String getMenuTitle() {
        return switch (flag) {
            case 0 -> "Home Tutor -> Gestisci Prenotazioni -> Richieste Arrivate";
            case 1 -> "Home Tutor -> Gestisci Prenotazioni -> Prenotazioni Attive";
            case 2 -> "Home Tutor -> Gestisci Prenotazioni -> Richieste Rifiutate";
            default -> "Home Tutor -> Gestisci Prenotazioni";
        };
    }







}





