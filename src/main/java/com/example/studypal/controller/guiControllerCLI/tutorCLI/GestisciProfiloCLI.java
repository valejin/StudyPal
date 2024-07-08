package com.example.studypal.controller.guiControllerCLI.tutorCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.tutor.GestioneProfiloTutorController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class GestisciProfiloCLI extends AbstractState {
    private static final Logger logger = Logger.getLogger(GestisciProfiloCLI.class.getName());
    private final LoggedInUserBean user;
    private RipetizioneInfoBean infoCorrentiProfilo;
    private final List<Boolean> giorni;

    public GestisciProfiloCLI(LoggedInUserBean user) {
        this.user = user;
        this.giorni = new ArrayList<>();
    }



    @Override
    public void action(StateMachineImpl context) {

        Scanner scanner = new Scanner(System.in);

        this.infoCorrentiProfilo = caricaInformazioniProfilo(user.getEmail());

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consuma newline

        while(choice != 0) {

            switch (choice) {
                case 1:
                    visualizzaProfilo();
                    mostraMenu();
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consuma newline
                    break;
                case 2:
                    modificaProfilo(scanner);
                    mostraMenu();
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consuma newline
                    break;

                default:
                    Printer.println("Scelta non valida. Riprova: ");
                    break;
            }
        }
        goBack(context);
    }


    private void visualizzaProfilo() {
        Printer.println(" ");
        Printer.print("Home Tutor -> Gestisci Profilo -> ");
        Printer.println("Dettagli Profilo:");
        Printer.println("Nome: " + user.getNome());
        Printer.println("Email: " + user.getEmail());
        Printer.println("Ruolo: " + (user.getRuolo() ? "Tutor" : "Studente"));
        Printer.println("In Presenza: " + infoCorrentiProfilo.getInPresenza());
        Printer.println("Online: " + infoCorrentiProfilo.getOnline());
        Printer.println("Luogo: " + infoCorrentiProfilo.getLuogo());
        Printer.println("Materia: " + infoCorrentiProfilo.getMateria());
        Printer.println("Tariffa: " + infoCorrentiProfilo.getTariffa() + "€");
        Printer.println("Giorni: " + infoCorrentiProfilo.getGiorni());
    }



    private void modificaProfilo(Scanner scanner) {
        Printer.println("Inserisci nuove materie: ");
        String materie = scanner.nextLine();

        Printer.println("Inserisci nuova tariffa (in €): ");
        int tariffa = scanner.nextInt();
        scanner.nextLine(); // Consuma newline

        Printer.println("Inserisci luogo (Roma, Milano, Napoli, Palermo, Torino): ");
        String luogo = scanner.nextLine();

        Printer.println("In Presenza (true/false): ");
        boolean inPresenza = scanner.nextBoolean();

        Printer.println("Online (true/false): ");
        boolean online = scanner.nextBoolean();

        scanner.nextLine(); // Consuma newline

        List<Boolean> giorni = new ArrayList<>();
        for (String giorno : new String[]{"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"}) {
            Printer.println(giorno + " (true/false): ");
            giorni.add(scanner.nextBoolean());
        }

        // Crea il bean e invia al controller applicativo
        RipetizioneInfoBean ripetizioneInfoBean = new RipetizioneInfoBean(materie, inPresenza, online, luogo, giorni, tariffa, user.getEmail());

        // Stampa i valori a terminale
        Printer.println(" ");
        Printer.println("Riepilogo modifiche dell'utente: " + user.getEmail());
        Printer.println("    Materie: " + materie);

        if(inPresenza == true && online == true){
            Printer.println("    Modalità: in presenza & online");
        } else if (inPresenza == true) {
            Printer.println("    Modalità: in Presenza");
        } else if (online == true) {
            Printer.println("    Modalità: online");
        }

        Printer.println("    Luogo: " + luogo);
        Printer.println("    Giorni: " + ripetizioneInfoBean.getGiorni());
        Printer.println("    Tariffa: " + tariffa + "€/h");

        GestioneProfiloTutorController gestioneProfiloTutorController = new GestioneProfiloTutorController();
        gestioneProfiloTutorController.gestioneProfiloMethod(ripetizioneInfoBean);

        Printer.println("Modifiche avvenute con successo.");
    }


    private RipetizioneInfoBean caricaInformazioniProfilo(String email) {
        GestioneProfiloTutorController gestioneProfiloTutorController = new GestioneProfiloTutorController();
        return gestioneProfiloTutorController.caricaInformazioniProfilo(email);
    }


    @Override
    public void mostraMenu(){

        Printer.println(" ");
        Printer.print("Home Tutor -> ");
        Printer.println("Gestione Profilo:");
        Printer.println("1. Visualizza Profilo");
        Printer.println("2. Modifica Profilo");
        Printer.println("0. Torna Indietro");
        Printer.print("Scegli un'opzione: ");

    }

    @Override
    public void entry(StateMachineImpl context){
        mostraMenu();
    }

}
