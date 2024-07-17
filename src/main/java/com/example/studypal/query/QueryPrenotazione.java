package com.example.studypal.query;

import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryPrenotazione {

    private QueryPrenotazione(){}


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



    public static ResultSet gestisciPrenotazioni(Statement stmt, String email, int flag){

        String sql = null;
        ResultSet rs = null;
        try{
            if(flag == 0) {
                sql = String.format(Query.RICHIESTE_IN_ATTESA, email);
            } else if(flag == 1){
            //qui ho le richieste confermate =>prenotazioni attive
                sql = String.format(Query.PRENOTAZIONI_ATTIVE, email);
            }else if(flag == 2){
                sql = String.format(Query.RICHIESTE_RIFIUTATE, email);
            }

            rs = stmt.executeQuery(sql);

        } catch (SQLException e){
            handleException(e);
        }
        return rs;
    }

    public static ResultSet gestisciPrenotazioniStudente(Statement stmt, String email, int flag){

        String sql = null;
        ResultSet rs = null;
        try{
            if(flag == 0) {
                sql = String.format(Query.RICHIESTE_INVIATE, email);
            } else if(flag == 1){
                //qui ho le richieste confermate =>prenotazioni attive
                sql = String.format(Query.PRENOTAZIONI_ATTIVE_S, email);
            }else if(flag == 2){
                sql = String.format(Query.RICHIESTE_RIFIUTATE_S, email);
            }

            rs = stmt.executeQuery(sql);

        } catch (SQLException e){
            handleException(e);
        }
        return rs;
    }

    public static void modificaStatoRichiesta(Statement stmt,Integer idRichiesta, Integer stato){

        String sql;

        try{
            if (stato == 0){
                sql = String.format(Query.CONFERMA_RICHIESTA, idRichiesta);
            } else {
                sql = String.format(Query.RIFIUTA_RICHIESTA, idRichiesta);
            }
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            handleException(e);
        }
    }

    public static int getStatoRichiesta(Statement stmt, int idRichiesta){
        String sql;
        ResultSet rs = null;
        int stato = -1;

        try{

            sql = String.format(Query.STATO_RICHIESTA, idRichiesta);

            rs = stmt.executeQuery(sql);
            rs.next();
            stato = rs.getInt("stato");

        } catch (SQLException e){
            handleException(e);
        }
        return stato;
    }

    public static void cancellaRichiesta(Statement stmt, Integer idRichiesta){
        String sql;

        try{
            sql = String.format(Query.CANCELLA_RICHIESTA, idRichiesta);
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            handleException(e);
        }
    }

    public static void recensione(Statement stmt, int idRichiesta, int recensione){
        String sql;

        try{
            sql = String.format(Query.RECENSIONE,recensione, idRichiesta);
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            handleException(e);
        }
    }


    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryPrenotazione: %s", e.getMessage()));
    }


}
