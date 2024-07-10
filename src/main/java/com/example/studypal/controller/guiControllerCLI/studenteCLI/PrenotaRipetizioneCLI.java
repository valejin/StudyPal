package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.studente.PrenotaRipetizioneController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;


public class PrenotaRipetizioneCLI extends AbstractState {

    LoggedInUserBean user;
    RipetizioneInfoBean informazioni;
    RipetizioneInfoBean filtri;

    public PrenotaRipetizioneCLI(LoggedInUserBean user, RipetizioneInfoBean tutor, RipetizioneInfoBean filtri) {
        this.user = user;
        this.informazioni = tutor;
        this.filtri = filtri;
    }
    @Override
    public void action(StateMachineImpl context){

        Scanner scan = new Scanner(System.in);

        //stampo il riepilogo della prenotazione
        Printer.println("   Nome: " + informazioni.getNome());
        Printer.println("   Cognome: " + informazioni.getCognome());
        Printer.println("   Email: " + informazioni.getEmail());
        Printer.println("   Tariffa: " + informazioni.getTariffa() + "€/h");
        Printer.println("   Materia: " + filtri.getMateria());
        Printer.println("   Luogo: " + informazioni.getLuogo());

        if (filtri.getGiorni() != null) {
            Printer.println("   Giorni: " + filtri.getGiorni());
        } else {
            Printer.println("   Giorni: Non specificato");
        }

        if (filtri.getInPresenza() != null && filtri.getOnline() != null) {
            Printer.println("   Modalità: In presenza, online");
        } else if (filtri.getInPresenza() != null) {
            Printer.println("   Modalità: In presenza");
        } else if (filtri.getOnline() != null) {
           Printer.println("    Modalità: Online");
        } else {
            Printer.println("   Modalità: Non specificato");
        }

        Printer.print("   Note aggiuntive (max 250 caratteri): ");
        String note = scan.nextLine();
        if (note.length() > 250) {
            note = note.substring(0, 250);
        }

        int choice;
        mostraMenu();

        while((choice = scan.nextInt()) != 0) {

            try {
                if (choice == 1) {
                        prenota(note);
                        goNext(context, new HomeStudenteCLI(user));
                        break;
                } else{
                        Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                }

            } catch (InputMismatchException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero intero: ");
                scan.nextLine(); // Consuma l'input non valido)
            }
        }

        goBack(context);
    }


    public void prenota(String note) {
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        prenotazioneBean.setNome(informazioni.getNome());
        prenotazioneBean.setCognome(informazioni.getCognome());
        prenotazioneBean.setEmailTutor(informazioni.getEmail());
        prenotazioneBean.setEmailStudente(user.getEmail());
        prenotazioneBean.setMateria(filtri.getMateria());
        prenotazioneBean.setTariffa(informazioni.getTariffa());

        if (filtri.getGiorni() != null) {
            prenotazioneBean.setGiorno(filtri.getGiorni());
        }
        prenotazioneBean.setNote(note);

        PrenotaRipetizioneController prenotaRipetizioneController = new PrenotaRipetizioneController();
        prenotaRipetizioneController.prenota(prenotazioneBean);
        Printer.println("Richiesta inviata con successo!");
    }


    @Override
    public void stampaBenvenuto() {
        Printer.println(" ");
        Printer.printlnBlu("Home studente -> Prenota ripetizione -> Ricerca -> Risultati ricerca -> Prenota");
        Printer.println("Riepilogo della prenotazione:");
        Printer.println("----------------------------------");
    }

    @Override
    public void mostraMenu(){
        Printer.println("----------------------------------");
        Printer.println("   1. Conferma invio della richiesta.");
        Printer.println("   0. Torna indietro.");
        Printer.print("Opzione scelta: ");
    }

    @Override
    public void entry(StateMachineImpl context){
        stampaBenvenuto();
    }
}
