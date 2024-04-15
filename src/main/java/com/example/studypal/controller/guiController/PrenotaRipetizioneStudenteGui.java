package com.example.studypal.controller.guiController;

import com.example.studypal.bean.BaseInfoBean;
import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.PrenotaRipetizioneController;
import com.example.studypal.other.Printer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.ls.LSOutput;

public class PrenotaRipetizioneStudenteGui extends HomeStudenteGui {


    @FXML
    private TextField cercaMateria;

    @FXML
    private ComboBox luogo;
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


    //eredita dal padre un attributo LoggedInUserBean
    public PrenotaRipetizioneStudenteGui(LoggedInUserBean user) {this.user = user;}



    @FXML
    public void initialize() {
        //materia textfield ----------------------------------------------------------------------


        // Imposta il valore minimo e massimo del Slider

        tariffaSlider.setMin(5);
        tariffaSlider.setMax(40);
        tariffaSlider.setValue(40); // Imposta un valore predefinito(minimo)
        tariffaSlider.setShowTickLabels(true);
        tariffaSlider.setShowTickMarks(true);

        // Per visualizzare dinamicamente il valore di Slider
        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            tariffaValue.setText(Integer.toString(newValue.intValue()) + "€");

        });

        //luogo----------------------------------------------
        luogo.getItems().addAll("Roma", "Milano", "Palermo");


        //combobox giorni disponibili -------------------------------------------------------
        giorno.setOnAction(event -> {
            if (giorno.isShowing()) {
                giorno.hide();
            } else {
                giorno.show();
            }
        });

    }


    public void ricercaMethod(){

        //this.giorno.getItems().isEmpty() &&
        if( !this.tariffaSlider.isValueChanging() &&
                (!this.inPresenza.isSelected() && !this.online.isSelected()) &&
                this.luogo.getSelectionModel().isEmpty() && menuButtonIsEmpty(giorno)

        ){
            //se tutti i campi aggiuntivi sono vuoti, allora la ricerca va fatta solo per materia
            ricercaMateria();
        }else{
            //altrimenti la ricerca avviene anche con i filtri aggiunti
            Printer.println("fai ricerca con filtri");
            ricercaConFiltri();
        }

    }

    public void ricercaMateria(){
        /*
        prende la materia da BEAN
         */

        String materia;

        //TODO: controllo se il campo isEmpty
        materia = this.cercaMateria.getText();

        Printer.println("La materia inserita è: " + materia);

        //prendo un BEAN base e inserisco info
        BaseInfoBean baseInfoBean = new BaseInfoBean(materia);

        PrenotaRipetizioneController prenotaRipetizioneController = new PrenotaRipetizioneController();

        //chiama il controller applicativo e gli passa il BEAN che contiene la materia
        prenotaRipetizioneController.prenotaRipetizioneMethod(baseInfoBean);

    }


    public boolean menuButtonIsEmpty(MenuButton menuButton){
        ObservableList<MenuItem> items = menuButton.getItems();
        Boolean answer = true;

        for (MenuItem item : items) {
            if (item instanceof CheckMenuItem) {
                CheckMenuItem checkItem = (CheckMenuItem) item;
                if (checkItem.isSelected()) {
                    answer = false;
                    return answer;
                }
            }
        }

        return answer;
    }



    public void ricercaConFiltri(){

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
        Printer.println("   -La materia inserita è: " + materia);


        luogo = (String)this.luogo.getValue();
        Printer.println("   -Luogo: " + luogo);

        //checkBox: modalità di lezione ------------------------------------------------
        Printer.print("   -Modalità di lezione: ");
        if(this.inPresenza.isSelected()) {
            inPresenza = true;
            Printer.println("in presenza");
        }

        if(this.online.isSelected()) {
            online = true;
            Printer.println("online");
        }

        //menuButton di giono-------------------------------------------------------
        ObservableList<MenuItem> items = giorno.getItems();
        StringBuilder selectedValues = new StringBuilder();
        for (MenuItem item : items) {
            if (item instanceof CheckMenuItem) {
                CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                if (checkMenuItem.isSelected()) {
                    if (selectedValues.length() > 0) {
                        selectedValues.append(", ");
                    }
                    selectedValues.append(checkMenuItem.getText());
                }
            }
        }
        giorni = selectedValues.toString();
        Printer.println("   -Gioni selezionati: " + giorni);


        //tariffaSlider---------------------------------------------------
        tariffa = (int) Math.round(this.tariffaSlider.getValue());

        //istanzio un RipetizioneInfoBean
        RipetizioneInfoBean ripetizioneInfoBean = new RipetizioneInfoBean(materia, inPresenza, online, luogo, giorni, tariffa, email);

        ripetizioneInfoBean.setEmail(email);
        ripetizioneInfoBean.setMateria(materia);
        ripetizioneInfoBean.setInPresenza(inPresenza);
        ripetizioneInfoBean.setOnline(online);
        ripetizioneInfoBean.setLuogo(luogo);
        ripetizioneInfoBean.setGiorni(giorni);
        ripetizioneInfoBean.setTariffa(tariffa);

        //istanzio un controller applicativo e gli passo il Bean contenente i dati per la ricerca con filtri
        PrenotaRipetizioneController prenotaRipetizioneController = new PrenotaRipetizioneController();
        prenotaRipetizioneController.prenotaRipetizioneMethod(ripetizioneInfoBean);




    }

}
