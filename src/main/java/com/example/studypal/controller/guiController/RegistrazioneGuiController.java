package com.example.studypal.controller.guiController;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.controller.applicationController.RegistrazioneController;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.RegistrazioneDBException;
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

            System.out.println("siamo qui 1");
            userNome = this.nomeField.getText();
            userCognome = this.cognomeField.getText();
            userEmail = this.emailField.getText();
            userPassword = this.passwordField.getText();
            confermaPassword = this.confermaPasswordField.getText();

            System.out.println("siamo qui 2");
            if (!confermaPassword.equals(userPassword)){
                campiError.setText("Password non corrispondenti.");
                return;
            }

            System.out.println("siamo qui 3");
            //gestisco il ruolo dell'utente
            if (this.ruoloCheckBox.isSelected()) {
                ruolo = true;
               // System.out.println("l'utente è un tutor");
            } else {
                ruolo = false;
            }
        }
        else{
            campiError.setText("Campi obbligatori.");
            System.out.println("siamo qui 4"); //arriviamo qui
            return;
        }

        try {
            //inserisco gli input ottenuti in BEAN
            System.out.println("invio i dati al controller");
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

            Printer.println("L'utente è stato correttamente registrato.");
            caricaConferma();

        }catch(EmailAlreadyInUseException e){
            Printer.errorPrint("controller applicativo: l'email è già in uso");
            campiError.setText("Email già usato.");

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

            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RegistrazioneGuiController " + e.getMessage());
        }
    }
}
