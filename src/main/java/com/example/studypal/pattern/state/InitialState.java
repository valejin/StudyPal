package com.example.studypal.pattern.state;

import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.controller.guiControllerCLI.RegistrazioneCLI;
import com.example.studypal.other.Printer;

import java.util.Scanner;

public class InitialState extends AbstractState {

    /* stato iniziale della StateMachine*/


    @Override
    public void action(StateMachineImpl context){

        AbstractState nextState;
        this.stampaBenvenuto();
        this.mostraMenu();

        Scanner scan = new Scanner(System.in);
        int scelta;

        while((context.getState() != null && (scelta = scan.nextInt()) < 3) ) {
            switch(scelta){
                case(0):
                    Printer.println("Arrivederci!");
                    context.setState();
                    return;
                case(1):
                    nextState = new LoginCLI();
                    goNext(context, nextState);  //questo fa solo camiare lo stato corrente, poi deve essere il client (Starter) a far avanzare la macchina a stati
                    break;
                case(2):
                    //registrazione, effettuo il metodo goNext per cambiare la pagina (transizione)
                    nextState = new RegistrazioneCLI();
                    goNext(context, nextState);
                    break;

                default:
                    Printer.print("Input invalido. Scegliere un'opzione tra quelle disponibili: ");
                    return;
            }
        }
    }

    @Override
    public void mostraMenu(){
        Printer.println("   1. Login");
        Printer.println("   2. Registrazione");
        Printer.println("   0. Esci");
        Printer.print("Opzione scelta: ");
    }

    @Override
    public void stampaBenvenuto(){
        Printer.println("--------------Benvenuto a StudyPal!--------------");
        Printer.println("E' necessario avere un account per continuare.");
    }

    @Override
    public void exit(StateMachineImpl stateMachine){
        System.out.println("uscendo da initial state");
    }

}
