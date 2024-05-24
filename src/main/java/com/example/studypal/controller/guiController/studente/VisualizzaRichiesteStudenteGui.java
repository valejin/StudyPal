package com.example.studypal.controller.guiController.studente;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.applicationController.studente.GestisciPrenotazioniStudenteController;
import com.example.studypal.controller.guiController.tutor.VisualizzaRichiestaGui;
import com.example.studypal.other.Printer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class VisualizzaRichiesteStudenteGui extends HomeStudenteGui {
    public Text titolo;
    /* controller grafico delle varie pagine "visualizza dettagli richiesta/prenotazione"*/

    @FXML
    public Button gestisciPrenotazioni;
    @FXML
    Label nome, cognome;
    @FXML
    Label emailTutor;
    @FXML
    private Label materia;
    @FXML
    private Label modLezione;
    @FXML
    private Label giorni;
    @FXML
    private Label note;
    PrenotazioneBean dettagliRichiesta;
    List <PrenotazioneBean> listaRichieste;
    String email, materiaRichiesta, giorno, noteAggiuntive;
    int modalita;

    private static final Logger logger = Logger.getLogger(VisualizzaRichiesteStudenteGui.class.getName());

    public VisualizzaRichiesteStudenteGui(LoggedInUserBean user, PrenotazioneBean prenotazioneSelezionata, List<PrenotazioneBean> listaRichieste){
        this.user = user;
        this.dettagliRichiesta = prenotazioneSelezionata;
        this.listaRichieste = listaRichieste;
    }

    public void initialize(){

        email = dettagliRichiesta.getEmailTutor();
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
        emailTutor.setText(email);
        materia.setText(materiaRichiesta);

        if(modalita == 0) {
            modLezione.setText("Non specificato");
        } else if (modalita == 1) {
            modLezione.setText("In presenza");
        } else if (modalita == 2) {
            modLezione.setText("Online");
        }

        note.setText(noteAggiuntive);
    }


    /*----------------------------------FLAG 0: RICHIESTE INVIATE-----------------------------------------------------*/
    public void goToRichiesteInviate(){
        try {
            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/studente/richiesteInviate.fxml"));
            loader.setControllerFactory(c -> new RichiesteStudenteGui(this.user, 0));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) emailTutor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in VisualizzaRichiesteStudenteGui (caricamento pagina R.I.+) " + e.getMessage());
        }
    }
    /*----------------------------------FLAG 1: PRENOTAZIONI ATTIVE -----------------------------------------------------*/
    public void goToPrenotazioniAttive(){
        try {
            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/studente/prenotazioniAttiveStudente.fxml"));
            loader.setControllerFactory(c -> new RichiesteStudenteGui(this.user, 1));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) emailTutor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in VisualizzaRichiesteStudenteGui (caricamento pagina - P.A.) " + e.getMessage());
        }
    }

    public void goToRichiesteRifiutate(){
        try {
            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/studente/richiesteRifiutate.fxml"));
            loader.setControllerFactory(c -> new RichiesteStudenteGui(this.user, 2));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) emailTutor.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in VisualizzaRichiesteStudenteGui (caricamento pagina R.R.) " + e.getMessage());
        }
    }

    public void cancellaRichiesta() {

        Printer.println("Verrà annullato l'invio della richiesta di prenotazione di ripetizione.");

        /* qui dobbiamo prendere l'id della richiesta e poi semplicemente andare nel dao ad eliminarla con una query specifica*/

        GestisciPrenotazioniStudenteController gestisciPrenotazioniStudenteController = new GestisciPrenotazioniStudenteController(user);
        gestisciPrenotazioniStudenteController.cancellaRichiesta(this.dettagliRichiesta.getIdRichiesta());
        //carico popup di notifica di avvenuta cancellazione
        confermaCancellazione();
        goToRichiesteInviate();
    }

    public void confermaCancellazione(){
        try{

            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/cancellazionePOPUP.fxml"));
            Parent parent = loader.load();
            // Crea una nuova scena per il popup
            Scene scene = new Scene(parent);

            // Crea una nuova finestra di dialogo modale per il popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Popup");
            popupStage.setScene(scene);

            // Visualizza il popup
            popupStage.showAndWait();
        } catch(IOException e){
            logger.severe("Errore caricamento popup di cancellazione" + e.getMessage());
        }
    }

    /*-----------------------------------------------RECENSIONE-------------------------------------------------------*/
    public void recensioneMethod(){

        try{

            FXMLLoader loader = new FXMLLoader(VisualizzaRichiestaGui.class.getResource("/com/example/studypal/view/recensionePOPUP.fxml"));
            Parent parent = loader.load();
            // Crea una nuova scena per il popup
            Scene scene = new Scene(parent);

            // Crea una nuova finestra di dialogo modale per il popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Popup");
            popupStage.setScene(scene);

            // Visualizza il popup
            popupStage.showAndWait();
        } catch(IOException e){
            logger.severe("Errore caricamento popup di recensione" + e.getMessage());
        }

    }
}
