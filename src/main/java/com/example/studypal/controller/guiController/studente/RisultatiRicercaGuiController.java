package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.other.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class RisultatiRicercaGuiController extends HomeStudenteGui {

    /*
    Controller grafico per la gestione della pagina dei risultati di ricerca
    Deve mostrare le informazioni relative ai tutor che soddisfano i requisiti della ricerca fatta in CercaRipetizioneGui
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
    @FXML
    private Label materiaRisultato;
    @FXML
    private Label luogoRisultato;
    @FXML
    private Label modalitaRisultato;
    @FXML
    private Label tariffaRisultato;
    @FXML
    private Label giorniRisultato;

    //inizializzo una lista, in cui popolo gli elementi della tabella
    List<RipetizioneInfoBean> tutorList = new ArrayList<>();
    RipetizioneInfoBean filtri;
    private static final Logger logger = Logger.getLogger(RisultatiRicercaGuiController.class.getName());


    protected RisultatiRicercaGuiController(LoggedInUserBean user,List<RipetizioneInfoBean> risultatiRicercaBean, RipetizioneInfoBean ripetizioneInfoBean){
        this.user = user;
        this.tutorList = risultatiRicercaBean;
        this.filtri = ripetizioneInfoBean;
    }

    public RisultatiRicercaGuiController() {
    }

    public void initialize(){

        /* ------------ FILTRI ----------------*/

        materiaRisultato.setText(filtri.getMateria());
        if (filtri.getLuogo() != null){
            luogoRisultato.setText(filtri.getLuogo());
        } else {
            luogoRisultato.setText("Non specificato");
        }
        if ((filtri.getInPresenza()!= null) || (filtri.getOnline() != null)) {
            if(Boolean.TRUE.equals(filtri.getInPresenza()) && Boolean.TRUE.equals(filtri.getOnline())){
                modalitaRisultato.setText("In presenza & Online");
            } else if (Boolean.TRUE.equals(filtri.getOnline())) {
                modalitaRisultato.setText("Online");
            } else if (Boolean.TRUE.equals(filtri.getInPresenza())) {
                modalitaRisultato.setText("In presenza");
            } else if (Boolean.FALSE.equals(filtri.getInPresenza()) && Boolean.FALSE.equals(filtri.getOnline())){
                modalitaRisultato.setText("In presenza o Online");
            }
        } else {
            modalitaRisultato.setText("In presenza & Online");
        }
        if (filtri.getTariffa() != null){
            int value = filtri.getTariffa();
            tariffaRisultato.setText(value + "€/h");
        } else {
            tariffaRisultato.setText("50€/h");
        }

        if(filtri.getGiorni() != null) {
            giorniRisultato.setText(filtri.getGiorni());
        } else {
            giorniRisultato.setText("Qualsiasi");
        }


        /*abbrevio i giorni prima di mostrarli--------------------------*/

        for (RipetizioneInfoBean tutor: this.tutorList){
            tutor.abbreviaGiorni();
            Printer.println(tutor.getGiorni());
        }


        /* --------------------------TABELLA----------------------------*/

        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        tariffa.setCellValueFactory(new PropertyValueFactory<>("tariffa"));
        giorni.setCellValueFactory(new PropertyValueFactory<>("giorni"));


        // Aggiungi gli elementi alla TableView
        risultatiTable.getItems().addAll(tutorList);


        // Aggiungi una cell factory personalizzata alla colonna "nome"
        nome.setCellFactory(new Callback<TableColumn<RipetizioneInfoBean, String>, TableCell<RipetizioneInfoBean, String>>() {
            @Override
            public TableCell<RipetizioneInfoBean, String> call(TableColumn<RipetizioneInfoBean, String> param) {
                return new TableCell<RipetizioneInfoBean, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");
                            setOnMouseClicked(event -> {
                                RipetizioneInfoBean tutorSelezionato = getTableView().getItems().get(getIndex());
                                apriProfiloTutor(tutorSelezionato);
                            });
                        } else {
                            setText(null);
                            setStyle(null);
                            setOnMouseClicked(null);
                        }
                    }
                };
            }
        });




        // Imposta la factory per la colonna "Richiedi"
        prenota.setCellFactory(new Callback<>() {
            @Override
            public TableCell<RipetizioneInfoBean, Button> call(TableColumn<RipetizioneInfoBean, Button> param) {
                return new TableCell<>() {
                    final Button btn = new Button("Richiedi");

                    {
                        btn.setOnAction(event -> {
                            RipetizioneInfoBean tutor = getTableView().getItems().get(getIndex());
                            scegliTutor(tutor);
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







    public void scegliTutor(RipetizioneInfoBean tutor) {
        Printer.println("ho scelto il tutor");

        /* carica la pagina di conferma della prenotazione */

        try {
            FXMLLoader loader = new FXMLLoader(PrenotaRipetizioneGui.class.getResource("/com/example/studypal/view/studente/prenotaRipetizione.fxml"));
            loader.setControllerFactory(c -> new PrenotaRipetizioneGui(this.user, tutor, this.tutorList, this.filtri));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) risultatiTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RisultatiRicercaGuiController (caricamento pagina) " + e.getMessage());
        }
    }

    public void goToRicerca() {

        try {
            FXMLLoader loader = new FXMLLoader(RisultatiRicercaGuiController.class.getResource("/com/example/studypal/view/studente/cercaRipetizioneStudente.fxml"));
            loader.setControllerFactory(c -> new CercaRipetizioneGui(user, this.filtri.getMateria()));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) materiaRisultato.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RisultatiRicercaGuiController (ritorno a ricerca) " + e.getMessage());

        }

    }

    public void apriProfiloTutor(RipetizioneInfoBean tutor){
        try {
            FXMLLoader loader = new FXMLLoader(RisultatiRicercaGuiController.class.getResource("/com/example/studypal/view/studente/profiloTutor.fxml"));
            loader.setControllerFactory(c -> new ProfiloTutorGui(user, tutor, this.tutorList, this.filtri));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) materiaRisultato.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RisultatiRicercaGuiController (caricamento profilo tutor)" + e.getMessage());

        }
    }
}
