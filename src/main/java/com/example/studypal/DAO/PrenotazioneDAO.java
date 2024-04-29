package com.example.studypal.DAO;

import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PrenotazioneDAO {

    /*
        DAO che si occupa della gestione delle prenotazioni di ripetizioni. Contiene i seguenti metodi:
            - prenota: inserisce le richieste di prenotazioni nella tabella "richieste"

           Tutor:
            - Gestione Prenotazione:
                - Prenotazioni attive: prendere le prenotazioni attive dal DB, ovvero quelle già confermate dal tutor
                - Richieste arrivate: prendere le richieste di prenotazione inviate dagli studenti dal DB
     */
    private static final Logger logger = Logger.getLogger(PrenotazioneDAO.class.getName());
    public void prenota(PrenotazioneModel prenotazioneModel) throws SQLException {
        /*
        fa la query per inserire la richiesta di ripetizione nel database
         */
        Connection connection;
        PreparedStatement statement;
        //non ci interessa resultSet

        Printer.println("Stiamo inserendo la richiesta nel nostro database.");
        String query = "INSERT INTO richieste (emailTutor, emailStudente, materia, modLezione, tariffa, giorni, note) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, prenotazioneModel.getEmailTutor());
            statement.setString(2, prenotazioneModel.getEmailStudente());
            statement.setString(3, prenotazioneModel.getMateria());
            statement.setInt(4, prenotazioneModel.getModLezione());
            statement.setInt(5, prenotazioneModel.getTariffa());
            statement.setString(6, prenotazioneModel.getGiorno());
            statement.setString(7, prenotazioneModel.getNote());

            //eseguo
            statement.execute();

            System.out.println("Ho eseguito la query. Controlla il db per vedere se la prenotazione è stata correttamente inserita.");

        } catch (SQLException e) {
            logger.severe("errore in prenotazioneDAO " + e.getMessage());
            throw e;
            //todo: ??? è necessario rimandarla al controller applicativo?? In teoria questa si verifica solo in caso di errore vero e proprio (dato che è un inserimento)
        }
    }


    List<PrenotazioneModel> risultatiRicerca = new ArrayList<>();

    //Gestione Prenotazioni (TUTOR): prendere le richieste arrivate da DB
    public List<PrenotazioneModel> richiesteArrivate(PrenotazioneModel prenotazioneModel) throws NonProduceRisultatoException{
        //viene passato il userModel per prendere email del tutor

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        Printer.println("Cerco le Richieste di prenotazione");

        //query per la ricerca di email del tutor nella lista di tutte le richieste
        String query = "SELECT * FROM richieste WHERE emailTutor = ?";

        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, prenotazioneModel.getEmailTutor());

            rs = statement.executeQuery();

            if(rs.next()){
                Printer.println("Sono arrivate le seguente richieste per il tutor" + prenotazioneModel.getEmailTutor());

                //prendo email dello studente, materia richiesta, e aggiungo il pulsante VISUALIZZA per ciascun tupla estratta
                do{
                    //popolo una nuova istanza di PrenotazioneModel per ritornare al CtlApplicativo
                    PrenotazioneModel risultatoCorrente = new PrenotazioneModel();
                    risultatoCorrente.setEmailStudente(rs.getString("emailStudente"));
                    risultatoCorrente.setMateria(rs.getString("materia"));

                    //aggiunggo la tupla in lista dei risultati di ricerca
                    risultatiRicerca.add(risultatoCorrente);

                }while(rs.next());

            }else{
                throw new NonProduceRisultatoException();
            }


        }catch(SQLException e){
            Printer.println("Non ci sono le richieste arrivate.");
            logger.severe("errore in PrenotazioneDAO " + e.getMessage());
        }

        return risultatiRicerca;
    }





}
