package com.example.studypal.controller.guiController;

import com.example.studypal.bean.LoggedInUserBean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RisultatiRicercaGuiController extends HomeStudenteGui {

    /*
    Controller grafico per la gestione della pagina dei risultati di ricerca
    Deve mostrare le informazioni relative ai tutor che soddisfano i requisiti della ricerca fatta in PrenotaRipetizioneStudenteGui
     */

    public RisultatiRicercaGuiController(LoggedInUserBean user){this.user = user;}

    public void scegliTutor() {
        System.out.println("ho scelto il tutor");
    }
}
