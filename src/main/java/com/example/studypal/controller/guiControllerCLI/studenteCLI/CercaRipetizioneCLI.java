package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.studente.PrenotaRipetizioneController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.*;

public class CercaRipetizioneCLI extends AbstractState {

    private String materia;
    private Integer tariffa;
    private String luoghi;
    private Boolean inPresenze;
    private Boolean onlinee;
    private String giorni;

    LoggedInUserBean user;
    private RipetizioneInfoBean ripetizioneInfoBean;
    private List<RipetizioneInfoBean> risultatiRicercaBean = new ArrayList<>();

    public CercaRipetizioneCLI(LoggedInUserBean user){ this.user = user;}
    Scanner scanner = new Scanner(System.in);
    @Override
    public void action(StateMachineImpl context){



        while (true) {
            Printer.print("Inserisci la materia: ");
            materia = scanner.nextLine();
            if (!materia.trim().isEmpty()) {
                break;
            } else {
                Printer.errorPrint("Campo obbligatorio.");
            }
        }

        String metodo;

        boolean tryAgain = true;
        while(tryAgain){

            while (true) {
                Printer.print("Desideri inserire filtri aggiuntivi? [si/no]: ");

                metodo = scanner.nextLine();
                if (!metodo.trim().isEmpty()) {
                    break;
                } else {
                    Printer.errorPrint("Campo obbligatorio.");
                }
            }

            switch (metodo){
                case("no"):
                    ricercaMateria();
                    tryAgain = false;
                    break;
                case("si"):
                    ricercaConFiltri();
                    tryAgain = false;
                    break;
                default:
                    Printer.errorPrint("Input invalido.");
                    break;
            }
        }

        goNext(context, new RisultatiRicercaCLI(user, risultatiRicercaBean, ripetizioneInfoBean));

    }


    public void ricercaMethod() {
        if (materia == null || materia.isEmpty()) {
            Printer.println("Non hai inserito la materia");
        } else {
            if (Boolean.TRUE.equals(tariffa == 50 && !inPresenze && !onlinee && (luoghi == null || luoghi.isEmpty())) && Boolean.TRUE.equals(giorniVuoti(giorni))) {
                risultatiRicercaBean = ricercaMateria();
                //caricaRisultati(risultatiRicercaBean);    QUI NUOVO STATO?
                Printer.println("Ricerca completata.");
            } else if (Boolean.TRUE.equals(inPresenze) && (luoghi == null || luoghi.isEmpty())) {
                Printer.println("Inserire un luogo");
            } else {
                risultatiRicercaBean = ricercaConFiltri();
                //caricaRisultati(risultatiRicercaBean);    QUI NUOVO STATO?
                Printer.println("Ricerca completata!");
            }
        }
    }

    public List<RipetizioneInfoBean> ricercaMateria() {

        ripetizioneInfoBean = new RipetizioneInfoBean(materia);

        BaseInfoBean baseInfoBean = new BaseInfoBean(materia);
        PrenotaRipetizioneController cercaRipetizioneController = new PrenotaRipetizioneController();
        risultatiRicercaBean = cercaRipetizioneController.ricercaMethod(baseInfoBean);

        return risultatiRicercaBean;
    }

    public List<RipetizioneInfoBean> ricercaConFiltri() {

        Printer.println("Vuoi lezioni in presenza? (si/no):");
        inPresenze = "sì".equalsIgnoreCase(scanner.nextLine());

        Printer.println("Vuoi lezioni online? (si/no):");
        onlinee = "sì".equalsIgnoreCase(scanner.nextLine());

        if (Boolean.TRUE.equals(inPresenze)) {
            Printer.println("Inserisci il luogo (Roma, Milano, Palermo, Torino, Napoli):");
            luoghi = scanner.nextLine();
        }

        Printer.println("Inserisci la tariffa massima (5-50):");
        tariffa = Integer.parseInt(scanner.nextLine());

        Printer.println("Seleziona i giorni disponibili (Lunedì, Martedì, Mercoledì, Giovedì, Venerdì, Sabato, Domenica):");
        giorni = scanner.nextLine(); // Il formato dei giorni può essere elaborato successivamente.



        String email = this.user.getEmail();
        List<Boolean> giorniList = parseGiorni(giorni);

        ripetizioneInfoBean = new RipetizioneInfoBean(materia, inPresenze, onlinee, luoghi, giorniList, tariffa, email);
        PrenotaRipetizioneController prenotaRipetizioneController = new PrenotaRipetizioneController();
        risultatiRicercaBean = prenotaRipetizioneController.ricercaMethod(ripetizioneInfoBean);

        return risultatiRicercaBean;
    }

    public List<Boolean> parseGiorni(String giorni) {
        List<Boolean> giorniList = new ArrayList<>(Collections.nCopies(7, false));
        if (giorni.contains("Lunedì")) giorniList.set(0, true);
        if (giorni.contains("Martedì")) giorniList.set(1, true);
        if (giorni.contains("Mercoledì")) giorniList.set(2, true);
        if (giorni.contains("Giovedì")) giorniList.set(3, true);
        if (giorni.contains("Venerdì")) giorniList.set(4, true);
        if (giorni.contains("Sabato")) giorniList.set(5, true);
        if (giorni.contains("Domenica")) giorniList.set(6, true);
        return giorniList;
    }

    public Boolean giorniVuoti(String giorni) {
        return giorni == null || giorni.isEmpty();
    }

    @Override
    public void stampaBenvenuto(){
        Printer.println(" ");
        Printer.printBlu("Home Studente -> ");
        Printer.printBlu("PrenotaRipetizione -> ");
        Printer.printlnBlu("Ricerca");
        Printer.printlnBlu("---------------------- RICERCA ---------------------");
    }

    @Override
    public void entry(StateMachineImpl context){
        stampaBenvenuto();
    }
}
