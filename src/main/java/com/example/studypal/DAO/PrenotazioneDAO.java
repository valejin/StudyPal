package com.example.studypal.DAO;

import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PrenotazioneDAO {

    /*
        DAO che si occupa della gestione delle prenotazioni di ripetizioni. Contiene i seguenti metodi:
            - prenota: inserisce le richieste di prenotazioni nella tabella "richieste"
            -
     */
    private static final Logger logger = Logger.getLogger(PrenotazioneDAO.class.getName());
    public void prenota() {
        /*
        fa la query per inserire la prenotazione di ripetizione nel database
         */
        Connection connection;
        PreparedStatement statement;
        //non ci interessa resultSet

        Printer.println("Stiamo inserendo la richiesta nel nostro database.");
        String query = "INSERT INTO richieste (emailTutor, emailStudente, materia, modLezione, tariffa, giorni, note) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            //todo: finire di preparare lo statement con i dati presi dal model datoci dal controller applicativo
            //statement.setString(1, );

        } catch (SQLException e) {
            logger.severe("errore in prenotazioneDAO " + e.getMessage());
        }
    }
}
