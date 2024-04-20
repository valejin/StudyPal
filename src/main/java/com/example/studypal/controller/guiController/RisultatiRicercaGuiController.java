package com.example.studypal.controller.guiController;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
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

    /*
    public RisultatiRicercaGuiController(LoggedInUserBean user){
        this.user = user;
    }
    */

    protected RisultatiRicercaGuiController(LoggedInUserBean user,List<RipetizioneInfoBean> risultatiRicercaBean, RipetizioneInfoBean ripetizioneInfoBean){
        this.user = user;
        this.tutorList = risultatiRicercaBean;
        this.filtri = ripetizioneInfoBean;
    }

    public void initialize(){

        //setto i valori posti da utente
        materiaRisultato.setText(filtri.getMateria());
        luogoRisultato.setText(filtri.getLuogo());

        if(filtri.getInPresenza() && filtri.getOnline()){
            modalitaRisultato.setText("In presenza & Online");
        } else if (filtri.getOnline()) {
            modalitaRisultato.setText("Online");
        } else if (filtri.getInPresenza()) {
            modalitaRisultato.setText("In presenza");
        }

        int value = filtri.getTariffa();
        tariffaRisultato.setText(Integer.toString(value));

        giorniRisultato.setText(filtri.getGiorni());


        nome.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, String>("nome"));
        cognome.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, String>("cognome"));
        tariffa.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, Integer>("tariffa"));
        giorni.setCellValueFactory(new PropertyValueFactory<RipetizioneInfoBean, String>("giorni"));



        // Aggiungi gli elementi alla TableView
        risultatiTable.getItems().addAll(tutorList);

        // Imposta la factory per la colonna "Prenota"
        prenota.setCellFactory(new Callback<TableColumn<RipetizioneInfoBean, Button>, TableCell<RipetizioneInfoBean, Button>>() {
            @Override
            public TableCell<RipetizioneInfoBean, Button> call(TableColumn<RipetizioneInfoBean, Button> param) {
                return new TableCell<RipetizioneInfoBean, Button>() {
                    final Button btn = new Button("Prenota");

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






    public RisultatiRicercaGuiController(LoggedInUserBean user){
        this.user = user;
    }

    public void scegliTutor(RipetizioneInfoBean tutor) {
        System.out.println("ho scelto il tutor");

        /*
            deve caricare la funzione di prenotazione di tutor
            carica la pagina di conferma della prenotazione

         */

        try {
            FXMLLoader loader = new FXMLLoader(RegistrazioneGuiController.class.getResource("/com/example/studypal/view/studente/prenotaRipetizione.fxml"));
            loader.setControllerFactory(c -> new PrenotaRipetizioneGui(user, tutor));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) risultatiTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RisultatiRicercaGuiController (caricamento pagina) " + e.getMessage());
        }


    }
}
