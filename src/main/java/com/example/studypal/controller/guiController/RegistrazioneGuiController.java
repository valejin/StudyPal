package com.example.studypal.controller.guiController;

import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.controller.applicationController.RegistrazioneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class RegistrazioneGuiController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confermaPasswordField;
    @FXML
    private Label campiError;

    private static final Logger logger = Logger.getLogger(RegistrazioneGuiController.class.getName());


    @FXML
    void registrazioneMethod() {
        //metodo attivato dal pulsante di conferma sulla schermata di registrazione


        String userNome;
        String userCognome;
        String userEmail;
        String userPassword;
        String confermaPassword;


        if(!this.nomeField.getText().isEmpty() &&
                !this.cognomeField.getText().isEmpty() &&
                !this.emailField.getText().isEmpty() &&
                !this.passwordField.getText().isEmpty() &&
                !this.confermaPasswordField.getText().isEmpty()){
            //se sono stati compilati tutti i campi

            userNome = this.nomeField.getText();
            userCognome = this.cognomeField.getText();
            userEmail = this.emailField.getText();
            userPassword = this.passwordField.getText();
            confermaPassword = this.confermaPasswordField.getText();
        }
        else{
            campiError.setText("Campi obbligatori.");
            return;
        }


        //inserisco gli input ottenuti in BEAN
        RegistrazioneUserBean registrazioneUserBean = new RegistrazioneUserBean();
        registrazioneUserBean.setNome(userNome);
        registrazioneUserBean.setCognome(userCognome);
        registrazioneUserBean.setEmail(userEmail);
        registrazioneUserBean.setPassword(userPassword);
        registrazioneUserBean.setConfermaPassword(confermaPassword);

        //istanzio un'istanza di controller applicativo
        RegistrazioneController registrazioneController = new RegistrazioneController();

        //chiamare il registrazioneMethod
        registrazioneController.registrazioneMethod(registrazioneUserBean);

        System.out.println("L'utente è stato correttamente registrato.");

    }

    public void caricaLogin () {
        try {
            FXMLLoader loader = new FXMLLoader(RegistrazioneGuiController.class.getResource("/com/example/studypal/view/login.fxml"));
            loader.setControllerFactory(c -> new LoginGuiController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) campiError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RegistrazioneGuiController " + e.getMessage());

        }
    }
}
