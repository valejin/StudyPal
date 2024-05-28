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
    private ComboBox<String> luogoBox;

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
    RipetizioneInfoModel infoCorrentiProfilo;


    public void initialize() {
        
        this.infoCorrentiProfilo = caricaInformazioniProfilo(user.getEmail());

        /* mostro all'utente le sue informazioni correnti------------------------------------------------------------*/
        inPresenzaBox.setSelected(this.infoCorrentiProfilo.getInPresenza());
        onlineBox.setSelected(this.infoCorrentiProfilo.getOnline());
        luogoBox.setPromptText(this.infoCorrentiProfilo.getLuogo());
        materieField.setText(this.infoCorrentiProfilo.getMateria());
        tariffaSlider.setValue(this.infoCorrentiProfilo.getTariffa());
        tariffaSlider.valueProperty().addListener((observable, oldValue, newValue) -> tariffaValue.setText(newValue.intValue() + "€"));
        giorniMenu.setText(this.infoCorrentiProfilo.getGiorni());

        for (MenuItem item : giorniMenu.getItems()) {
            if (item instanceof CheckMenuItem checkItem && infoCorrentiProfilo.getGiorni()!=null && this.infoCorrentiProfilo.getGiorni().contains(checkItem.getText())) {
                    checkItem.setSelected(true);
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
        if (this.infoCorrentiProfilo.getTariffa() != null){
            tariffaValue.setText(this.infoCorrentiProfilo.getTariffa() + "€");
        }
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

        materie = this.materieField.getText();
        tariffa = (int) Math.round(this.tariffaSlider.getValue());

        if (this.luogoBox.getValue() == null){
            luogo = this.infoCorrentiProfilo.getLuogo();
        } else {
            luogo = this.luogoBox.getValue();
        }


        if (this.inPresenzaBox.isSelected()){ inPresenza = true;}
        if (this.onlineBox.isSelected()) { online = true;}

        /*gestione del menubutton dei giorni-------------------------------------------------------------------------*/
        String giorni = getGiorni();


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

    private String getGiorni() {
        ObservableList<MenuItem> items = giorniMenu.getItems();
        StringBuilder selectedValues = new StringBuilder();
        for (MenuItem item : items) {
            if (item instanceof CheckMenuItem checkMenuItem && checkMenuItem.isSelected()) {
                    if (!selectedValues.isEmpty()) {   //prima era selectedValues.length() > 0
                        selectedValues.append(", ");
                    }
                    selectedValues.append(checkMenuItem.getText());
                }

        }
        return selectedValues.toString();
    }


    private RipetizioneInfoModel caricaInformazioniProfilo(String email) {

        //metodo che prende le informazioni del tutor e le carica nella pagina di gestione profilo del tutor
        GestioneProfiloTutorController gestioneProfiloTutorController = new GestioneProfiloTutorController();
        return gestioneProfiloTutorController.caricaInformazioniProfilo(email);
    }
}
