package com.example.studypal.query;

import com.example.studypal.other.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryRicerca {

    public static ResultSet ricercaMateria(Statement stmt, String materia){

        String sql = null;
        ResultSet rs = null;
        try{
            sql = String.format(Query.RICERCA_MATERIA, '%' + materia + '%');
            rs = stmt.executeQuery(sql);
        }catch (SQLException e){
            handleException(e);
        }
        return rs;
    }

    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryPrenotazione: %s", e.getMessage()));
    }

}
