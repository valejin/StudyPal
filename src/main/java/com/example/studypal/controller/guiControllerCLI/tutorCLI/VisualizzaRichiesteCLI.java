package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.tutor.GestisciPrenotazioniController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class VisualizzaRichiesteCLI extends AbstractState {

    private final LoggedInUserBean user;
    private final PrenotazioneBean dettagliRichiesta;
    private final List<PrenotazioneBean> listRisultati;

    public VisualizzaRichiesteCLI(LoggedInUserBean user, PrenotazioneBean prenotazioneBean, List<PrenotazioneBean> list) {
        this.user = user;
        this.dettagliRichiesta = prenotazioneBean;
        this.listRisultati = list;
    }



    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);

        Printer.println(" ");
        Printer.println("Dettagli Richiesta:");
        Printer.println("Email Studente: " + dettagliRichiesta.getEmailStudente());
        Printer.println("Materia: " + dettagliRichiesta.getMateria());
        Printer.println("Modalità Lezione: " + getModalitaLezione(dettagliRichiesta.getModLezione()));
        Printer.println("Giorni: " + (dettagliRichiesta.getGiorno() != null && !dettagliRichiesta.getGiorno().isEmpty() ? dettagliRichiesta.getGiorno() : "Non specificato"));
        Printer.println("Note: " + (dettagliRichiesta.getNote() != null && !dettagliRichiesta.getNote().isEmpty() ? "\"" + dettagliRichiesta.getNote() + "\"" : "Nessuna nota"));

        mostraMenu();


        int choice;

        while ((choice = scanner.nextInt()) != 0) {

            scanner.nextLine(); // Consuma newline

            switch (choice) {
                case 1:
                    confermaRichiesta();
                    mostraMenu();
                    break;
                case 2:
                    rifiutaRichiesta();
                    mostraMenu();
                    break;

                default:
                    Printer.println("Scelta non valida. Riprova.");
                    break;
            }
        }

        goBack(context);   //ritorno allo stato precedente
    }




    private String getModalitaLezione(int modalita) {
        switch (modalita) {
            case 1:
                return "In presenza";
            case 2:
                return "Online";
            default:
                return "Non specificato";
        }
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
        Printer.println("Home Tutor -> Gestisci Prenotazioni -> Richieste Arrivate -> ");
        Printer.println("1. Conferma Richiesta");
        Printer.println("2. Rifiuta Richiesta");
        Printer.println("0. Torna Indietro");
        Printer.print("Scegli un'opzione: ");
    }








}
