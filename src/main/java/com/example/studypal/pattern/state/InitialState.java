package com.example.studypal.pattern.state;

import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.controller.guiControllerCLI.RegistrazioneCLI;
import com.example.studypal.other.Printer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InitialState extends AbstractState {

    /* stato iniziale della StateMachine*/


    @Override
    public void action(StateMachineImpl context){

        AbstractState nextState;

        Scanner scan = new Scanner(System.in);
        boolean validInput = false;
        int scelta;

        while((context.getState() != null )){

            try {
                this.stampaBenvenuto();
                this.mostraMenu();
                scelta = scan.nextInt();
                switch (scelta) {
                    case (0):
                        context.setState();
                        return;
                    case (1):
                        nextState = new LoginCLI();
                        goNext(context, nextState);  //questo fa solo camiare lo stato corrente, poi deve essere il client (Starter) a far avanzare la macchina a stati
                        break;
                    case (2):
                        //registrazione, effettuo il metodo goNext per cambiare la pagina (transizione)
                        nextState = new RegistrazioneCLI();
                        goNext(context, nextState);
                        break;
                    default:
                        Printer.errorPrint("Input invalido. Scegliere un'opzione tra quelle disponibili. ");
                        break;
                }
            } catch(InputMismatchException e){
                Printer.errorPrint("Input non valido. Per favore, inserisci un numero intero.");
                scan.nextLine(); // Consuma l'input non valido
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
        Printer.println(" ");
        Printer.printlnBlu("--------------Benvenuto a StudyPal!--------------");
        Printer.println("E' necessario avere un account per continuare.");
    }

    @Override
    public void exit(StateMachineImpl stateMachine){

    }

}
