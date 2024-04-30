package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class RichiesteInviateGui extends HomeStudenteGui{

    /* todo: forse dobbiamo separare i controller di gestisci prenotazioni e visualizza richieste inviate? */

    @FXML
    Button richiesteInviate;
    @FXML
    protected TableView<PrenotazioneBean> richiesteTable;
    @FXML
    protected TableColumn<PrenotazioneBean, String> materia;
    @FXML
    protected TableColumn<PrenotazioneBean, String> email;
    @FXML
    protected TableColumn<PrenotazioneBean, Integer> tariffa;
    @FXML
    protected TableColumn<PrenotazioneBean, Button> visualizza;

    List<PrenotazioneBean> richiesteList= new ArrayList<>();


    public RichiesteInviateGui(LoggedInUserBean user){this.user = user;}

    public void initialize() {


        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController(user);
        richiesteList = gestisciPrenotazioniStudenteController.richiesteInviate(user.getEmail());

        //inizializza la tabella popolandola con le informazioni che gli passa il controller grafico precedente*/

        materia.setCellValueFactory(new PropertyValueFactory<>("materia"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tariffa.setCellValueFactory(new PropertyValueFactory<>("tariffa"));

        richiesteTable.getItems().addAll(richiesteList);

        /* gestione del pulsante per visualizzare più informazioni riguardo la prenotazione*/
        visualizza.setCellFactory(new Callback<>() {
            @Override
            public TableCell<PrenotazioneBean, Button> call(TableColumn<PrenotazioneBean, Button> param) {
                return new TableCell<>() {
                    final Button btn = new Button("Visualizza");

                    {
                        btn.setOnAction(event -> {
                            PrenotazioneBean prenotazione = getTableView().getItems().get(getIndex());
                            visualizza(prenotazione);
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

    public void visualizza(PrenotazioneBean prenotazione) {

    }


}
