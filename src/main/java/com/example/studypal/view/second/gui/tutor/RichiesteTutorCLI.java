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

                if (choice == 1) {
                    visualizzaRichieste(context);
                    mostraMenu();
                    break;
                } else if (choice == 0) {
                    break;
                } else {
                    Printer.errorPrint("Scelta non valida. Riprova.");
                    break;
                }
            }catch (InputMismatchException e){
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scanner.nextLine(); // Consuma l'input non valido
            }
        }
        goBack(context);   //torno allo stato precedente

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


            boolean inputValido = false;
            int scelta = 0;


            while(!inputValido) {
                try {

                    Printer.print("Seleziona una richiesta da visualizzare (0 per tornare indietro): ");
                    scelta =scanner.nextInt();
                    scanner.nextLine(); // Consuma newline
                    inputValido = true; // Imposta inputValido a true se la lettura è andata a buon fine

                    if (scelta < 0 || scelta > richiesteList.size()) {
                        Printer.errorPrint("Scelta non valida. Riprova.");
                        inputValido = false; // Riprova se la scelta non è nel range corretto
                    }

                }catch(InputMismatchException e){
                    Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                    scanner.nextLine(); // Consuma l'input non valido
                }
            }

            if (scelta > 0 && scelta <= richiesteList.size()) {
                PrenotazioneBean prenotazioneSelezionata = richiesteList.get(scelta - 1);
                goNext(context, new VisualizzaRichiesteCLI(user, prenotazioneSelezionata, richiesteList, flag)); // Transizione a VisualizzaRichiesteCLI
            }
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
        switch (flag) {
            case 0:
                return "Home Tutor -> Gestisci Prenotazioni -> Richieste Arrivate";
            case 1:
                return "Home Tutor -> Gestisci Prenotazioni -> Prenotazioni Attive";
            case 2:
                return "Home Tutor -> Gestisci Prenotazioni -> Richieste Rifiutate";
            default:
                return "Home Tutor -> Gestisci Prenotazioni";
        }
    }







}





