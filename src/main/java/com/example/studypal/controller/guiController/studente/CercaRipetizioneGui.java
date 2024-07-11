package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.studente.CercaRipetizioneController;
import com.example.studypal.other.Printer;
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
    @FXML
    private CheckBox lunediBox;
    @FXML
    private CheckBox martediBox;
    @FXML
    private CheckBox mercolediBox;
    @FXML
    private CheckBox giovediBox;
    @FXML
    private CheckBox venerdiBox;
    @FXML
    private CheckBox sabatoBox;
    @FXML
    private CheckBox domenicaBox;
    private List<CheckBox> listaCheckbox;

    private String materia;
    private Integer tariffa;
    private String luoghi;
    private Boolean inPresenze;
    private Boolean onlinee;
    private String giorni;


    private static final Logger logger = Logger.getLogger(CercaRipetizioneGui.class.getName());
    RipetizioneInfoBean ripetizioneInfoBean; /*filtri*/
    List<RipetizioneInfoBean> risultatiRicercaBean = new ArrayList<>();

    //eredita dal padre un attributo LoggedInUserBean
    public CercaRipetizioneGui(LoggedInUserBean user) {this.user = user;}
    public CercaRipetizioneGui(LoggedInUserBean user, String materia, Integer tariffa, String luoghi, Boolean inPresenze, Boolean onlinee, String giorni) {
        this.user = user;
        this.materia = materia;
        this.tariffa = tariffa;
        this.luoghi = luoghi;
        this.inPresenze = inPresenze;
        this.onlinee = onlinee;
        this.giorni = giorni;

    }

    @FXML
    public void initialize() {

        listaCheckbox = List.of(lunediBox, martediBox, mercolediBox, giovediBox, venerdiBox, sabatoBox, domenicaBox);
        /* lista usata per iterare attraverso le checkbox e leggerne i valori senza fare troppi if*/
        if(giorni != null){
            // Verifica e seleziona i CheckBox in base ai giorni presenti nella stringa
            if (giorni.contains("L")) {
                lunediBox.setSelected(true);
            }
            if (giorni.contains("Ma")) {
                martediBox.setSelected(true);
            }
            if (giorni.contains("Me")) {
                mercolediBox.setSelected(true);
            }
            if (giorni.contains("G")) {
                giovediBox.setSelected(true);
            }
            if (giorni.contains("V")) {
                venerdiBox.setSelected(true);
            }
            if (giorni.contains("S")) {
                sabatoBox.setSelected(true);
            }
            if (giorni.contains("D")) {
                domenicaBox.setSelected(true);
            }
        }


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

        // Verifica se la stringa tariffa non è vuota
        if (tariffa != null) {
            tariffaSlider.setValue(tariffa);
        }


        //luogo----------------------------------------------
        luogo.getItems().addAll("Roma", "Milano", "Palermo", "Torino", "Napoli");
        if(luoghi != null) {
            luogo.setValue(luoghi);
        }

        //modalità
        if(inPresenze != null){
            inPresenza.setSelected(true);
        }
        if(onlinee != null){
            online.setSelected(true);
        }

    }


    /*----------------------------------------------------------------------------------------------------------------*/
    public void ricercaMethod(){

        if(this.cercaMateria.getText().isEmpty()) {
            Printer.println("Non hai inserito la materia");
            campiError.setText("Campo obbligatorio");

        } else {
            if (this.tariffaSlider.getValue() == 50 &&
                    (!this.inPresenza.isSelected() && !this.online.isSelected()) &&
                    this.luogo.getSelectionModel().isEmpty() && this.luogo.getValue()==null && Boolean.TRUE.equals(giorniVuoti(listaCheckbox))

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
        risultatiRicercaBean = cercaRipetizioneController.ricercaMethod(baseInfoBean);


        Printer.println("GUI: Email tutor che soddisfano la ricerca: ");
        for (RipetizioneInfoBean risultatoBean: risultatiRicercaBean) {
            Printer.println("    nome: " + risultatoBean.getNome());
            Printer.println("    cognome: " + risultatoBean.getCognome());
            Printer.println("    materie: " + risultatoBean.getMaterie());
            Printer.println("    lezioni in presenza: " + risultatoBean.getInPresenza());
            Printer.println("    lezioni online: " + risultatoBean.getOnline());
            Printer.println("    luogo: " + risultatoBean.getLuogo());
            Printer.println("    giorni di interesse: " + risultatoBean.getGiorni());
            Printer.println("    tariffa massima: " + risultatoBean.getTariffa() + "€/h");
            Printer.println("    email: " + risultatoBean.getEmail());
            Printer.println("------------------------------------------------");
        }

        return risultatiRicercaBean;
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
        int tariffa;
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
        List <Boolean> giorni = getGiorni(listaCheckbox);
        Printer.println("   -Giorni selezionati: " + giorni);

        //tariffaSlider---------------------------------------------------
        tariffa = (int) Math.round(this.tariffaSlider.getValue());
        Printer.println("   -Tariffa massima: " + tariffa);

        //istanzio un RipetizioneInfoBean
        ripetizioneInfoBean = new RipetizioneInfoBean(materia, inPresenza, online, luogo, giorni, tariffa, email);

        //istanzio un controller applicativo e gli passo la lista di Bean contenente i risultati della ricerca
        CercaRipetizioneController cercaRipetizioneController = new CercaRipetizioneController();
        risultatiRicercaBean = cercaRipetizioneController.ricercaMethod(ripetizioneInfoBean);


        return risultatiRicercaBean;
    }



    /*---------------------funzione per leggere le checkbox selezionate dall'utente-----------------------------------*/
    public List<Boolean> getGiorni(List<CheckBox> listaCheckbox) {
        List<Boolean> giorni = new ArrayList<>();
        for (CheckBox checkBox : listaCheckbox) {
            giorni.add(checkBox.isSelected());
        }
        return giorni;
    }

    /*-----------------------------funzione che controlla se sono stati selezionati giorni----------------------------*/
    public Boolean giorniVuoti(List<CheckBox> giorni){

        for (CheckBox checkBox : giorni) {
            if (checkBox.isSelected()){
                return false;
            }
        }
        return true;
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
