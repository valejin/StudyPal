package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import com.example.studypal.controller.applicationController.tutor.GestisciPrenotazioniController;
import com.example.studypal.model.PrenotazioneModel;
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

public class GestisciPrenotazioniStudenteGui extends HomeStudenteGui{

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
    private static final Logger logger = Logger.getLogger(GestisciPrenotazioniStudenteGui.class.getName());


    /* costruttori ---------------------------------------------------------------------------------------------------*/
    public GestisciPrenotazioniStudenteGui(LoggedInUserBean user){this.user=user;}

    public GestisciPrenotazioniStudenteGui(LoggedInUserBean user, List<PrenotazioneBean> richiesteList){
        this.user = user;
        this.richiesteList = richiesteList;
        initializeRichieste(); //in questo modo viene eseguito solo quando passiamo alla seconda pagina??
    }


    /* inizializzazione della pagina delle richieste inviate----------------------------------------------------------*/
    public void initializeRichieste() {

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

    public void goToRichiesteInviate(){
        /* carica la nuova pagina dove vengono mostrate le prenotazioni inviate*/

        /* deve fare richiesta delle richieste inviate e passarle al costruttore del controller grafico*/

        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController(user);
        richiesteList = gestisciPrenotazioniStudenteController.richiesteInviate(user.getEmail());

        try {
            FXMLLoader loader = new FXMLLoader(GestisciPrenotazioniStudenteGui.class.getResource("/com/example/studypal/view/studente/richiesteInviate.fxml"));
            loader.setControllerFactory(c -> new GestisciPrenotazioniStudenteGui(user, richiesteList));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) richiesteInviate.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in GestisciPrenotazioniStudenteGui " + e.getMessage());
            e.printStackTrace();
        }

    }


    public void visualizza(PrenotazioneBean prenotazione) {

    }


}
