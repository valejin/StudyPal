package com.example.studypal.controller.guiController;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RisultatiRicercaGuiController extends HomeStudenteGui {

    /*
    Controller grafico per la gestione della pagina dei risultati di ricerca
    Deve mostrare le informazioni relative ai tutor che soddisfano i requisiti della ricerca fatta in PrenotaRipetizioneStudenteGui
     */
    @FXML
    private TableView<RipetizioneInfoBean> risultatiTable;
    @FXML
    private TableColumn<RipetizioneInfoBean, String> nome;
    @FXML
    private TableColumn<RipetizioneInfoBean, String> cognome;
    @FXML
    private TableColumn<RipetizioneInfoBean, Integer> tariffa;
    @FXML
    private TableColumn<RipetizioneInfoBean, String> giorni;
    @FXML
    private TableColumn<RipetizioneInfoBean, Button> prenota;

    //inizializzo una lista, in cui popolo gli elementi della tabella
    List<RipetizioneInfoBean> tutorList = new ArrayList<>();

    protected RisultatiRicercaGuiController(LoggedInUserBean user,List<RipetizioneInfoBean> risultatiRicercaBean){
        this.user = user;
        this.tutorList = risultatiRicercaBean;
    }

    public void initialize(){
        nome.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, String>("nome"));
        cognome.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, String>("cognome"));
        tariffa.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, Integer>("tariffa"));
        giorni.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, String>("giorni"));
        prenota.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, Button>("prenota"));


        // Aggiungi gli elementi alla TableView
        risultatiTable.getItems().addAll(tutorList);





    }






    public RisultatiRicercaGuiController(LoggedInUserBean user){
        this.user = user;
    }

    public void scegliTutor() {
        System.out.println("ho scelto il tutor");
    }
}
