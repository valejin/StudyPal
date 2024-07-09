package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import com.example.studypal.controller.guiController.studente.VisualizzaRichiesteStudenteGui;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class VisualizzaRichiesteStudenteCLI extends AbstractState {

    LoggedInUserBean user;
    PrenotazioneBean dettagliRichiesta;
    List<PrenotazioneBean> listRisultati;
    int flag;


    public VisualizzaRichiesteStudenteCLI(LoggedInUserBean user, PrenotazioneBean prenotazioneBean, List<PrenotazioneBean> list, Integer flag) {
        this.user = user;
        this.dettagliRichiesta = prenotazioneBean;
        this.listRisultati = list;
        this.flag = flag;
    }

    @Override
    public void action(StateMachineImpl context) {

        Scanner scan = new Scanner(System.in);

        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle() + " -> Dettagli Richiesta:");

        Printer.println("   Tutor: " + dettagliRichiesta.getNome() + " " + dettagliRichiesta.getCognome());
        Printer.println("   Email: " + dettagliRichiesta.getEmailTutor());
        Printer.println("   Materia: " + dettagliRichiesta.getMateria());
        Printer.println("   Modalità: " + dettagliRichiesta.getModLezione());
        Printer.print("   Giorno: ");

        String giorno;

        giorno = dettagliRichiesta.getGiorno();
        if (giorno != null && !giorno.isEmpty()) {
            Printer.println(giorno);
        } else {
            Printer.println("Qualsiasi");
        }
        Printer.println("   Note aggiuntive: " + dettagliRichiesta.getNote());

        Printer.println("La richiesta è in attesa di conferma.");

        int choice = -1;

        while (choice != 0) {

            mostraMenu();

            try {
                choice = scan.nextInt();
                scan.nextLine(); // Consuma newline

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        cancellaRichiesta();
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida. Riprova.");
                        break;
                }
            } catch (InputMismatchException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scan.nextLine(); // Consuma l'input non valido
            }
        }
    }


    public void cancellaRichiesta(){
        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController(user);
        gestisciPrenotazioniStudenteController.cancellaRichiesta(this.dettagliRichiesta.getIdRichiesta());
        Printer.println("La richiesta è stata cancellata.");
    }


    @Override
    public void mostraMenu(){

        if (flag == 0){
            Printer.println("   1. Cancella richiesta");
        }
        Printer.println("   0. Torna indietro");
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
