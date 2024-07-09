package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RisultatiRicercaCLI extends AbstractState {

    LoggedInUserBean user;
    List<RipetizioneInfoBean> tutorList = new ArrayList<>();
    RipetizioneInfoBean filtri;

    protected RisultatiRicercaCLI(LoggedInUserBean user, List<RipetizioneInfoBean> risultatiRicercaBean, RipetizioneInfoBean ripetizioneInfoBean){
        this.user = user;
        this.tutorList = risultatiRicercaBean;
        this.filtri = ripetizioneInfoBean;
    }

    public RisultatiRicercaCLI() {}

    @Override
    public void action(StateMachineImpl context){

        Scanner scan = new Scanner(System.in);

        int index = 1;
        for (RipetizioneInfoBean tutor : tutorList) {
            Printer.println(index + ". Tutor: " + tutor.getNome() + " " + tutor.getCognome());
            Printer.println("   Tariffa: " + tutor.getTariffa() + "€/h");
            Printer.println("   Giorni: " + tutor.getGiorni());
            //Printer.println("   Email: " + tutor.getEmail());
            //Printer.println("   Insegnamenti offerti: " + tutor.getMaterie());
            Printer.println("----------------------------------");
            index++;
        }
        mostraMenu();


        int choice;

        while((choice = scan.nextInt()) != 0) {

            switch(choice){
                case(1):
                    //visualizza profilo
                    Printer.print("Indice del profilo da visualizzare: ");
                    //devo prendere il tutor
                    RipetizioneInfoBean tutorSelezionato = tutorList.get(scan.nextInt());
                    goNext(context, new ProfiloTutorCLI(user, tutorSelezionato));
                    break;
                case(2):
                    //richiedi tutor
                    Printer.print("Indice del tutor da richiedere: ");
                    RipetizioneInfoBean info = tutorList.get(scan.nextInt());
                    goNext(context, new PrenotaRipetizioneCLI(user, info));
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
        Printer.println("   1. Visualizza un profilo.");
        Printer.println("   2. Richiedi tutor.");
        Printer.println("   0. Torna indietro.");
        Printer.print("Opzione scelta: ");

    }
    @Override
    public void stampaBenvenuto(){
        Printer.println(" ");
        Printer.printlnBlu("Home studente -> Prenota ripetizione -> Ricerca -> Risultati ricerca");
        Printer.println("La ricerca ha prodotto i seguenti risultati:");
    }

    @Override
    public void entry(StateMachineImpl context){
        stampaBenvenuto();
    }

}
