package com.example.studypal.pattern.state;

import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.controller.guiControllerCLI.RegistrazioneCLI;
import com.example.studypal.other.Printer;

import java.util.Scanner;

public class InitialState extends AbstractState {

    /* stato iniziale della StateMachine*/


    @Override
    public void action(StateMachineImpl SM){

        AbstractState nextState;
        this.stampaBenvenuto();
        this.mostraMenu();

        Scanner scan = new Scanner(System.in);

        int scelta = scan.nextInt();

        switch(scelta){
            case(0):
                //in realtà questo non è necessario perché si blocca starter direttamente
                Printer.println("Arrivederci!");
                break;

            case(1):
                //vado allo stato di login
                nextState = new LoginCLI();
                goNext(SM, nextState);
                break;
            case(2):
                //registrazione, effettuo il metodo goNext per cambiare la pagina (transizione)
                RegistrazioneCLI registrazioneCLI = new RegistrazioneCLI();
                goNext(SM, registrazioneCLI);
                break;

            default:
                Printer.println("Input invalido."); //todo sistemare perché così non richiediamo l'input all'utente
                break;
        }

    }

    @Override
    public void mostraMenu(){
        Printer.println("   1. Login");
        Printer.println("   2. Registrazione");
        Printer.println("   0. Esci");
        Printer.print("Opzione scelta: ");
    }

    public void stampaBenvenuto(){
        Printer.println("--------------Benvenuto a StudyPal!--------------");
        Printer.println("E' necessario avere un account per continuare.");
    }

    @Override
    public void exit(StateMachineImpl stateMachine){
        System.out.println("uscendo dallo stato iniziale");
    }

}
