package com.example.studypal.view.second.gui.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;
import java.util.List;
import java.util.Scanner;

public class RisultatiRicercaCLI extends AbstractState {

    LoggedInUserBean user;
    List<RipetizioneInfoBean> tutorList;
    RipetizioneInfoBean filtri;

    protected RisultatiRicercaCLI(LoggedInUserBean user, List<RipetizioneInfoBean> risultatiRicercaBean, RipetizioneInfoBean ripetizioneInfoBean){
        this.user = user;
        this.tutorList = risultatiRicercaBean;
        this.filtri = ripetizioneInfoBean;
    }

    @Override
    public void action(StateMachineImpl context){

        Scanner scan = new Scanner(System.in);

        if (tutorList.isEmpty()){
            Printer.println("La ricerca non ha prodotto risultati.");
            //riportiamo alla pagina di ricerca? alla home? menu 1. nuova ricerca 0. torna indietro
        } else {
            Printer.println("La ricerca ha prodotto i seguenti risultati:");
            Printer.println(" ");
            Printer.println("----------------------------------");
        }

        int index = 1;
        for (RipetizioneInfoBean tutor : tutorList) {
            Printer.println(index + ". Tutor: " + tutor.getNome() + " " + tutor.getCognome());
            Printer.println("   Tariffa: " + tutor.getTariffa() + "€/h");
            Printer.println("   Giorni: " + tutor.getGiorni());
            Printer.println("----------------------------------");
            index++;
        }
        mostraMenu();


        int choice;
        int secondChoice;
        while((choice = scan.nextInt()) != 0) {

            switch(choice){
                case(1):
                    //visualizza profilo
                    Printer.print("Indice del profilo da visualizzare: ");

                    secondChoice = scan.nextInt();
                    if (secondChoice < tutorList.size()){
                        RipetizioneInfoBean tutorSelezionato = tutorList.get(secondChoice - 1);
                        stampaInfoTutor(tutorSelezionato);
                    } else {
                        Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili:  ");
                    }
                    break;

                case(2):
                    //richiedi tutor
                    Printer.print("Indice del tutor da richiedere: ");
                    secondChoice = scan.nextInt();
                    if (secondChoice < (tutorList.size() + 1)){
                        RipetizioneInfoBean info = tutorList.get(secondChoice - 1);
                        goNext(context, new PrenotaRipetizioneCLI(user, info, filtri));
                    } else {
                        Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                    }
                    break;
                case(3):
                    goNext(context, new HomeStudenteCLI(user));
                    break;
                default:
                    Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                    break;
            }
        }

        goBack(context);

    }
    @Override
    public void mostraMenu(){
        Printer.println(" ");
        Printer.printlnBlu("Home studente -> Prenota ripetizione -> Ricerca -> Risultati ricerca");
        Printer.println("   1. Visualizza i dettagli di un profilo.");
        Printer.println("   2. Richiedi uno dei tutor.");
        Printer.println("   3. Home.");
        Printer.println("   0. Torna indietro.");
        Printer.print("Opzione scelta: ");

    }
    @Override
    public void stampaBenvenuto(){
        Printer.println(" ");
        Printer.printlnBlu("Home studente -> Prenota ripetizione -> Ricerca -> Risultati ricerca");
    }

    @Override
    public void entry(StateMachineImpl context){
        stampaBenvenuto();
    }

    public void stampaInfoTutor(RipetizioneInfoBean tutorSelezionato){
        Printer.println("   Nome: " + tutorSelezionato.getNome());
        Printer.println("   Cognome: " + tutorSelezionato.getCognome());
        Printer.println("   Email: " + tutorSelezionato.getEmail());
        Printer.println("   Materie: " + tutorSelezionato.getMateria());
        Printer.println("   Giorni Disponibili: " + tutorSelezionato.getGiorni());
        Printer.println("   Tariffa: " + tutorSelezionato.getTariffa() + "€/h");

    }

    public void traduciBool(){

    }

}
