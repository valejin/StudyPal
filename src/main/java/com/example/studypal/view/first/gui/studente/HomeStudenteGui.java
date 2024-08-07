package com.example.studypal.view.first.gui.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.view.first.gui.LoginGuiController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeStudenteGui {

    /*
        controller che gestisce il menu di navigazione
        verrà ereditato da tutte le classi che hanno bisogno delle sue funzionalità
        prevede i metodi relativi allo studente: prenota ripetizione, gestisci prenotazioni, esci
     */

    @FXML
    private VBox barraMenu;
    protected LoggedInUserBean user;

    private static final Logger logger = Logger.getLogger(HomeStudenteGui.class.getName());


    protected HomeStudenteGui() {}
    public HomeStudenteGui(LoggedInUserBean user) { this.user = user;}


    //funzione per il bottone Prenota Ripetizione
    @FXML
    public void goToPrenotaRipetizione(){
        //metodo che porta alla pagina di gestione del profilo
        try {
            FXMLLoader loader = new FXMLLoader(HomeStudenteGui.class.getResource("/com/example/studypal/view/studente/cercaRipetizioneStudente.fxml"));
            loader.setControllerFactory(c -> new CercaRipetizioneGui(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) barraMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in HomeStudenteGui " + e.getMessage());
        }
    }

    public void goToGestisciPrenotazioni(){

        //metodo che porta alla pagina di gestione delle prenotazioni
        try {
            FXMLLoader loader = new FXMLLoader(HomeStudenteGui.class.getResource("/com/example/studypal/view/studente/gestisciPrenotazioniStudente.fxml"));
            loader.setControllerFactory(c -> new GestisciPrenotazioniStudenteGui(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) barraMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in HomeStudenteGui " + e.getMessage());
        }


    }

    public void goToLogout(){
        try {

            /*
            Prima mantenevamo i dati relativi alla sessione passando un bean di controller in controller.
            eliminare la sessione significa tornare alla pagina di login senza passare alcun parametro contente i dati della sessione
            quindi di base il controller grafico deve al massimo portare ad una pagina di conferma del logout (qui non è fatto), poi semplicemente carica il login
            */

            FXMLLoader loader = new FXMLLoader(HomeStudenteGui.class.getResource("/com/example/studypal/view/login.fxml"));
            loader.setControllerFactory(c -> new LoginGuiController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) barraMenu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in MenuBaseStudenteGuiController " + e.getMessage());
        }
    }
}
