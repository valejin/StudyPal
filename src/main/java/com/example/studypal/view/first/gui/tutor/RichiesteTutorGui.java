package com.example.studypal.view.first.gui.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.application.tutor.GestisciPrenotazioniController;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.observer.Observer;
import com.example.studypal.pattern.observer.RichiesteArrivateCollection;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RichiesteTutorGui extends GestisciPrenotazioniGui implements Observer {
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
    private RichiesteArrivateCollection richiesteArrivateCollection; //istanza del Concrete Subject del pattern Observer
    List<PrenotazioneModel> richiesteListModel = new ArrayList<>();
    List<PrenotazioneBean> richiesteList = new ArrayList<>();
    private Integer flag;

    private static final Logger logger = Logger.getLogger(RichiesteTutorGui.class.getName());


    public RichiesteTutorGui(LoggedInUserBean user, Integer flag){
        this.user = user;
        this.flag = flag;
    }

    public RichiesteTutorGui() {
    }


    public void initialize(){

        //pattern Observer
        richiesteArrivateCollection = RichiesteArrivateCollection.getInstance();

        richiesteArrivateCollection.attach(this);

        //creo un'istanza di controller applicativo corrispondente
        GestisciPrenotazioniController gestisciPrenotazioniController = new GestisciPrenotazioniController(user);

        //chiamo la funzione nel controller applicativo per ottenere una lista di BEAN che contiene tutte le info per la tabella
        richiesteList = gestisciPrenotazioniController.getRichieste(user, flag);


        email.setCellValueFactory(new PropertyValueFactory<>("emailStudente"));
        materia.setCellValueFactory(new PropertyValueFactory<>("materia"));

        // Aggiungo tutti i nuovi elementi alla TableView
        risultatiTable.getItems().clear();
        risultatiTable.getItems().addAll(richiesteList);

        //Imposto la factory per la colonna "Visualizza"
        visualizza.setCellFactory(param -> new VisualizzaButtonCell());

    }



    public class VisualizzaButtonCell extends TableCell<PrenotazioneBean, Button> {
        private final Button btn = new Button("Visualizza");

        public VisualizzaButtonCell() {
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

    }



    /*-------------------------------------------PATTERN OBSERVER-----------------------------------------------------*/

    @Override

    public void update(){

        richiesteListModel = richiesteArrivateCollection.ottieniStato();

        //ora converto la lista di model in una lista di bean e per fare questo sfrutto un metodo del controller grafico
        GestisciPrenotazioniController gestisciPrenotazioniController = new GestisciPrenotazioniController(user);
        richiesteList = gestisciPrenotazioniController.convertiRichieste(richiesteListModel);

        risultatiTable.getItems().clear();
        risultatiTable.getItems().addAll(richiesteList);

    }


    /*---------------------------------------------CARICA PAGINA------------------------------------------------------*/

    public void visualizzaRichiesta(PrenotazioneBean prenotazioneBean) {
        Printer.println("---------------------------------------------------------");
        Printer.println("Stai visualizzando la richiesta di: " + prenotazioneBean.getEmailStudente());

        /*
            carica la pagina con dettagli di richiesta
        */
        if(this.flag == 0) {
            try {
                FXMLLoader loader = new FXMLLoader(RichiesteTutorGui.class.getResource("/com/example/studypal/view/tutor/visualizzaRichiesteArrivate.fxml"));
                loader.setControllerFactory(c -> new VisualizzaRichiestaGui(user, prenotazioneBean, richiesteList));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) risultatiTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("errore in RichiesteTutorGui (caricamento richiesta arrivata) " + e.getMessage());
            }
        }else if(this.flag == 1){
            try {
                FXMLLoader loader = new FXMLLoader(RichiesteTutorGui.class.getResource("/com/example/studypal/view/tutor/visualizzaPrenotazioniAttive.fxml"));
                loader.setControllerFactory(c -> new VisualizzaRichiestaGui(user, prenotazioneBean, richiesteList));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) risultatiTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("errore in RichiesteTutorGui (caricamento prenotazione attiva) " + e.getMessage());
            }
        }else if(this.flag == 2){
            try {
                FXMLLoader loader = new FXMLLoader(RichiesteTutorGui.class.getResource("/com/example/studypal/view/tutor/visualizzaPrenotazioniRifiutate.fxml"));
                loader.setControllerFactory(c -> new VisualizzaRichiestaGui(user, prenotazioneBean, richiesteList));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) risultatiTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("errore in RichiesteTutorGui (caricamento richiesta rifiutata) " + e.getMessage());
            }
        }
    }
}
