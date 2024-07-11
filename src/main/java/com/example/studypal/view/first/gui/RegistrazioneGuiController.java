package com.example.studypal.view.first.gui;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.controller.application.RegistrazioneController;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.EmailNonValidaException;
import com.example.studypal.other.Printer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
    private CheckBox ruoloCheckBox;

    protected LoggedInUserBean user;

    private static final Logger logger = Logger.getLogger(RegistrazioneGuiController.class.getName());

    protected RegistrazioneGuiController(LoggedInUserBean user) { this.user = user;}

    // Pattern regex per validare l'email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @FXML
    void registrazioneMethod() {
        //metodo attivato dal pulsante di conferma sulla schermata di registrazione

        String userNome;
        String userCognome;
        String userEmail;
        String userPassword;
        String confermaPassword;
        boolean ruolo;



        //se sono stati compilati tutti i campi
        if(!this.nomeField.getText().isEmpty() && !this.cognomeField.getText().isEmpty() && !this.emailField.getText().isEmpty() && !this.passwordField.getText().isEmpty() && !this.confermaPasswordField.getText().isEmpty()){

            //prendo i dati inseriti dall'utente
            userNome = this.nomeField.getText();
            userCognome = this.cognomeField.getText();


            //controllo la forma dell'email inserito
            userEmail = this.emailField.getText();
            try{
                isValidEmail(userEmail);
            } catch(EmailNonValidaException e){
                campiError.setText("L'email non è valida");
                return;
            }



            userPassword = this.passwordField.getText();
            confermaPassword = this.confermaPasswordField.getText();

            //controllo sulle password
            if (!confermaPassword.equals(userPassword)){
                campiError.setText("Password non corrispondenti");
                return;
            }


            //gestisco il ruolo dell'utente
            if (this.ruoloCheckBox.isSelected()) {
                ruolo = true;
            } else {
                ruolo = false;
            }
        }
        else{
            campiError.setText("Campi obbligatori");
            return;
        }

        try {
            //inserisco gli input ottenuti in BEAN
            RegistrazioneUserBean registrazioneUserBean = new RegistrazioneUserBean(userEmail, userNome, userCognome, ruolo, userPassword, confermaPassword);

            registrazioneUserBean.setNome(userNome);
            registrazioneUserBean.setCognome(userCognome);
            registrazioneUserBean.setEmail(userEmail);
            registrazioneUserBean.setPassword(userPassword);
            registrazioneUserBean.setConfermaPassword(confermaPassword);
            registrazioneUserBean.setRuolo(ruolo);

            //istanzio un controller applicativo e gli passo il bean contenente i dati per registrare l'utente
            RegistrazioneController registrazioneController = new RegistrazioneController();
            registrazioneController.registrazioneMethod(registrazioneUserBean);

            Printer.println("---------------------------------------------------------");
            Printer.println("L'utente è stato correttamente registrato.");
            caricaConferma();

        }catch(EmailAlreadyInUseException e){
            Printer.errorPrint("controller applicativo: l'email è già in uso");
            campiError.setText("Email già usato");

        }
    }


    //funzione per il controllo della forma di email-------------------------------------------------
    public static void isValidEmail(String email) throws EmailNonValidaException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailNonValidaException();
        }
    }





    //cambio pagina: quando effettuato correttamente la registrazione--------------------------------------------------------------
    public void caricaConferma () {
        try {
            FXMLLoader loader = new FXMLLoader(RegistrazioneGuiController.class.getResource("/com/example/studypal/view/confermaRegistrazione.fxml"));
            loader.setControllerFactory(c -> new RegistrazioneGuiController(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) campiError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RegistrazioneGuiController " + e.getMessage());
        }
    }


    public void goToLogin () {
        try {
            FXMLLoader loader = new FXMLLoader(RegistrazioneGuiController.class.getResource("/com/example/studypal/view/login.fxml"));
         //   loader.setControllerFactory(c -> new LoginGuiController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            Stage stage = (Stage) campiError.getScene().getWindow();
            if (stage != null) {
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Impossibile ottenere la finestra di Login.");
            }

            assert stage != null;
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RegistrazioneGuiController " + e.getMessage());
        }
    }
}
