package com.example.studypal.DAO;


import com.example.studypal.Query.QueryLogin;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
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

        //PreparedStatement statement;
        Statement stmt;

        //query per verificare credenziali utente
        //String query = "SELECT * FROM utente WHERE email=? AND password=?";

        try{

            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            String email = credenzialiModel.getEmail();
            String password = credenzialiModel.getPassword();

            //stmt = connection.prepareStatement(query);
            //statement.setString(1, credenzialiModel.getEmail());
            //statement.setString(2, credenzialiModel.getPassword());
            // nel try: ResultSet rs = statement.executeQuery()

            //dopo che ho verificato se l'email inserito dall'utente è stata registrata o meno
            try{
                //Printer.println("controllo email");
                ResultSet rs = QueryLogin.checkEmail(stmt, email);
                if (rs == null){
                    throw new UtenteInesistenteException();
                }
            } catch(UtenteInesistenteException e){
                //Printer.println("L'email non è registrata");
                throw new UtenteInesistenteException();
            }


            //verifico i credenziali inseriti dall'utente
            try (ResultSet rs = QueryLogin.loginUser(stmt, email, password)) {

                //Printer.println("---------------------------------------------------------");
                if(!rs.next()) {
                    //Printer.println("Il ResultSet è vuoto.");
                    throw new CredenzialiSbagliateException("Credenziali sbagliate");
                }
                else {
                    userModel.setNome(rs.getString("nome"));
                    userModel.setEmail(rs.getString("email"));
                    //Printer.println("Email: " + userModel.getEmail());
                    userModel.setCognome(rs.getString("cognome"));
                    //Printer.println("Accesso effettuato per l'utente: " + userModel.getNome());
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
        PreparedStatement statement;

        String query = "INSERT INTO utente (email, nome, cognome, password, isTutor) VALUES (?, ?, ?, ?, ?)";

        try {

            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, registrazioneModel.getEmail());
            statement.setString(2, registrazioneModel.getNome());
            statement.setString(3, registrazioneModel.getCognome());
            statement.setString(4, registrazioneModel.getPassword());
            statement.setBoolean(5, registrazioneModel.getRuolo());

            //inseriamo effettivamente l'utente nel database
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }
    }



    //metodo per controllare se l'email fornita al momento della registrazione è già utilizzata
    public void controllaEmailMethod(UserModel registrazioneModel) throws EmailAlreadyInUseException {

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;


        String query = "SELECT * FROM utente WHERE email=?";

        try {

            //accediamo al db dall'unica istanza di connessione
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, registrazioneModel.getEmail());

            rs = statement.executeQuery();

            //se il result set non è vuoto, l'email è già in uso e lanciamo un'eccezione
            if (rs.next()) {
                throw new EmailAlreadyInUseException();
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


