package com.example.studypal;

import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.StateMachine;
import com.example.studypal.pattern.state.StateMachineImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }

    //todo: qui voglio implementare polimorfismo! si può  fare con due classi che specializzano la classe starter

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


        //qui devo mettere uno scanner per capire input messo dall'utente, solo per leggere se uscire
        Scanner scan = new Scanner(System.in);

        //fisso lo stato iniziale dello stateMachine
        StateMachineImpl SM = new StateMachineImpl();

        SM.start();

        do {
            /* option=0 -> exit */
            System.out.println("start state");
            SM.goNext();
            System.out.println("done");
            //qua si aspetta di leggere per continuare ad andare avanti!! Non viene eseguita la action del login
        } while (scan.nextInt() != 0);

    }
}


