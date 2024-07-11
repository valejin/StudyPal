package com.example.studypal.view.second.gui.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.application.tutor.GestioneProfiloTutorController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GestisciProfiloCLI extends AbstractState {
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

        int choice =-1;

        while(choice != 0) {

            mostraMenu();

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consuma newline

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        visualizzaProfilo();
                        break;
                    case 2:
                        modificaProfilo(scanner);
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida. Riprova: ");
                        break;
                }
            }catch (InputMismatchException e){
                Printer.errorPrint("Input non valido. Inserisci un numero intero.");
                scanner.nextLine(); // Consuma l'input non valido
            }
        }
        goBack(context);

    }


    private void visualizzaProfilo() {
        Printer.println(" ");
        Printer.printlnBlu("Home Tutor -> Gestisci Profilo -> Dettagli Profilo:");
        Printer.println("Nome: " + user.getNome());
        Printer.println("Cognome: " + user.getCognome());
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

        boolean inPresenza;
        boolean online;

        Printer.println("Selezioni modalità di lezione: ");
        while (true) {
            try {
                Printer.println("In Presenza (T/F): ");
                inPresenza = getBooleanInput(scanner);
                break;
            } catch (IllegalArgumentException e) {
                Printer.errorPrint("Input non valido. Inserisci 'T' per true o 'F' per false.");
            }
        }

        while (true) {
            try {
                Printer.println("Online (T/F): ");
                online = getBooleanInput(scanner);
                break;
            } catch (IllegalArgumentException e) {
                Printer.errorPrint("Input non valido. Inserisci 'T' per true o 'F' per false.");
            }
        }


        Printer.println("Selezioni giorni disponibili: ");
        List<Boolean> giorni = new ArrayList<>();
        for (String giorno : new String[]{"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"}) {
            while (true) {
                try {
                    Printer.println(giorno + " (T/F): ");
                    giorni.add(getBooleanInput(scanner));
                    break;
                } catch (IllegalArgumentException e) {
                    Printer.errorPrint("Input non valido. Inserisci 'T' per true o 'F' per false.");
                }
            }
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
        Printer.printlnBlu("Home Tutor -> Gestione Profilo:");
        Printer.println("1. Visualizza Profilo");
        Printer.println("2. Modifica Profilo");
        Printer.println("0. Torna Indietro");
        Printer.print("Scegli un'opzione: ");

    }


    private boolean getBooleanInput(Scanner scanner) {
        String input = scanner.nextLine().trim().toUpperCase();
        switch (input) {
            case "T":
                return true;
            case "F":
                return false;
            default:
                throw new IllegalArgumentException("Input non valido. Inserisci 'T' per true o 'F' per false.");
        }
    }





}
