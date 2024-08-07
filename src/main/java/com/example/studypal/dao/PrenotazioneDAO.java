package com.example.studypal.dao;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.exceptions.PrenotazioneConfermataException;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;
import com.example.studypal.query.QueryPrenotazione;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PrenotazioneDAO {

    /*
        DAO che si occupa dell'interazione con il database relativa alla tabella 'richieste'

     */


    /*-------------------------------------------PRENOTAZIONE----------------------------------------------------------*/
    public void prenota(PrenotazioneModel prenotazioneModel) throws SQLException {
        /*
        fa la query per inserire la richiesta di ripetizione nel database
         */
        Connection connection;
        Statement stmt = null;

        try{
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            QueryPrenotazione.richiediRipetiz(stmt, prenotazioneModel);

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            // Chiusura delle risorse
            closeResources(stmt,null);
        }
    }


    List<PrenotazioneModel> risultatiRicerca = new ArrayList<>();



    /*--------------Gestione Prenotazioni (TUTOR): prendere le diverse da DB ------------------------*/
        /*
    una volta la richiesta in attesa viene confermata dal tutor, sparisce dalla lista di richieste arrivate
    */
    public List<PrenotazioneModel> getRichieste(String email, int flag) throws NonProduceRisultatoException{

        //viene passato il userModel per prendere email del tutor
        Statement stmt = null;
        Connection connection = Connect.getInstance().getDBConnection();

        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            handleDAOException(e);
        }



        try (ResultSet rs = QueryPrenotazione.gestisciPrenotazioni(stmt, email, flag)){

            //in base al valore del flag: flag == 0 richieste arrivate; flag == 1 prenotazioni attive;  flag == 2 richieste rifiutate

            if(rs.next()){
                Printer.println("Lista di email richiedenti per il tutor: " + email);

                //prendo email dello studente, materia richiesta, e aggiungo il pulsante VISUALIZZA per ciascun tupla estratta
                do{
                    LoggedInUserBean userTutor = new LoggedInUserBean(rs.getString("emailTutor"), rs.getString("nomeTutor"), rs.getString("cognomeTutor"));

                    List<Integer> valori = new ArrayList<>();
                    valori.add(rs.getInt("modLezione"));
                    valori.add(rs.getInt("tariffa"));
                    valori.add(rs.getInt("stato"));

                    //popolo una nuova istanza di PrenotazioneModel per ritornare al CtlApplicativo
                    PrenotazioneModel risultatoCorrente = new PrenotazioneModel(rs.getInt("idrichieste"), userTutor, rs.getString("emailStudente"), rs.getString("materia"), rs.getString("giorni"), rs.getString("note"), valori);

                    //aggiunggo la tupla in lista dei risultati di ricerca
                    risultatiRicerca.add(risultatoCorrente);

                }while(rs.next());

            }else{
                throw new NonProduceRisultatoException();
            }


        }catch(SQLException e){
            handleDAOException(e);
        }finally {
            // Chiusura delle risorse
            closeResources(stmt,null);
        }

        return risultatiRicerca;
    }






    /* -------------------------------------------------STUDENTE------------------------------------------------------*/
    public List<PrenotazioneModel> richiesteInviate(String email, Integer flag) throws NonProduceRisultatoException {

        /* metodo che riceve la stringa contenente l'email con cui fare la query
           restituisce una lista contenente tutte le richieste di prenotazione inviate ma ancora in attesa di conferma*/

        List<PrenotazioneModel> listaRichieste = new ArrayList<>();

        Connection connection = Connect.getInstance().getDBConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            handleDAOException(e);
        }

        try{
            rs = QueryPrenotazione.gestisciPrenotazioniStudente(stmt, email, flag);
            if (rs.next()){

                do {

                    LoggedInUserBean userTutor = new LoggedInUserBean(rs.getString("emailTutor"), rs.getString("nomeTutor"), rs.getString("cognomeTutor"));

                    List<Integer> valori = new ArrayList<>();
                    valori.add(rs.getInt("modLezione"));
                    valori.add(rs.getInt("tariffa"));
                    valori.add(rs.getInt("stato"));

                    PrenotazioneModel richiesta = new PrenotazioneModel(rs.getInt("idrichieste"), userTutor,
                            rs.getString("emailStudente"), rs.getString("materia"),
                            rs.getString("giorni"), rs.getString("note"), valori);

                    listaRichieste.add(richiesta);

                } while (rs.next());

            } else {
                throw new NonProduceRisultatoException();
            }

        } catch (SQLException e) {
            handleDAOException(e);
        } finally {
            closeResources(stmt, rs);
        }
        return listaRichieste;
    }



    /*------------------------------------CONFERMA/RIFIUTA (TUTOR) ---------------------------------------------------*/
    public void modificaStatoRichiesta(Integer idRichiesta, Integer stato){
        /*
            stato:
                    1 -> conferma
                    2 -> rifiuta
         */

        Connection connection;
        Statement stmt = null;

        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            QueryPrenotazione.modificaStatoRichiesta(stmt, idRichiesta, stato);

        } catch (SQLException e){
            handleDAOException(e);
        } finally {
            closeResources(stmt, null);
        }
    }


    /*---------------------------------------------CANCELLA (STUDENTE)-----------------------------------------------*/
    public void cancellaRichiesta(Integer idRichiesta) throws PrenotazioneConfermataException{

        Connection connection;
        Statement stmt;

        try {
            if (controllaStato(idRichiesta) == 0){ //controllo che sia effettivamente una richiesta in attesa prima di cancellarla

                connection = Connect.getInstance().getDBConnection();
                stmt = connection.createStatement();

                QueryPrenotazione.cancellaRichiesta(stmt, idRichiesta);
            } else if (controllaStato(idRichiesta) == 1){
                //la prenotazione è stata confermata dal tutor nel frattempo
                throw new PrenotazioneConfermataException("Non è stato possibile cancellare la richiesta.");
            }

        } catch(SQLException e){
            handleDAOException(e);
        }
    }


    public int controllaStato(int idRichiesta){
        Connection connection;
        Statement stmt;
        int stato = -1;

        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            stato = QueryPrenotazione.getStatoRichiesta(stmt, idRichiesta);

        } catch(SQLException e){
            handleDAOException(e);
        }
        return stato;
    }


    /*-------------------------------------------RECENSIONE (STUDENTE)------------------------------------------------*/

    public void recensioneMethod(int idRichiesta, int recensione){

        Connection connection;
        Statement stmt;

        try {
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            QueryPrenotazione.recensione(stmt, idRichiesta, recensione);

        } catch(SQLException e){
            handleDAOException(e);
        }
    }



    /** Metodo utilizzato per chiudere le risorse utilizzate */
    private void closeResources(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            handleDAOException(e);
        }
    }


    private void handleDAOException(Exception e) {
        Printer.errorPrint(String.format("PrenotazioneDAO: %s", e.getMessage()));
    }


}
