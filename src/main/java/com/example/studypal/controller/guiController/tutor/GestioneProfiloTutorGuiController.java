package com.example.studypal.controller.guiController.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.RipetizioneInfoBean;
import com.example.studypal.controller.applicationController.tutor.GestioneProfiloTutorController;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GestioneProfiloTutorGuiController extends HomeTutorGui {

    //controller grafico che contiene i metodi di gestione del profilo tutor

    @FXML
    private Label tariffaValue;

    @FXML
    private ComboBox luogoBox;

    @FXML
    private CheckBox inPresenzaBox;

    @FXML
    private CheckBox onlineBox;

    @FXML
    private Slider tariffaSlider;

    @FXML
    private TextField materieField;

    @FXML
    private MenuButton giorniMenu;

    @FXML
    private Label successoModifiche;


    //eredita dal padre un attributo LoggedInUserBean
    public GestioneProfiloTutorGuiController(LoggedInUserBean user){ this.user = user;}


    public void initialize() {

        /*
        TODO: controllare se i campi di infoCorrentiProfilo sono tutti selezionati e gestire il caso..
              perché dovrei voler modificare i miei dati e lasciare alcuni campi vuoti???
              Magari se voglio dare ripetizioni esclusivamente online e non voglio inserire un luogo(?)
        */

        RipetizioneInfoModel infoCorrentiProfilo;
        infoCorrentiProfilo = caricaInformazioniProfilo(user.getEmail());

        /* mostro all'utente le sue informazioni correnti------------------------------------------------------------*/
        inPresenzaBox.setSelected(infoCorrentiProfilo.getInPresenza());
        onlineBox.setSelected(infoCorrentiProfilo.getOnline());
        luogoBox.setPromptText(infoCorrentiProfilo.getLuogo());
        materieField.setText(infoCorrentiProfilo.getMateria());
        tariffaSlider.setValue(infoCorrentiProfilo.getTariffa());
        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> tariffaValue.setText(newValue.intValue() + "€"));
        giorniMenu.setText(infoCorrentiProfilo.getGiorni());

        for (MenuItem item : giorniMenu.getItems()) {
            if (item instanceof CheckMenuItem checkItem) {
                if (infoCorrentiProfilo.getGiorni()!=null && infoCorrentiProfilo.getGiorni().contains(checkItem.getText())) {
                    checkItem.setSelected(true);
                }
            }
        }

        //combobox luogo---------------------------------------------------------------------
        luogoBox.getItems().addAll("Roma", "Milano", "Napoli","Palermo", "Torino");

        //combobox giorni disponibili -------------------------------------------------------
        giorniMenu.setOnAction(event -> {
            if (giorniMenu.isShowing()) {
                giorniMenu.hide();
            } else {
                giorniMenu.show();
            }
        });

        //tariffa slider--------------------------------------------------------------------
        tariffaSlider.setBlockIncrement(1);
        tariffaSlider.setMin(5);
        tariffaSlider.setMax(50);
        tariffaSlider.setShowTickLabels(true);
        tariffaSlider.setShowTickMarks(true);
        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> tariffaValue.setText(newValue.intValue() + "€"));

    }



    public void gestioneProfiloMethod() {
        /*
        prende le informazioni inserite dall'utente e le inserisce in un'istanza di RipetizioneInfoBean
        invia il bean al controller applicativo che popolerà un model per mandarlo al DAO
         */

        String materie;
        boolean inPresenza = false;
        boolean online = false;
        String luogo;
        int tariffa;
        String email = this.user.getEmail(); //questa è l'email del tutor loggato


        //TODO: controllo isempty... necessario davvero qui?

        materie = this.materieField.getText();
        luogo = (String) this.luogoBox.getValue(); //TODO: controllare se c'è un modo migliore rispetto al cast!!
        tariffa = (int) Math.round(this.tariffaSlider.getValue());

        if (this.inPresenzaBox.isSelected()){ inPresenza = true;}
        if (this.onlineBox.isSelected()) { online = true;}

        /*gestione del menubutton dei giorni-------------------------------------------------------------------------*/
        ObservableList<MenuItem> items = giorniMenu.getItems();
        StringBuilder selectedValues = new StringBuilder();
        for (MenuItem item : items) {
            if (item instanceof CheckMenuItem checkMenuItem) {
                if (checkMenuItem.isSelected()) {
                    if (!selectedValues.isEmpty()) {   //prima era selectedValues.length() > 0
                        selectedValues.append(", ");
                    }
                    selectedValues.append(checkMenuItem.getText());
                }
            }
        }
        String giorni = selectedValues.toString();


        //stampo i valori a terminale-----------------------------------------------------------------------------------
        Printer.println("Modifiche desiderate dall'utente " + email);
        Printer.println("    Materie: " + materie);
        Printer.println("    Modalità: " + inPresenza + " " + online);
        Printer.println("    Luogo: " + luogo);
        Printer.println("    Giorni: " + giorni );
        Printer.println("    Tariffa: " + tariffa + "€/h");


        //creo il bean, istanzio il controller applicativo e chiamo il suo metodo---------------------------------------
        RipetizioneInfoBean ripetizioneInfoBean = new RipetizioneInfoBean(materie, inPresenza, online, luogo, giorni, tariffa, email);
        GestioneProfiloTutorController gestioneProfiloTutorController = new GestioneProfiloTutorController();
        gestioneProfiloTutorController.gestioneProfiloMethod(ripetizioneInfoBean);

        //se sono arrivato qui è andato tutto a buon fine e posso comunicarlo all'utente
        successoModifiche.setText("Modifiche avvenute con successo");
    }


    //TODO: si può mettere direttamente in initialize?
    public RipetizioneInfoModel caricaInformazioniProfilo(String email) {

        //metodo che prende le informazioni del tutor e le carica nella pagina di gestione profilo del tutor

        GestioneProfiloTutorController gestioneProfiloTutorController = new GestioneProfiloTutorController();

        return gestioneProfiloTutorController.caricaInformazioniProfilo(email);
    }
}
