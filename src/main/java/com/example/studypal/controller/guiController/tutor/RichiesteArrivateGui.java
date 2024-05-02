package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.tutor.RichiesteArrivateController;
import com.example.studypal.controller.guiController.studente.PrenotaRipetizioneGui;
import com.example.studypal.controller.guiController.studente.RichiesteInviateGui;
import com.example.studypal.other.Printer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RichiesteArrivateGui extends GestisciPrenotazioniGui {
    /*
    Controller grafico per la gestione della pagina dei risultati arrivati a un determinato tutor
    Devo mostrare: email dello studente, materia richiesta, bottone VISUALIZZA
     */

    @FXML
    private TableView<PrenotazioneBean> risultatiTable;

    @FXML
    private TableColumn<PrenotazioneBean, String> email;

    @FXML
    private TableColumn<PrenotazioneBean, String> materia;

    @FXML
    private TableColumn<PrenotazioneBean, Button> visualizza;


    //inizializzo una lista, in cui popolo gli elementi della tabella
    List<PrenotazioneBean> richiesteList = new ArrayList<>();


    private static final Logger logger = Logger.getLogger(RichiesteArrivateGui.class.getName());


    public RichiesteArrivateGui(LoggedInUserBean user){
        this.user = user;
    }

    public RichiesteArrivateGui() {
    }


    public void initialize(){

        //creo un'istanza di controller applicativo corrispondente
        RichiesteArrivateController richiesteArrivateController = new RichiesteArrivateController();

        //chiamo la funzione nel controller applicativo per ottenere una lista di BEAN dove contiene tutte le info per stampare a schermo
        richiesteList = richiesteArrivateController.richiesteArrivate(user);


        email.setCellValueFactory(new PropertyValueFactory<>("emailStudente"));
        materia.setCellValueFactory(new PropertyValueFactory<>("materia"));

        // Aggiungi gli elementi alla TableView
        risultatiTable.getItems().addAll(richiesteList);

        //Imposta la factory per la colonna "Visualizza"
        visualizza.setCellFactory(new Callback<>() {
            @Override
            public TableCell<PrenotazioneBean, Button> call(TableColumn<PrenotazioneBean, Button> param) {
                return new TableCell<>() {
                    final Button btn = new Button("Visualizza");

                    {
                        btn.setOnAction(event -> {
                            PrenotazioneBean bean = getTableView().getItems().get(getIndex());
                            visualizzaRichiesta(bean);
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });






    }





    public void visualizzaRichiesta(PrenotazioneBean prenotazioneBean) {
        Printer.println("---------------------------------------------------------");
        Printer.println("Stai visualizzando la richiesta di: " + prenotazioneBean.getEmailStudente());

        /*
            carica la pagina con dettagli di richiesta
        */

        try {
            FXMLLoader loader = new FXMLLoader(RichiesteArrivateGui.class.getResource("/com/example/studypal/view/tutor/visualizzaRichiesteArrivate.fxml"));
            loader.setControllerFactory(c -> new VisualizzaRichiestaGui(user, prenotazioneBean, richiesteList));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) risultatiTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in VisualizzaRichiesteArrivateGuiController (caricamento pagina) " + e.getMessage());
            e.printStackTrace();
        }





    }

}
