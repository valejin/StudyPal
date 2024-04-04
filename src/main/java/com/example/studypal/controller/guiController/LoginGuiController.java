package com.example.studypal.controller.guiController;

import com.example.studypal.bean.CredenzialiBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.applicationController.LoginController;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginGuiController {


    private String loginPage = "com/example/studypal/view/login.fxml";

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text signUpButton;

    @FXML
    private Label credenzialiError;

    @FXML
    private Label credenzialiSbagliate;


    @FXML
     void loginMethod() throws IOException {
        //String userEmail = this.emailField.getText();
        //String userPassword = this.passwordField.getText();
        String userEmail = null;
        String userPassword = null;


        //poi controlla isempty()
        if (!this.emailField.getText().isEmpty() && !this.passwordField.getText().isEmpty()) {
            userEmail = this.emailField.getText();
            userPassword = this.passwordField.getText();
        } else {
            credenzialiError.setText("Campi obbligatori.");
            return;
        }


        try {

            CredenzialiBean credenzialiBean = new CredenzialiBean();
            credenzialiBean.setEmail(userEmail);
            credenzialiBean.setPassword(userPassword);

            //istanziamo il controller applicativo che si deve occupare del login e gli passiamo il bean contenente le credenziali
            LoginController loginController = new LoginController();

            //prendiamo i dati dell'utente loggato (sessione)
            LoggedInUserBean loggedInUserBean = loginController.loginMethod(credenzialiBean);

            //in base al ruolo dell'utente loggato carichiamo la pagina corretta della home
            System.out.println("siamo:" + loggedInUserBean.getRuolo());

            caricaHome(loggedInUserBean.getRuolo());

        } catch (CredenzialiSbagliateException e) {
            credenzialiSbagliate.setText("Credenziali sbagliate.");

        }

    }

    //cambio pagina
    public void caricaRegistrazione () {
        try {
            FXMLLoader loader = new FXMLLoader(LoginGuiController.class.getResource("/com/example/studypal/view/registrazione.fxml"));
            loader.setControllerFactory(c -> new RegistrazioneGuiController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) credenzialiError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //metodo che carica la home corretta in base al ruolo
    public void caricaHome(boolean isTutor) {

        try {
            FXMLLoader loader;

            if (isTutor) {
                System.out.println("siamo:" + isTutor);
                loader = new FXMLLoader(LoginGuiController.class.getResource("/com/example/studypal/view/tutor/homeTutor.fxml"));
                loader.setControllerFactory(c -> new HomeTutorGuiController());
            } else {
                System.out.println("siamo:" + isTutor);

                loader = new FXMLLoader(LoginGuiController.class.getResource("/com/example/studypal/view/studente/homeStudente.fxml"));
                loader.setControllerFactory(c -> new HomeStudenteGuiController());
            }
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) credenzialiError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
