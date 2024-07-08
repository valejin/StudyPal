package com.example.studypal;

import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.StateMachineImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;


public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }

    //todo: qui voglio implementare polimorfismo! si può fare con due classi che specializzano la classe starter

    @Override
    public void start(Stage stage) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Printer.println("-------------- StudyPal --------------");
        Printer.println("   1. Interfaccia grafica");
        Printer.println("   2. Command Line Interface (CLI)");
        Printer.print("Scegliere un'opzione: ");
        int scelta = scanner.nextInt();


        switch(scelta){
            case 1:
                interfacciaGrafica(stage);
                break;
            case 2:
                interfacciaCLI();
                break;
            default:
                Printer.println("Scelta non valida.");
                start(stage);
        }

    }

    public void interfacciaGrafica(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("StudyPal");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        Stage secondoStage = new Stage();
        FXMLLoader secondofxmlLoader = new FXMLLoader(Starter.class.getResource("view/login.fxml"));
        Scene secondaScene = new Scene(secondofxmlLoader.load());
        secondoStage.setTitle("StudyPal");
        secondoStage.setResizable(false);
        secondoStage.setScene(secondaScene);
        secondoStage.show();
    }

    public void interfacciaCLI() {

        //todo: se inseriamo qualcosa che non è un int va in errore. Gestire il caso (eccezione?)


        //fisso lo stato iniziale dello stateMachine
        StateMachineImpl context = new StateMachineImpl();

        context.start();

        /*
        if (SM.inEsecuzione){
            System.out.println("SM IN ESECUZIONE");
        } else if (!SM.inEsecuzione) {
            System.out.println("SM NON IN ESECUZIONE");
        }
        */

        while (context.getState() != null) {

            //System.out.println("start state");

            context.goNext(); //viene eseguita l'azione dello stato attuale, che al termine causerà la transizione allo stato successivo

            //System.out.println("done");

        }
    }
}


