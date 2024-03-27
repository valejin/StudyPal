package com.example.studypal.DAO;

import com.example.studypal.exceptions.RegistrazioneDBException;
import com.example.studypal.model.RegistrazioneModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RegistrazioneDAO {
    //DAO che gestisce l'interazione con il database relativa alla registrazione dell'utente
    //istanzia connessione con db
    //se l'email già esiste lanciamo un'eccezione

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public void registrazioneMethod(RegistrazioneModel registrazioneModel) throws RegistrazioneDBException {

        String path = "jdbc:mysql://localhost:3306/studypal";
        String username = "root";
        String password = "Valentina";
        PreparedStatement statement = null;

        //query per inserire il nuovo utente con i dati ricevuti in RegistrazioneModel
        //però dobbiamo controllare prima che l'email passata dall'utente non sia già presente nel db, perché è una primary key

        String query = "SELECT * FROM utente where email=?";

        try{
            Connection connection = DriverManager.getConnection(path, username, password);
            statement = connection.prepareStatement(query);


        } catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

    }
}
