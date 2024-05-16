package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Logger;

public class GestisciPrenotazioniStudenteGui extends HomeStudenteGui{

    @FXML
    Button richiesteInviate;
    @FXML
    protected TableColumn<PrenotazioneBean, String> materia;
    @FXML
    protected TableColumn<PrenotazioneBean, String> email;
    @FXML
    protected TableColumn<PrenotazioneBean, Integer> tariffa;
    private static final Logger logger = Logger.getLogger(GestisciPrenotazioniStudenteGui.class.getName());


    public GestisciPrenotazioniStudenteGui(LoggedInUserBean user){this.user=user;}


    public void goToRichiesteInviate(){

        /*
            carica la nuova pagina dove vengono mostrate le prenotazioni inviate
            deve fare richiesta delle richieste inviate e passarle al costruttore del controller grafico

        */


        try {
            FXMLLoader loader = new FXMLLoader(GestisciPrenotazioniStudenteGui.class.getResource("/com/example/studypal/view/studente/richiesteInviate.fxml"));
            loader.setControllerFactory(c -> new RichiesteInviateGui(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) richiesteInviate.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in GestisciPrenotazioniStudenteGui (caricamento richieste inviate) " + e.getMessage());
            //e.printStackTrace();
        }

    }

    public void goToRichiesteRifiutate(){

    }

}
