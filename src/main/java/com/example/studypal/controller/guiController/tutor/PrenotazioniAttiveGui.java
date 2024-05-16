package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.tutor.PrenotazioniAttiveController;
import com.example.studypal.controller.applicationController.tutor.RichiesteArrivateController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PrenotazioniAttiveGui extends GestisciPrenotazioniGui{
    /*
    Controller grafico per gestire la pagina che contiene tutte le prenotazioni attive confermate dal tutor
    Devo mostrare: emailStudente, materia da fare ripetizione, bottone VISUALIZZA per vedere gli dettagli
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
    List<PrenotazioneBean> prenotazioniAttiveList = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(RichiesteArrivateGui.class.getName());


    public PrenotazioniAttiveGui(LoggedInUserBean user){
        this.user = user;
    }


    public void initialize(){
        //creo un'istanza di controller applicativo corrispondente
        PrenotazioniAttiveController prenotazioniAttiveController = new PrenotazioniAttiveController();

        //chiamo la funzione nel controller applicativo per ottenere una lista di BEAN dove contiene tutte le info per stampare a schermo
        prenotazioniAttiveList = prenotazioniAttiveController.PrenotazioniAttive(user);

        email.setCellValueFactory(new PropertyValueFactory<>("emailStudente"));
        materia.setCellValueFactory(new PropertyValueFactory<>("materia"));

        // Aggiungi gli elementi alla TableView
        risultatiTable.getItems().addAll(prenotazioniAttiveList);

        //Imposta la factory per la colonna "Visualizza"
        visualizza.setCellFactory(new Callback<>() {
            @Override
            public TableCell<PrenotazioneBean, Button> call(TableColumn<PrenotazioneBean, Button> param) {
                return new TableCell<>() {
                    final Button btn = new Button("Visualizza");

                    {
                        btn.setOnAction(event -> {
                            PrenotazioneBean bean = getTableView().getItems().get(getIndex());
                            /*
                            manca la chiamata del metodo

                            visualizzaRichiesta(bean);

                             */
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






}
