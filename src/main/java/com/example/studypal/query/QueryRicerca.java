package com.example.studypal.query;

import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryRicerca {

    QueryRicerca(){}

    private static final String GIORNI =  " AND giorni LIKE '%s'";


    public static ResultSet ricercaMateria(Statement stmt, String materia){

        String sql;
        ResultSet rs = null;
        try{
            sql = String.format(Query.RICERCA_MATERIA, '%' + materia + '%');
            rs = stmt.executeQuery(sql);
        }catch (SQLException e){
            handleException(e);
        }
        return rs;
    }

    public static ResultSet ricercaFiltri(Statement stmt, RipetizioneInfoModel filtri){
        String sql;
        ResultSet rs = null;
        try{

            sql = String.format(Query.RICERCA_FILTRI, filtri.getTariffa());

            if (filtri.getLuogo() != null && !filtri.getLuogo().isEmpty()) {
                //luogo selezionato
                sql += "  AND luogo = '%s' AND LOWER(materie) LIKE '%s'";
                sql = String.format(sql, filtri.getLuogo(), '%' + filtri.getMateria() + '%');

            }else{
                sql += " AND LOWER(materie) LIKE '%s'"; //se non ci è stato dato un luogo saltiamo il primo filtro
                sql = String.format(sql, '%' + filtri.getMateria() + '%');
            }

            if (Boolean.TRUE.equals(filtri.getInPresenza())) {
                sql += " AND inPresenza = '%b'";
                sql += GIORNI;
                sql = String.format(sql,filtri.getInPresenza(), '%' + filtri.getGiorni() + '%');
            }else if (Boolean.TRUE.equals(filtri.getOnline())) {
                sql += " AND webCam = '%b'";
                sql += GIORNI;
                sql = String.format(sql,filtri.getOnline(), '%' + filtri.getGiorni() + '%');
            } else {
                sql += GIORNI;
                sql = String.format(sql, '%' + filtri.getGiorni() + '%');
            }

            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            handleException(e);
        }
        return rs;
    }



    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryPrenotazione: %s", e.getMessage()));
    }


}
