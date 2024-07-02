package com.example.studypal.Query;

import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.other.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class QueryLogin {

    public static ResultSet checkEmail(Statement stmt, String email) throws UtenteInesistenteException {

        try{
            String sql = String.format(Query.RICERCA_EMAIL, email);
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()){
                throw new UtenteInesistenteException();
            }
            return rs;

        } catch (SQLException e){
            throw new UtenteInesistenteException();
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
}
