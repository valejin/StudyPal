package com.example.studypal.view.first.gui.tutor;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.application.tutor.GestisciPrenotazioniController;
import com.example.studypal.other.Printer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class VisualizzaRichiestaGui extends HomeTutorGui {

    /*
    Controller grafico per mostrare i dettagli di una particolare richiesta arrivata al tutor quando il tutor clicca il
    bottone "Visualizza"
     */

    @FXML
    private Label emailStudente;
    @FXML
    private Label materia;
    @FXML
    private Label modLezione;
    @FXML
    private Label giorni;
    @FXML
    private Label note;

    PrenotazioneBean dettagliRichiesta;

    List<PrenotazioneBean> listRisultati;
    String email;
    String materiaRichiesta;
    String giorno;
    String noteAggiuntive;
    int modalita;

    private static final Logger logger = Logger.getLogger(VisualizzaRichiestaGui.class.getName());

    public VisualizzaRichiestaGui(LoggedInUserBean user, PrenotazioneBean prenotazioneBean, List<PrenotazioneBean> list){
        this.user = user;
        this.dettagliRichiesta = prenotazioneBean;
        this.listRisultati = list;
    }


    public void initialize(){
        email = dettagliRichiesta.getEmailStudente();
        materiaRichiesta = dettagliRichiesta.getMateria();
        modalita = dettagliRichiesta.getModLezione();

        giorno = dettagliRichiesta.getGiorno();
        if (giorno != null && !giorno.isEmpty()) {
            giorni.setText(giorno);
        }else{
            giorni.setText("Non specificato");
        }

        noteAggiuntive = dettagliRichiesta.getNote();


    /* -------------------setto i label nell'interfaccia utente---------------------------------*/
        emailStudente.setText(email);
        materia.setText(materiaRichiesta);

        if(modalita == 0) {
            modLezione.setText("Non specificato");
        } else if (modalita == 1) {
            modLezione.setText("In presenza");
        } else if (modalita == 2) {
            modLezione.setText("Online");
        }
        
        if (!noteAggiuntive.isEmpty()){
            note.setText("\"" + noteAggiuntive + "\"");
        }
    }







/*------------------tasto di ritorno alla pagina delle richieste arrivate-----------------*/
    public void goToRichiesteArrivate(){
        try {
            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/tutor/richiesteArrivate.fxml"));
            loader.setControllerFactory(c -> new RichiesteTutorGui(this.user, 0));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) emailStudente.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in VisualizzaRichiesteGui (caricamento pagina) " + e.getMessage());
        }
    }


/*----------------------tasto di ritorno alla pagina di prenotazioni attive-------------------------*/
public void goToPrenotazioniAttive() {

    try {
        FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/tutor/prenotazioniAttive.fxml"));
        loader.setControllerFactory(c -> new RichiesteTutorGui(user, 1));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) emailStudente.getScene().getWindow();
        stage.setScene(scene);
    } catch (IOException e) {
        logger.severe("errore in GestisciPrenotazioniGuiController " + e.getMessage());
    }
}


/*----------------------tasto di ritorno alla pagina di prenotazioni rifiutate-------------------------*/
public void goToPrenotazioniRifiutate(){

    //metodo che porta alle prenotazioni rifiutate
    try {
        FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/tutor/prenotazioniRifiutate.fxml"));
        loader.setControllerFactory(c -> new RichiesteTutorGui(user, 2));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) emailStudente.getScene().getWindow();
        stage.setScene(scene);
    } catch (IOException e) {
        logger.severe("errore in GestisciPrenotazioniGuiController " + e.getMessage());
    }
}


/*-------------------CONFERMA/RIFIUTA---------------------------------------------------------------------------------*/
    public void confermaRichiestaMethod(){
        /*conferma la richiesta arrivata (dovrà essere modificato lo stato nella tabella delle richieste)*/

        //istanzio controller applicativo
        Printer.println("IDRICHIESTA: " + this.dettagliRichiesta.getIdRichiesta());
        GestisciPrenotazioniController gestisciPrenotazioniController = new GestisciPrenotazioniController(user);
        gestisciPrenotazioniController.modificaStatoRichiesta(this.dettagliRichiesta.getIdRichiesta(),1);
        caricaConferma();
        goToRichiesteArrivate();

    }

    public void rifiutaRichiestaMethod(){
        /*rifiuta la richiesta arrivata (dovrà essere modificato lo stato nella
        tabella delle richieste)*/

        Printer.println("IDRICHIESTA: " + this.dettagliRichiesta.getIdRichiesta());
        GestisciPrenotazioniController gestisciPrenotazioniController = new GestisciPrenotazioniController(user);
        gestisciPrenotazioniController.modificaStatoRichiesta(this.dettagliRichiesta.getIdRichiesta(),2);
        caricaRifiuta();
        goToRichiesteArrivate();

    }

    public void caricaConferma(){
        try{

            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/confermaPOPUP.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Popup");
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch(IOException e){
            logger.severe("Errore caricamento popup di conferma" + e.getMessage());
        }

    }

    public void caricaRifiuta(){
        try{

            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/rifiutaPOPUP.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Popup");
            popupStage.setScene(scene);

            popupStage.showAndWait();
        } catch(IOException e){
            logger.severe("Errore caricamento popup di rifiuto" + e.getMessage());
        }
    }
}
