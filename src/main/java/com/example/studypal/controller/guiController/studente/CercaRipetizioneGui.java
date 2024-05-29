package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.studente.CercaRipetizioneController;
import com.example.studypal.other.Printer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class CercaRipetizioneGui extends HomeStudenteGui {


    @FXML
    private TextField cercaMateria;
    @FXML
    private ComboBox<String> luogo;
    @FXML
    private MenuButton giorno;
    @FXML
    private Slider tariffaSlider;
    @FXML
    private Label tariffaValue;
    @FXML
    private CheckBox inPresenza;
    @FXML
    private CheckBox online;
    @FXML
    private Label campiError;
    @FXML
    private Label luogoError;

    private String materia;
    private static final Logger logger = Logger.getLogger(CercaRipetizioneGui.class.getName());
    RipetizioneInfoBean ripetizioneInfoBean; /*filtri*/
    List<RipetizioneInfoBean> risultatiRicercaBean = new ArrayList<>();

    //eredita dal padre un attributo LoggedInUserBean
    public CercaRipetizioneGui(LoggedInUserBean user) {this.user = user;}
    public CercaRipetizioneGui(LoggedInUserBean user, String materia) {this.user = user; this.materia = materia;}

    @FXML
    public void initialize() {

        //se siamo tornati indietro dalla pagina dei risultati
        if (materia != null){ cercaMateria.setText(materia);}

        // Imposta il valore minimo e massimo del Slider
        tariffaSlider.setMin(5);
        tariffaSlider.setMax(50);
        tariffaSlider.setValue(50); // Imposta un valore predefinito(minimo)
        tariffaSlider.setShowTickLabels(true);
        tariffaSlider.setShowTickMarks(true);
        tariffaSlider.setBlockIncrement(1);

        // Per visualizzare dinamicamente il valore di Slider
        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> tariffaValue.setText(newValue.intValue() + "€"));

        //luogo----------------------------------------------
        luogo.getItems().addAll("Roma", "Milano", "Palermo", "Torino", "Napoli");


        //combobox giorni disponibili -------------------------------------------------------
        giorno.setOnAction(event -> {
            StringBuilder selectedValues = getSelectedValues();
            giorno.setText(selectedValues.toString());
        });

    }


    /*----------------------------------------------------------------------------------------------------------------*/
    public void ricercaMethod(){

        if(this.cercaMateria.getText().isEmpty()) {
            Printer.println("Non hai inserito la materia");
            campiError.setText("Campo obbligatorio");

        } else {
            if (this.tariffaSlider.getValue() == 50 &&
                    (!this.inPresenza.isSelected() && !this.online.isSelected()) &&
                    this.luogo.getSelectionModel().isEmpty() && this.luogo.getValue()==null && menuButtonIsEmpty(giorno)

            ) {
                //se tutti i campi aggiuntivi sono vuoti, allora la ricerca va fatta solo per materia
                risultatiRicercaBean = ricercaMateria();
                caricaRisultati(risultatiRicercaBean);
                Printer.println("ricerca completata");

            } else if (this.inPresenza.isSelected() && this.luogo.getSelectionModel().isEmpty() && this.luogo.getValue()==null){
                luogoError.setText("Inserire un luogo");

            } else {
                //altrimenti la ricerca avviene anche con i filtri aggiunti
                Printer.println("Ricerca con filtri...");
                risultatiRicercaBean =  ricercaConFiltri();
                caricaRisultati(risultatiRicercaBean);
                Printer.println("ricerca completata!");
            }
        }
    }

    /*-------------------------------------RICERCA PER SOLA MATERIA---------------------------------------------------*/
    public   List<RipetizioneInfoBean>  ricercaMateria(){

        String materia = this.cercaMateria.getText();
        ripetizioneInfoBean = new RipetizioneInfoBean(materia);
        Printer.println("La materia inserita è: " + materia);

        //prendo un BEAN base e inserisco info
        BaseInfoBean baseInfoBean = new BaseInfoBean(materia);

        CercaRipetizioneController cercaRipetizioneController = new CercaRipetizioneController();

        //chiama il controller applicativo e gli passa il BEAN che contiene la materia
        risultatiRicercaBean = cercaRipetizioneController.prenotaRipetizioneMethod(baseInfoBean);

        return risultatiRicercaBean;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public boolean menuButtonIsEmpty(MenuButton menuButton){
        ObservableList<MenuItem> items = menuButton.getItems();
        boolean answer = true;

        for (MenuItem item : items) {
            if (item instanceof CheckMenuItem checkItem && checkItem.isSelected()) {
                    answer = false;
                    return answer;
                }
        }
        return answer;
    }


    /*-------------------------------------------- RICERCA CON FILTRI ------------------------------------------------*/
    public List<RipetizioneInfoBean> ricercaConFiltri() {

        /*
        prendo luogo, inPresenza, online, giorno, tariffa da BEAN
        */
        String materia;
        String luogo;
        boolean inPresenza = false;
        boolean online = false;
        String giorni;
        Integer tariffa;
        String email = this.user.getEmail();

        //i campi sono già stati controllati se sono vuoti in ricercaMethod
        //prendo i dati inseriti dall'utente

        materia = this.cercaMateria.getText();

        if (this.cercaMateria.getText().isEmpty()) {
            Printer.println("Non hai inserito la materia");
            campiError.setText("Campo obbligatorio");
            return Collections.emptyList();
        } else {
            campiError.setVisible(false);
        }

        Printer.println("FILTRI PER LA RICERCA:");
        Printer.println("   -Materia: " + materia);

        luogo = this.luogo.getValue();
        Printer.print("   -Luogo: ");
        if (this.luogo.getValue() != null) {
            Printer.println(luogo);
        } else {
            Printer.println("");
        }

        //checkBox: modalità di lezione ------------------------------------------------
        Printer.print("   -Modalità di lezione: ");
        if (this.inPresenza.isSelected()) {
            inPresenza = true;
            Printer.println("in presenza");
            if (this.luogo.getSelectionModel().isEmpty() && this.luogo.getValue()==null) {
                Printer.errorPrint("Seleziona un luogo");
                luogoError.setText("Seleziona un luogo");
                return Collections.emptyList();
            } else {
                luogoError.setVisible(false);
            }
        }

        if (this.online.isSelected()) {
            online = true;
            Printer.println("online");
        }

        if (!this.inPresenza.isSelected() && !this.online.isSelected()) {
            Printer.println(" ");
        }

        //menuButton di giono-------------------------------------------------------
        StringBuilder selectedValues = getSelectedValues();
        giorni = selectedValues.toString();
        Printer.println("   -Giorni selezionati: " + giorni);

        //tariffaSlider---------------------------------------------------
        tariffa = (int) Math.round(this.tariffaSlider.getValue());
        Printer.println("   -Tariffa massima: " + tariffa);

        //istanzio un RipetizioneInfoBean
        ripetizioneInfoBean = new RipetizioneInfoBean(materia, inPresenza, online, luogo, giorni, tariffa, email);

        //todo: è necessario settarli di nuovo se abbiamo usato il costruttore con i parametri? Non penso!
        ripetizioneInfoBean.setEmail(email);
        ripetizioneInfoBean.setMateria(materia);
        ripetizioneInfoBean.setInPresenza(inPresenza);
        ripetizioneInfoBean.setOnline(online);
        ripetizioneInfoBean.setLuogo(luogo);
        ripetizioneInfoBean.setGiorni(giorni);
        ripetizioneInfoBean.setTariffa(tariffa);

        //istanzio un controller applicativo e gli passo la lista di Bean contenente i risultati della ricerca
        CercaRipetizioneController cercaRipetizioneController = new CercaRipetizioneController();
        risultatiRicercaBean = cercaRipetizioneController.prenotaRipetizioneMethod(ripetizioneInfoBean);

        /*DEBUG
        System.out.println("Controller grafico ha ricevuto questi risultati:");
        for (RipetizioneInfoBean risultatoBean: risultatiRicercaBean) {
            System.out.println("    nome: " + risultatoBean.getNome());
            System.out.println("    cognome: " + risultatoBean.getCognome());
            System.out.println("    materie: " + risultatoBean.getMaterie());
            System.out.println("    lezioni in presenza: " + risultatoBean.getInPresenza());
            System.out.println("    lezioni online: " + risultatoBean.getOnline());
            System.out.println("    luogo: " + risultatoBean.getLuogo());
            System.out.println("    giorni disponibili: " + risultatoBean.getGiorni());
            System.out.println("    tariffa: " + risultatoBean.getTariffa() + "€/h");
            System.out.println("    email: " + risultatoBean.getEmail());
            System.out.println("------------------------------------------------");
        }
        */

        return risultatiRicercaBean;
    }

    private StringBuilder getSelectedValues() {

        /* funzione che costruisce la stringa che indica i giorni selezionati
        * todo: ma perché restituisce stringbuilder? che vuol dire non capisco aaaaaa
        * */

        ObservableList<MenuItem> items = giorno.getItems();
        StringBuilder selectedValues = new StringBuilder();
        for (MenuItem item : items) {
            if (item instanceof CheckMenuItem checkMenuItem && checkMenuItem.isSelected()) {
                    if (!selectedValues.isEmpty()) {
                        selectedValues.append(", ");
                    }
                    selectedValues.append(checkMenuItem.getText());
                }

        }
        return selectedValues;
    }


    /*----------------------------------------------------------------------------------------------------------------*/
        public void caricaRisultati (List < RipetizioneInfoBean > risultatiRicercaBean) {

            try {
                FXMLLoader loader = new FXMLLoader(CercaRipetizioneGui.class.getResource("/com/example/studypal/view/studente/risultatoRicerca.fxml"));
                loader.setControllerFactory(c -> new RisultatiRicercaGuiController(user, risultatiRicercaBean, ripetizioneInfoBean));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) cercaMateria.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                logger.severe("errore in CercaRipetizioneGui " + e.getMessage());
            }
        }

    }
