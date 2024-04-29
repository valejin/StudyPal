package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class GestisciPrenotazioniStudenteGui extends HomeStudenteGui{

    private Button richiesteInviate;

    private static final Logger logger = Logger.getLogger(GestisciPrenotazioniStudenteGui.class.getName());

    public GestisciPrenotazioniStudenteGui(LoggedInUserBean user){ this.user = user;}

    public void goToRichiesteInviate(){
        /* carica la nuova pagina dove vengono mostrate le prenotazioni inviate*/

        try {
            FXMLLoader loader = new FXMLLoader(CercaRipetizioneGui.class.getResource("/com/example/studypal/view/studente/prenotazioniInviate.fxml"));
            loader.setControllerFactory(c -> new GestisciPrenotazioniStudenteGui(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) richiesteInviate.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in GestisciPrenotazioniStudenteGui " + e.getMessage());
            e.printStackTrace();
        }

    }


}
