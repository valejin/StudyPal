package com.example.studypal.query;

import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class QueryLogin {

    //controllo email inserito nel LOGIN
    public static void checkEmail(Statement stmt, String email) throws UtenteInesistenteException {

        try{
            String sql = String.format(Query.RICERCA_EMAIL, email);
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()){
                throw new UtenteInesistenteException();
            }

        } catch (SQLException e){
            throw new UtenteInesistenteException();
        }
    }


    //controllo email inserito nella REGISTRAZIONE
    public static boolean emailReg(Statement stmt, String email) throws EmailAlreadyInUseException {

        try{
            String sql = String.format(Query.RICERCA_EMAIL, email);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()){
                throw new EmailAlreadyInUseException();
            }
            return false;
        }catch (SQLException e){
            throw new EmailAlreadyInUseException();
        }
    }




    public static ResultSet loginUser(Statement stmt, String email, String password) throws CredenzialiSbagliateException{
        try{
            String sql = String.format(Query.VERIFICA_USER, email, password);
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new CredenzialiSbagliateException("");
        }
    }




    public static void registerUser(Statement stmt, UserModel registrazioneModel){
        try{

            String email = registrazioneModel.getEmail();
            String nome = registrazioneModel.getNome();
            String cognome = registrazioneModel.getCognome();
            String password = registrazioneModel.getPassword();
            Boolean ruolo = registrazioneModel.getRuolo();



            // Esegui prima l'inserimento nella tabella 'utente'
            String sql = String.format(Query.REGISTRAZIONE, email, nome, cognome, password, ruolo);
            stmt.executeUpdate(sql);

        }catch (SQLException e){
            Printer.errorPrint(String.format("QueryLogin: %s", e.getMessage()));

        }

    }

    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
    }




}



