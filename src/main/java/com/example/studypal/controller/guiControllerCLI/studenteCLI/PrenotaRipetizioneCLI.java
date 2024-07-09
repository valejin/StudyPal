package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.Scanner;


public class PrenotaRipetizioneCLI extends AbstractState {

    LoggedInUserBean user;
    RipetizioneInfoBean informazioni, filtri;

    public PrenotaRipetizioneCLI(LoggedInUserBean user, RipetizioneInfoBean tutor, RipetizioneInfoBean filtri) {
        this.user = user;
        this.informazioni = tutor;
        this.filtri = filtri;
    }
    @Override
    public void action(StateMachineImpl context){

        Scanner scan = new Scanner(System.in);

        mostraMenu();

        //stampo il riepilogo della prenotazione
        Printer.println("Nome: " + informazioni.getNome());
        Printer.println("Cognome: " + informazioni.getCognome());
        Printer.println("Email: " + informazioni.getEmail());
        Printer.println("Tariffa: " + informazioni.getTariffa() + "€/h");
        Printer.println("Materia: " + filtri.getMateria());
        Printer.println("Luogo: " + informazioni.getLuogo());

        if (filtri.getGiorni() != null) {
            Printer.println("Giorni: " + filtri.getGiorni());
        } else {
            Printer.println("Giorni: Non specificato");
        }

        if (filtri.getInPresenza() != null && filtri.getOnline() != null) {
            Printer.println("Modalità: In presenza, online");
        } else if (filtri.getInPresenza() != null) {
            Printer.println("Modalità: In presenza");
        } else if (filtri.getOnline() != null) {
           Printer.println("Modalità: Online");
        } else {
            Printer.println("Modalità: Non specificato");
        }

        Printer.println("Note (max 250 caratteri): ");
        String note = scan.nextLine();
        if (note.length() > 250) {
            note = note.substring(0, 250);
        }


    }

    @Override
    public void stampaBenvenuto() {
        Printer.println(" ");
        Printer.printlnBlu("Home studente -> Prenota ripetizione -> Ricerca -> Risultati ricerca -> Prenota");
        Printer.println("Riepilogo della prenotazione:");
    }

    @Override
    public void mostraMenu(){
        Printer.println("   1. Invia");
        Printer.println("   0. Torna indietro.");
        Printer.print("Opzione scelta: ");
    }

    @Override
    public void entry(StateMachineImpl context){
        stampaBenvenuto();
    }
}
