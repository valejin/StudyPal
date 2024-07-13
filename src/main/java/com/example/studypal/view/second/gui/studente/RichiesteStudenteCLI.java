package com.example.studypal.view.second.gui.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.application.studente.GestisciPrenotazioniStudenteController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RichiesteStudenteCLI extends AbstractState {

    private final LoggedInUserBean user;
    private final Integer flag;
    Scanner scanner = new Scanner(System.in);


    public RichiesteStudenteCLI(LoggedInUserBean user, Integer flag) {
        this.user = user;
        this.flag = flag;
    }


    @Override
    public void action(StateMachineImpl context) {
        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle());

        List<PrenotazioneBean> richiesteList = fetchRichiesteInviate();

        if (richiesteList.isEmpty()) {
            Printer.println("Nessuna richiesta trovata.");
        } else {
            displayRichieste(richiesteList);
            int scelta = getUserSelection(richiesteList.size());
            handleUserSelection(context, scelta, richiesteList);
        }
    }

    private List<PrenotazioneBean> fetchRichiesteInviate() {
        GestisciPrenotazioniStudenteController gestisciPrenotazioniController = new GestisciPrenotazioniStudenteController(user);
        return gestisciPrenotazioniController.richiesteInviate(user.getEmail(), flag);
    }

    private void displayRichieste(List<PrenotazioneBean> richiesteList) {
        for (int i = 0; i < richiesteList.size(); i++) {
            PrenotazioneBean prenotazione = richiesteList.get(i);
            Printer.println("-----------------------------------------");
            Printer.println((i + 1) + ". " + prenotazione.getEmailTutor() + " - " + prenotazione.getMateria() + " - " + prenotazione.getTariffa() + " €/h ");
        }
        Printer.println("-----------------------------------------");
    }

    private int getUserSelection(int listSize) {
        boolean inputValido = false;
        int scelta = -1;

        while (!inputValido) {
            try {
                Printer.print("Seleziona una richiesta da visualizzare (0 per tornare indietro): ");
                scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                if (scelta >= 0 && scelta <= listSize) {
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

    private void handleUserSelection(StateMachineImpl context, int scelta, List<PrenotazioneBean> richiesteList) {
        if (scelta > 0) {
            PrenotazioneBean prenotazioneSelezionata = richiesteList.get(scelta - 1);
            goNext(context, new VisualizzaRichiesteStudenteCLI(user, prenotazioneSelezionata, richiesteList, flag)); // Transizione a VisualizzaRichiesteCLI
        } else if (scelta == 0) {
            goBack(context);
        }
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
            case 0 -> "Home Studente -> Gestisci Prenotazioni -> Richieste Inviate";
            case 1 -> "Home Studente -> Gestisci Prenotazioni -> Prenotazioni Attive";
            case 2 -> "Home Studente -> Gestisci Prenotazioni -> Richieste Rifiutate";
            default -> "Home Studente -> Gestisci Prenotazioni";
        };
    }

}
