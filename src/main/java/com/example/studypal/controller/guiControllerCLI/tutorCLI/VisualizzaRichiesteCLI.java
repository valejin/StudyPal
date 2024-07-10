package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.tutor.GestisciPrenotazioniController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class VisualizzaRichiesteCLI extends AbstractState {

    private final LoggedInUserBean user;
    private final PrenotazioneBean dettagliRichiesta;
    private final Integer flag;


    public VisualizzaRichiesteCLI(LoggedInUserBean user, PrenotazioneBean prenotazioneBean, List<PrenotazioneBean> list, Integer flag) {
        this.user = user;
        this.dettagliRichiesta = prenotazioneBean;
        this.flag = flag;
    }



    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);

        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle() + " -> Dettagli Richiesta:");

        Printer.println("Email Studente: " + dettagliRichiesta.getEmailStudente());
        Printer.println("Materia: " + dettagliRichiesta.getMateria());
        Printer.println("Modalità Lezione: " + getModalitaLezione(dettagliRichiesta.getModLezione()));
        Printer.println("Giorni: " + (dettagliRichiesta.getGiorno() != null && !dettagliRichiesta.getGiorno().isEmpty() ? dettagliRichiesta.getGiorno() : "Non specificato"));
        Printer.println("Note: " + (dettagliRichiesta.getNote() != null && !dettagliRichiesta.getNote().isEmpty() ? "\"" + dettagliRichiesta.getNote() + "\"" : "Nessuna nota"));




        int choice = -1;

        while (choice != 0) {

            mostraMenu();

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        confermaRichiesta();
                        break;
                    case 2:
                        rifiutaRichiesta();
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

        goBack(context);   //ritorno allo stato precedente
    }




    private String getModalitaLezione(int modalita) {
        return switch (modalita) {
            case 1 -> "In presenza";
            case 2 -> "Online";
            default -> "Non specificato";
        };
    }



    private void confermaRichiesta() {
        Printer.println("IDRICHIESTA: " + dettagliRichiesta.getIdRichiesta());
        GestisciPrenotazioniController gestisciPrenotazioniController = new GestisciPrenotazioniController();
        gestisciPrenotazioniController.modificaStatoRichiesta(dettagliRichiesta.getIdRichiesta(), 1);  //confermo
        Printer.println("Richiesta confermata con successo.");
    }



    private void rifiutaRichiesta() {
        Printer.println("IDRICHIESTA: " + dettagliRichiesta.getIdRichiesta());
        GestisciPrenotazioniController gestisciPrenotazioniController = new GestisciPrenotazioniController();
        gestisciPrenotazioniController.modificaStatoRichiesta(dettagliRichiesta.getIdRichiesta(), 2);   //rifiuto
        Printer.println("Richiesta rifiutata con successo.");
    }




    @Override
    public void mostraMenu(){

        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle() + " -> Gestisci Richiesta Sovrastante:");

        if (flag == 0) {  // Solo per richieste arrivate
            Printer.println("1. Conferma Richiesta");
            Printer.println("2. Rifiuta Richiesta");
        }


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
