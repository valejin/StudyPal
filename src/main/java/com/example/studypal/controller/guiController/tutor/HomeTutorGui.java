package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.controller.guiController.LoginGuiController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeTutorGui {

    /*
        controller che gestisce il menu di navigazione
        verrà ereditato da tutte le classi che hanno bisogno delle sue funzionalità
        prevede i metodi relativi al tutor: gestisci profilo, gestisci prenotazioni, esci
     */

    @FXML
    private VBox barraMenu;
    protected LoggedInUserBean user;
    private static final Logger logger = Logger.getLogger(HomeTutorGui.class.getName());

    //costruttori-------------------------------------------------------------
    protected HomeTutorGui() {}
    public HomeTutorGui(LoggedInUserBean user) { this.user = user;}



    @FXML
    public void goToGestisciProfilo(){
        //metodo che porta alla pagina di gestione del profilo
        try {
            FXMLLoader loader = new FXMLLoader(HomeTutorGui.class.getResource("/com/example/studypal/view/tutor/gestioneProfiloTutor.fxml"));
            loader.setControllerFactory(c -> new GestioneProfiloTutorGuiController(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) barraMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in HomeTutorGui " + e.getMessage());
        }
    }
    @FXML
    public void goToGestisciPrenotazioni(){

        //metodo che porta alla pagina di gestione delle prenotazioni
        try {
            FXMLLoader loader = new FXMLLoader(GestisciPrenotazioniGui.class.getResource("/com/example/studypal/view/tutor/gestisciPrenotazioni.fxml"));
            loader.setControllerFactory(c -> new GestisciPrenotazioniGui(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) barraMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in MenuBaseTutorGuiController " + e.getMessage());
        }


    }

    public void goToLogout(){
        try {

            /*
            prima mantenevamo i dati relativi alla sessione passando un bean di controller in controller.
            eliminare la sessione significa tornare alla pagina di login senza passare alcun parametro contente i dati della sessione
             quindi di base il controller grafico deve al massimo portare ad una pagina di conferma del logout (qui non è fatto), poi semplicemente carica il login
             */

            FXMLLoader loader = new FXMLLoader(HomeTutorGui.class.getResource("/com/example/studypal/view/login.fxml"));
            loader.setControllerFactory(c -> new LoginGuiController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) barraMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in MenuBaseTutorGuiController " + e.getMessage());
        }
    }
}
