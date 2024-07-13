package com.example.studypal.dao;

import com.example.studypal.other.Printer;
import com.example.studypal.query.QueryLogin;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.Connect;

import java.sql.*;
import java.util.logging.Logger;


public class UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

//-----------------------------------------------------------------------------------------------
    public UserModel loginMethod(CredenzialiModel credenzialiModel) throws CredenzialiSbagliateException, UtenteInesistenteException{

        UserModel userModel = new UserModel();

        Statement stmt;

        try{

            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            String email = credenzialiModel.getEmail();
            String password = credenzialiModel.getPassword();


            //dopo che ho verificato se l'email inserito dall'utente è stata registrata o meno
            QueryLogin.checkEmail(stmt, email);

            //verifico i credenziali inseriti dall'utente
            try (ResultSet rs = QueryLogin.loginUser(stmt, email, password)) {

                if(!rs.next()) {
                    throw new CredenzialiSbagliateException("Credenziali sbagliate");
                }
                else {
                    userModel.setNome(rs.getString("nome"));
                    userModel.setEmail(rs.getString("email"));
                    userModel.setCognome(rs.getString("cognome"));
                    userModel.setRuolo(rs.getBoolean("isTutor"));
                }
            }

        }catch (SQLException e) {
            logger.severe("Errore in userDAO " + e.getMessage());
        }catch (UtenteInesistenteException e){
            throw new UtenteInesistenteException();
        }

        return userModel;
    }


    //metodo per la registrazione in DB-----------------------------------------------------------------------------
    public void registrazioneMethod(UserModel registrazioneModel) {

        Connection connection;
        Statement stmt;

        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            QueryLogin.registerUser(stmt, registrazioneModel);

        } catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());

        }
    }



    //metodo per controllare se l'email fornita al momento della registrazione è già utilizzata
    public void controllaEmailMethod(UserModel registrazioneModel) throws EmailAlreadyInUseException {

        Connection connection = null;
        Statement stmt = null;


        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            String email = registrazioneModel.getEmail();

            // Controlla se l'email è già in uso nel database
            boolean emailInUse = QueryLogin.emailReg(stmt, email);

            if (emailInUse) {
                throw new EmailAlreadyInUseException();
            }

        } catch (SQLException e) {
            logger.severe("Errore in userDAO: " + e.getMessage());
            // Gestione dell'eccezione SQL, se necessario
        } finally {
            // Chiudi le risorse in finally block per garantire la pulizia
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.severe("Errore durante la chiusura dello statement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.severe("Errore durante la chiusura della connessione: " + e.getMessage());
                }
            }
        }

    }

    public void registraTutorMethod(String email, String nome, String cognome) {

        //crea una tupla nella tabella tutor... DEVE ESSERE INVOCATO SOLO PER I TUTOR!!!

        Connection connection;
        Statement stmt = null;


        try{
            connection = Connect.getInstance().getDBConnection();

            stmt = connection.createStatement();
            QueryLogin.registraTutor(stmt, email, nome, cognome);


        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,null);
        }
    }







    private void handleDAOException(Exception e) {
        Printer.errorPrint(String.format("UserDAO: %s", e.getMessage()));
    }


    private void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            handleDAOException(e);
        }
    }





}


