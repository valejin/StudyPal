package com.example.studypal.DAO;


import com.example.studypal.Query.Query;
import com.example.studypal.Query.QueryLogin;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.EmailNonValidaException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;

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
            try{
                ResultSet rs = QueryLogin.checkEmail(stmt, email);
                if (rs == null){
                    throw new UtenteInesistenteException();
                }
            } catch(UtenteInesistenteException e){
                throw new UtenteInesistenteException();
            }


            //verifico i credenziali inseriti dall'utente
            try (ResultSet rs = QueryLogin.loginUser(stmt, email, password)) {

                //Printer.println("---------------------------------------------------------");
                if(!rs.next()) {
                    throw new CredenzialiSbagliateException("Credenziali sbagliate");
                }
                else {
                    userModel.setNome(rs.getString("nome"));
                    userModel.setEmail(rs.getString("email"));
                    userModel.setCognome(rs.getString("cognome"));
                    userModel.setRuolo(rs.getBoolean("isTutor"));

                    /*
                    if(!userModel.getRuolo()){
                        Printer.println("L'utente è iscritto come: Studente");
                    }else{
                        Printer.println("L'utente è iscritto come: Tutor");
                    }
                    */
                }
            }

        }catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
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

        Connection connection;
        boolean rs;
        Statement stmt;


        try {

            //accediamo al db dall'unica istanza di connessione
            connection = Connect.getInstance().getDBConnection();

            stmt = connection.createStatement();
            String email = registrazioneModel.getEmail();


            try{
                rs = QueryLogin.emailReg(stmt, email);

                //se il result set non è vuoto, l'email è già in uso e lanciamo un'eccezione
                if (rs) {
                    throw new EmailAlreadyInUseException();
                }

            } catch(EmailAlreadyInUseException e){
                Printer.println("L'email è già registrata");
                throw e;
            }


        } catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

    }

    public void registraTutorMethod(String email, String nome, String cognome) {

        //crea una tupla nella tabella tutor... DEVE ESSERE INVOCATO SOLO PER I TUTOR!!!

        Connection connection;
        PreparedStatement statement;

        String query = "INSERT INTO tutor (email, tariffa, luogo, materie, inPresenza, webCam, giorni, nome, cognome) VALUES (?, NULL, NULL, NULL, NULL, NULL, NULL, ?, ?)";

        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, nome);
            statement.setString(3, cognome);


            statement.executeUpdate();
            //Printer.println("---------------------------------------------------------");
            //Printer.println("tutor registrato");
        } catch (SQLException e) {
            logger.severe("errore in UserDAO registrazioneTutor " + e.getMessage());

        }
    }

}


