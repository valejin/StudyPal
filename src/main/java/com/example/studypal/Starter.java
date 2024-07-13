package com.example.studypal;

import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.StateMachineImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Starter extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while(!validInput) {

            try {
                mostraMenu();

                int scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma newline


                switch (scelta) {
                    case 1:
                        interfacciaGrafica(stage);
                        validInput = true;
                        break;
                    case 2:
                        interfacciaCLI();
                        validInput = true;
                        break;
                    default:
                        Printer.println("Scelta non valida.");
                        break;
                }
            }catch(InputMismatchException e){
                Printer.errorPrint("Input non valido. Per favore, riprova.");
                scanner.nextLine(); // Consuma l'input non valido
            }

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

        StateMachineImpl context = new StateMachineImpl();

        while (context.getState() != null) {
            context.goNext();
        }
        Printer.println("Applicazione terminata. Arrivederci!");
    }


    public void mostraMenu(){
        Printer.println(" ");
        Printer.printlnBlu("-------------- StudyPal --------------");
        Printer.println("   1. Interfaccia grafica");
        Printer.println("   2. Command Line Interface (CLI)");
        Printer.print("Scegliere un'opzione: ");
    }











}









