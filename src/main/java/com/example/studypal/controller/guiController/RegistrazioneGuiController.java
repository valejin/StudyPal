package com.example.studypal.controller.guiController;

import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.controller.applicationController.RegistrazioneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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


    @FXML
    void registrazioneMethod() {
        //metodo attivato dal pulsante di conferma sulla schermata di registrazione

        String userNome = null;
        String userCognome = null;
        String userEmail = null;
        String userPassword = null;
        String confermaPassword = null;

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



    }

}
