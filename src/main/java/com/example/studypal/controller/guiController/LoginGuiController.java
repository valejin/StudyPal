package com.example.studypal.controller.guiController;

import com.example.studypal.bean.CredenzialiBean;
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

            LoginController loginController = new LoginController();
            loginController.loginMethod(credenzialiBean);
        } catch (CredenzialiSbagliateException e) {
            System.out.println("controller grafico cred sbagl");
            credenzialiSbagliate.setText("Credenziali sbagliate.");

        }
       /* if(loginController.loginMethod(credenzialiBean) == null) {

            credenzialiError.setText("Campi obbligatori.");
            return;
        }*/

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

}
