package com.example.studypal.view.second.gui.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.InitialState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProfiloTutorCLI extends AbstractState {

    LoggedInUserBean user;
    RipetizioneInfoBean tutor;

    public ProfiloTutorCLI(LoggedInUserBean user, RipetizioneInfoBean tutorSelezionato){
        this.user = user;
        this.tutor = tutorSelezionato;
    }

    @Override
    public void action(StateMachineImpl context){
        Printer.errorPrint("DA IMPLEMENTARE (0 per tornare indietro)");
        Scanner scan = new Scanner(System.in);
        int choice;

        while((choice = scan.nextInt()) != 0) {

            try{
                switch(choice){
                    case(1):
                        goNext(context, new CercaRipetizioneCLI(user));
                        break;
                    case(2):
                        goNext(context, new GestisciPrenotazioniStudenteCLI(user));
                        break;
                    default:
                        Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                        break;
                }
            } catch (InputMismatchException e){
                Printer.errorPrint("Input non valido. Per favore, inserisci un numero intero: ");
                scan.nextLine(); // Consuma l'input non valido)
            }
        }

        goNext(context, new InitialState());

    }
}
