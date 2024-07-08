package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.studente.CercaRipetizioneController;
import com.example.studypal.controller.applicationController.studente.PrenotaRipetizioneController;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PrenotaRipetizioneCLI extends AbstractState {

    private String materia;
    private Integer tariffa;
    private String luoghi;
    private Boolean inPresenze;
    private Boolean onlinee;
    private String giorni;

    LoggedInUserBean user;
    private RipetizioneInfoBean ripetizioneInfoBean;
    private List<RipetizioneInfoBean> risultatiRicercaBean = new ArrayList<>();

    public PrenotaRipetizioneCLI(LoggedInUserBean user){ this.user = user;}

    @Override
    public void action(StateMachineImpl context){
        Scanner scanner = new Scanner(System.in);
        Printer.println("Inserisci la materia:");
        materia = scanner.nextLine();

        Printer.println("Vuoi lezioni in presenza? (sì/no):");
        inPresenze = "sì".equalsIgnoreCase(scanner.nextLine());

        Printer.println("Vuoi lezioni online? (sì/no):");
        onlinee = "sì".equalsIgnoreCase(scanner.nextLine());

        if (Boolean.TRUE.equals(inPresenze)) {
            Printer.println("Inserisci il luogo (Roma, Milano, Palermo, Torino, Napoli):");
            luoghi = scanner.nextLine();
        }

        Printer.println("Inserisci la tariffa massima (5-50):");
        tariffa = Integer.parseInt(scanner.nextLine());

        Printer.println("Seleziona i giorni disponibili (Lunedì, Martedì, Mercoledì, Giovedì, Venerdì, Sabato, Domenica):");
        giorni = scanner.nextLine(); // Il formato dei giorni può essere elaborato successivamente.

        ricercaMethod();
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
        Printer.println("La materia inserita è: " + materia);

        BaseInfoBean baseInfoBean = new BaseInfoBean(materia);
        CercaRipetizioneController cercaRipetizioneController = new CercaRipetizioneController();
        risultatiRicercaBean = cercaRipetizioneController.prenotaRipetizioneMethod(baseInfoBean);

        return risultatiRicercaBean;
    }

    public List<RipetizioneInfoBean> ricercaConFiltri() {
        String email = this.user.getEmail();
        List<Boolean> giorniList = parseGiorni(giorni);

        ripetizioneInfoBean = new RipetizioneInfoBean(materia, inPresenze, onlinee, luoghi, giorniList, tariffa, email);
        CercaRipetizioneController cercaRipetizioneController = new CercaRipetizioneController();
        risultatiRicercaBean = cercaRipetizioneController.prenotaRipetizioneMethod(ripetizioneInfoBean);

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
    public void mostraMenu(){
        Printer.println(" ");
        Printer.print("Home Studente -> ");
        Printer.print("PrenotaRipetizione -> ");
        Printer.println("Ricerca");
        Printer.println("---------------------- RICERCA ---------------------");
    }

    @Override
    public void entry(StateMachineImpl context){
        mostraMenu();
    }
}
