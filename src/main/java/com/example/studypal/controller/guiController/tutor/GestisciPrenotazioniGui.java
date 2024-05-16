package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class GestisciPrenotazioniGui extends HomeTutorGui {

    //controller grafico che contiene i metodi di gestione delle prenotazioni

    @FXML
    private Button  prenotazioniAttive;
    private static final Logger logger = Logger.getLogger(GestisciPrenotazioniGui.class.getName());

    public GestisciPrenotazioniGui(){}
    public GestisciPrenotazioniGui(LoggedInUserBean user){ this.user = user;}

    //vado alla pagina di tutte le richieste arrivate per questo tutor
    public void goToRichiesteArrivate(){

        //metodo che porta alla pagina di gestione delle prenotazioni
        try {
            FXMLLoader loader = new FXMLLoader(RichiesteArrivateGui.class.getResource("/com/example/studypal/view/tutor/richiesteArrivate.fxml"));
            loader.setControllerFactory(c -> new RichiesteArrivateGui(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) prenotazioniAttive.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in GestisciPrenotazioniGuiController " + e.getMessage());
           // e.printStackTrace();
        }
    }
}
