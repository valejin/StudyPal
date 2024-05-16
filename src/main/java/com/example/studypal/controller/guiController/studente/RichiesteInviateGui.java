package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import com.example.studypal.controller.guiController.tutor.RichiesteArrivateGui;
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

public class RichiesteInviateGui extends HomeStudenteGui{

    /* todo: forse dobbiamo separare i controller di gestisci prenotazioni e visualizza richieste inviate? */

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

    private static final Logger logger = Logger.getLogger(RichiesteInviateGui.class.getName());

    public RichiesteInviateGui(LoggedInUserBean user){this.user = user;}

    public void initialize() {


        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController(user);
        richiesteList = gestisciPrenotazioniStudenteController.richiesteInviate(user.getEmail());

        //inizializza la tabella popolandola con le informazioni che gli passa il controller grafico precedente*/

        materia.setCellValueFactory(new PropertyValueFactory<>("materia"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailTutor"));
        tariffa.setCellValueFactory(new PropertyValueFactory<>("tariffa"));

        richiesteTable.getItems().addAll(richiesteList);

        /* gestione del pulsante per visualizzare più informazioni riguardo la prenotazione*/
        visualizza.setCellFactory(new Callback<>() {
            @Override
            public TableCell<PrenotazioneBean, Button> call(TableColumn<PrenotazioneBean, Button> param) {
                return new TableCell<>() {
                    final Button btn = new Button("visualizza");
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
                            //btn.setStyle("-fx-font-size: 20px; -fx-font-family: Perpetua; -fx-padding: 5px 10px");
                            setGraphic(btn);
                        }
                    }
                };
            }
        });


    }

    public void visualizza(PrenotazioneBean prenotazione) {

        try {
            FXMLLoader loader = new FXMLLoader(RichiesteArrivateGui.class.getResource("/com/example/studypal/view/studente/visualizzaRichiesteInviate.fxml"));
            loader.setControllerFactory(c -> new VisualizzaRichiesteStudenteGui(user, prenotazione, richiesteList));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) richiesteTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in RichiesteInviateGui (caricamento pagina 'visualizza') " + e.getMessage());
            //e.printStackTrace();
        }




    }


}
