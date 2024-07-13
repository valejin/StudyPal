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

    public GestisciProfiloCLI(LoggedInUserBean user) {
        this.user = user;
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
        String materie = chiediNuoveMaterie(scanner);
        int tariffa = chiediNuovaTariffa(scanner);
        String luogo = chiediNuovoLuogo(scanner);
        boolean inPresenza = chiediModalitaLezione(scanner, "In Presenza");
        boolean online = chiediModalitaLezione(scanner, "Online");
        List<Boolean> days = chiediDisponibilitaGiorni(scanner);

        // Crea il bean e invia al controller applicativo
        RipetizioneInfoBean ripetizioneInfoBean = new RipetizioneInfoBean(materie, inPresenza, online, luogo, days, tariffa, user.getEmail());

        // Stampa i valori a terminale
        stampaRiepilogoModifiche(materie, inPresenza, online, luogo, ripetizioneInfoBean.convertiGiorni(days), tariffa);

        // Invia le modifiche al controller
        GestioneProfiloTutorController gestioneProfiloTutorController = new GestioneProfiloTutorController();
        gestioneProfiloTutorController.gestioneProfiloMethod(ripetizioneInfoBean);

        Printer.println("Modifiche avvenute con successo.");
    }

    private String chiediNuoveMaterie(Scanner scanner) {
        Printer.println("Inserisci nuove materie: ");
        return scanner.nextLine();
    }

    private int chiediNuovaTariffa(Scanner scanner) {
        Printer.println("Inserisci nuova tariffa (in €/h   nota: tariffa max 50): ");
        return scanner.nextInt();
    }

    private String chiediNuovoLuogo(Scanner scanner) {
        scanner.nextLine(); // Consuma newline
        Printer.println("Inserisci luogo (Roma, Milano, Napoli, Palermo, Torino): ");
        return scanner.nextLine();
    }

    private boolean chiediModalitaLezione(Scanner scanner, String modalita) {
        Printer.println("Selezioni modalità di lezione: ");
        while (true) {
            try {
                Printer.println(String.format("%s (T/F): ", modalita));
                return getBooleanInput(scanner);
            } catch (IllegalArgumentException e) {
                Printer.errorPrint("Input non valido. Inserisci 'T' per true o 'F' per false. ");
            }
        }
    }

    private List<Boolean> chiediDisponibilitaGiorni(Scanner scanner) {
        Printer.println("Selezioni giorni disponibili: ");
        List<Boolean> days = new ArrayList<>();
        for (String giorno : new String[]{"Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica"}) {
            while (true) {
                try {
                    Printer.println(giorno + " (T/F): ");
                    days.add(getBooleanInput(scanner));
                    break;
                } catch (IllegalArgumentException e) {
                    Printer.errorPrint("Input non valido. Inserisci 'T' per true o 'F' per false.");
                }
            }
        }
        return days;
    }

    private void stampaRiepilogoModifiche(String materie, boolean inPresenza, boolean online, String luogo, String days, int tariffa) {
        Printer.println(" ");
        Printer.println("Riepilogo modifiche dell'utente: " + user.getEmail());
        Printer.println("    Materie: " + materie);

        if (inPresenza && online) {
            Printer.println("    Modalità: in presenza & online");
        } else if (inPresenza) {
            Printer.println("    Modalità: in Presenza");
        } else if (online) {
            Printer.println("    Modalità: online");
        }

        Printer.println("    Luogo: " + luogo);
        Printer.println("    Giorni: " + days);
        Printer.println("    Tariffa: " + tariffa + "€/h");
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
        return switch (input) {
            case "T" -> true;
            case "F" -> false;
            default -> throw new IllegalArgumentException("Input non valido. Inserisci 'T' per true o 'F' per false.");
        };
    }





}
