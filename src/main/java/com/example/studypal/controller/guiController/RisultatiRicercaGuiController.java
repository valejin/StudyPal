package com.example.studypal.controller.guiController;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
    }
}
