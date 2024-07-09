package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import com.example.studypal.controller.applicationController.tutor.GestisciPrenotazioniController;
import com.example.studypal.controller.guiControllerCLI.tutorCLI.VisualizzaRichiesteCLI;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RichiesteStudenteCLI extends AbstractState {

    private final LoggedInUserBean user;
    private final Integer flag;
    private List<PrenotazioneBean> richiesteList;

    Scanner scanner = new Scanner(System.in);


    public RichiesteStudenteCLI(LoggedInUserBean user, Integer flag) {
        this.user = user;
        this.flag = flag;
    }


    @Override
    public void action(StateMachineImpl context) {

        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle());

        // Creo un'istanza del controller applicativo corrispondente
        GestisciPrenotazioniStudenteController gestisciPrenotazioniController = new GestisciPrenotazioniStudenteController();

        // Chiamo la funzione nel controller applicativo per ottenere una lista di BEAN che contiene tutte le info per la tabella
        richiesteList = gestisciPrenotazioniController.richiesteInviate(user.getEmail(), flag);

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
                goNext(context, new VisualizzaRichiesteStudenteCLI(user, prenotazioneSelezionata, richiesteList, flag)); // Transizione a VisualizzaRichiesteCLI
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
        return switch (flag) {
            case 0 -> "Home Studente -> Gestisci Prenotazioni -> Richieste Inviate";
            case 1 -> "Home Studente -> Gestisci Prenotazioni -> Prenotazioni Attive";
            case 2 -> "Home Studente -> Gestisci Prenotazioni -> Richieste Rifiutate";
            default -> "Home Studente -> Gestisci Prenotazioni";
        };
    }

}
