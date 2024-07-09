package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.tutor.GestisciPrenotazioniController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.observer.RichiesteArrivateCollection;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class RichiesteTutorCLI extends AbstractState {

    private final LoggedInUserBean user;
    private final Integer flag;
    private List<PrenotazioneBean> richiesteList;



    public RichiesteTutorCLI(LoggedInUserBean user, Integer flag) {
        this.user = user;
        this.flag = flag;
    }


    @Override
    public void action(StateMachineImpl context) {
        Scanner scanner = new Scanner(System.in);

        int choice;

        while ((choice = scanner.nextInt()) != 0) {
            scanner.nextLine(); // Consuma newline

            if(choice == 1) {
                visualizzaRichieste(context);
                mostraMenu();
                break;
            }else {
                Printer.println("Scelta non valida. Riprova.");
                break;
            }
        }
        goBack(context);   //torno allo stato precedente

    }


    public void visualizzaRichieste(StateMachineImpl context) {

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

            Printer.print("Seleziona una richiesta da visualizzare (0 per tornare indietro): ");
            int scelta = new Scanner(System.in).nextInt();

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

    @Override
    public void entry(StateMachineImpl context){
        mostraMenu();
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





