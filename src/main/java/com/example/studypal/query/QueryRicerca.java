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
                // luogo selezionato
                sql += String.format(" AND luogo = '%s' AND LOWER(materie) LIKE '%%%s%%'",
                        filtri.getLuogo(), filtri.getMateria());
            } else {
                // se non ci è stato dato un luogo saltiamo il primo filtro
                sql += String.format(" AND LOWER(materie) LIKE '%%%s%%'", filtri.getMateria());
            }

            if (Boolean.TRUE.equals(filtri.getInPresenza())) {
                sql += String.format(" AND inPresenza = true" + GIORNI, '%' + filtri.getGiorni() + '%');
            } else if (Boolean.TRUE.equals(filtri.getOnline())) {
                sql += String.format(" AND webCam = true" + GIORNI, '%' + filtri.getGiorni() + '%');
            } else {
                sql += String.format(GIORNI, '%' + filtri.getGiorni() + '%');
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
