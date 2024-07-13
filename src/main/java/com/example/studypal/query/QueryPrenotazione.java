package com.example.studypal.query;

import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;

import java.sql.SQLException;
import java.sql.Statement;

public class QueryPrenotazione {


    public static void richiediRipetiz(Statement stmt, PrenotazioneModel prenotazioneModel){

        try{

            String emailTutor = prenotazioneModel.getEmailTutor();
            String emailStud = prenotazioneModel.getEmailStudente();
            String materia = prenotazioneModel.getMateria();
            Integer modLez = prenotazioneModel.getModLezione();
            Integer tariffa = prenotazioneModel.getTariffa();
            String giorni = prenotazioneModel.getGiorno();
            String note = prenotazioneModel.getNote();
            String nome = prenotazioneModel.getNome();
            String cognome = prenotazioneModel.getCognome();



            String richiesta = String.format(Query.PRENOTA, emailTutor, emailStud, materia, modLez, tariffa, giorni, note, nome, cognome);
            stmt.executeUpdate(richiesta);


        } catch(SQLException e){
            handleException(e);
        }


    }






    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
    }






}
