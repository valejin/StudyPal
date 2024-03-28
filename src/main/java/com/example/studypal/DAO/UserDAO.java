package com.example.studypal.DAO;


import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.RegistrazioneDBException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.RegistrazioneModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.exceptions.LoginDBException;
import com.example.studypal.other.Connect;

import java.sql.*;
import java.util.logging.Logger;


public class UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());


    public UserModel loginMethod(CredenzialiModel credenzialiModel) throws LoginDBException{
        UserModel userModel = new UserModel();

        String path = "jdbc:mysql://localhost:3306/studypal";
        String username = "root";
        String password = "Valentina";
        PreparedStatement statement = null;

        //query per verificare credenziali utente
        String query = "SELECT * FROM utente where email=? AND password=?";

        try{

            Connection connection = DriverManager.getConnection(path, username, password);
            statement = connection.prepareStatement(query);
            statement.setString(1, credenzialiModel.getEmail());
            statement.setString(2, credenzialiModel.getPassword());

            try (ResultSet rs = statement.executeQuery()) {

                if(!rs.next()) {
                   System.out.println("Il ResultSet è vuoto.");
                    throw new LoginDBException(0);
                }
                else {
                    userModel.setNome(rs.getString("nome"));
                    userModel.setEmail(rs.getString("email"));
                    System.out.println(userModel.getEmail());
                    userModel.setCognome(rs.getString("cognome"));
                    System.out.println("Accesso effettuato per l'utente: " + userModel.getNome());
                }
            }

        }catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

        return userModel;
    }

    //metodo per la registrazione
    public void registrazioneMethod(UserModel registrazioneModel) {

        Connection connection;
        PreparedStatement statement = null;
        ResultSet rs = null;
        System.out.println("preparato la statement");

        String query = "INSERT INTO utente (email, nome, cognome, password) VALUES (?, ?, ?, ?)";

        try {

            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, registrazioneModel.getEmail());
            statement.setString(2, registrazioneModel.getNome());
            statement.setString(3, registrazioneModel.getCognome());
            statement.setString(4, registrazioneModel.getPassword());

            System.out.println("preso la query");
            //inseriamo effettivamente l'utente nel database
            statement.executeUpdate();
            System.out.println("inserito la query");

        } catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }
    }



    //metodo per controllare se l'email fornita al momento della registrazione è già utilizzata
    public void controllaEmailMethod(RegistrazioneModel registrazioneModel) throws EmailAlreadyInUseException, RegistrazioneDBException {

        Connection connection;
        PreparedStatement statement = null;
        ResultSet rs = null;


        String query = "SELECT * FROM utente where email=?";

        try{

            //accediamo al db dall'unica istanza di connessione
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, registrazioneModel.getEmail());

            //carico nella variabile rs il result set della query
            rs = statement.executeQuery(query);

            //se il result set non è vuoto, l'email è già in uso e lanciamo un'eccezione
            if (rs.next()) {
                throw new EmailAlreadyInUseException();
            }

        } catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

    }


}


