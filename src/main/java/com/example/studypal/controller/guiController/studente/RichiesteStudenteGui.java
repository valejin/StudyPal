package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import com.example.studypal.controller.guiController.tutor.RichiesteTutorGui;
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

public class RichiesteStudenteGui extends HomeStudenteGui{

    /* controller grafico che si occupa delle pagine relative alle prenotazioni (prenotazioni attive, richieste inviate, richieste rifiutate)
        in base al flag differenzia il comportamento*/

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
    private final Integer flag;
    private static final Logger logger = Logger.getLogger(RichiesteStudenteGui.class.getName());

    /* NOTA: il controller grafico si occupa di gestire le pagine di:
      - richieste inviate
      - richieste rifiutate
      - prenotazioni attive
     Sfruttiamo un flag per personalizzare i metodi che di base fanno la stessa cosa.
     Passiamo il flag al controller applicativo che poi differenzierà la logica.
    */

    public RichiesteStudenteGui(LoggedInUserBean user, Integer flag){
        this.user = user;
        this.flag = flag;
    }

    public void initialize() {


        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController(user);
        richiesteList = gestisciPrenotazioniStudenteController.richiesteInviate(user.getEmail(), this.flag);

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
                            setGraphic(btn);
                        }
                    }
                };
            }
        });
    }

    public void visualizza(PrenotazioneBean prenotazioneBean) {

        if (this.flag == 0){
            //richieste inviate in attesa di conferma
            try {
                FXMLLoader loader = new FXMLLoader(RichiesteTutorGui.class.getResource("/com/example/studypal/view/studente/visualizzaRichiesteInviate.fxml"));
                loader.setControllerFactory(c -> new VisualizzaRichiesteStudenteGui(user, prenotazioneBean, richiesteList));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) richiesteTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("errore in RichiesteStudenteGui (caricamento pagina richieste inviate) " + e.getMessage());
            }

        } else if (this.flag == 1){
            //richieste confermate (prenotazioni attive)
            try {
                FXMLLoader loader = new FXMLLoader(RichiesteTutorGui.class.getResource("/com/example/studypal/view/studente/visualizzaPrenotazioniAttive.fxml"));
                loader.setControllerFactory(c -> new VisualizzaRichiesteStudenteGui(user, prenotazioneBean, richiesteList));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) richiesteTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("errore in RichiesteStudenteGui (caricamento pagina prenotazioni attive) " + e.getMessage());
            }


        } else if (this.flag == 2){
            try {
                FXMLLoader loader = new FXMLLoader(RichiesteTutorGui.class.getResource("/com/example/studypal/view/studente/visualizzaRichiesteRifiutate.fxml"));
                loader.setControllerFactory(c -> new VisualizzaRichiesteStudenteGui(user, prenotazioneBean, richiesteList));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) richiesteTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("errore in RichiesteStudenteGui (caricamento pagina richieste rifiutate) " + e.getMessage());
            }
        }


    }
}
