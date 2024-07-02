package com.example.studypal;

import com.example.studypal.controller.guiControllerCLI.LoginCLI;
import com.example.studypal.other.Printer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

public class Starter extends Application {

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
    }

    public void interfacciaCLI(){
        LoginCLI loginCLI = new LoginCLI();
        loginCLI.start();
    }

    public static void main(String[] args) {
        launch();
    }
}