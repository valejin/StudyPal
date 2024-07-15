package com.example.studypal.query;

import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryProfilo {

    private QueryProfilo(){}

    public static void modificaProfiloTutor(Statement stmt, RipetizioneInfoModel nuoveInfo){
        String sql;
        try{
            sql = String.format(Query.MODIFICA_PROFILO, nuoveInfo.getTariffa(), nuoveInfo.getLuogo(),
                    nuoveInfo.getMateria(), convertiBool(nuoveInfo.getInPresenza()), convertiBool(nuoveInfo.getOnline()),
                    nuoveInfo.getGiorni(),
                    nuoveInfo.getEmail());
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            handleException(e);
        }
    }

    public static ResultSet caricaInformazioniProfilo(Statement stmt, String email){
        String sql;
        ResultSet rs = null;
        try{
            sql = String.format(Query.CARICA_INFO_PROFILO, email);
            rs = stmt.executeQuery(sql);
        } catch (SQLException e){
            handleException(e);
        }
        return rs;
    }

    public static int convertiBool(Boolean b){
        if (Boolean.TRUE.equals(b)){ return 1;}
        else{ return 0;}
    }

    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryPrenotazione: %s", e.getMessage()));
    }

}
