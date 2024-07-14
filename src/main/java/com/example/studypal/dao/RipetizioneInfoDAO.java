package com.example.studypal.dao;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.model.BaseInfoModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;
import com.example.studypal.query.QueryRicerca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RipetizioneInfoDAO {

    /*
    DAO che gestisce le interazioni con il db relative alle informazioni utili alle informazioni ddelle ripetizioni.
    Contiene:
        - metodo che effettua la ricerca di una ripetizione (PrenotaRipetizione)
        - metodo che effettua l'aggiornamento delle informazioni di un profilo tutor (GestioneProfiloTutor)
    In entrambi i casi d'uso viene inviato al DAO un model RipetizioneInfoModel contenente le informazioni necessarie alle query

     */


    //dichiaro una lista di ripetizioneInfoModel in cui appendo i model popolati con le informazioni del result set della query di ricerca
    List<RipetizioneInfoModel> risultatiRicerca = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(RipetizioneInfoDAO.class.getName());


    /*---------------------------------------------------RICERCA------------------------------------------------------*/

    public List<RipetizioneInfoModel> ricercaMateria(BaseInfoModel baseInfoModel) {
        /*
        metodo per la ricerca di ripetizioni, restituisce tutti i tutor e rispettive informazioni di ripetizioni disponibiili
        applica i parametri di ricerca ricevuti tramite istanza di RipetizioneInfoModel
        La query cerca nella tabella Tutor
         */

        Statement stmt;
        ResultSet rs;

        try {

            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            rs = QueryRicerca.ricercaMateria(stmt, baseInfoModel.getMateria());

            if(rs.next()) {

                do {
                    LoggedInUserBean user = new LoggedInUserBean(rs.getString("email"), rs.getString("nome"),rs.getString("cognome" ));
                    RipetizioneInfoModel risultatoCorrente = new RipetizioneInfoModel(user,
                            rs.getString("materie"), rs.getBoolean("inPresenza"),
                            rs.getBoolean("webcam"), rs.getString("luogo"),
                            rs.getString("giorni"), rs.getInt("tariffa"));

                    risultatiRicerca.add(risultatoCorrente);

                }while (rs.next());

            }

        }catch(SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

        return risultatiRicerca;
    }


    public List<RipetizioneInfoModel> ricercaFiltri(RipetizioneInfoModel ripetizioneInfoModel){

        PreparedStatement statement;
        ResultSet rs;

        String query = "SELECT email, tariffa, luogo, materie, inPresenza, webCam, giorni, nome, cognome FROM tutor where tariffa <= ?";

        if (ripetizioneInfoModel.getLuogo() != null && !ripetizioneInfoModel.getLuogo().isEmpty()) {
            //luogo selezionato
            query += "  AND luogo = ? AND LOWER(materie) LIKE ?";

        }else{
            query += " AND LOWER(materie) LIKE ?"; //se non ci è stato dato un luogo saltiamo il primo filtro
        }
        if (ripetizioneInfoModel.getInPresenza()  && ripetizioneInfoModel.getOnline()){
            query += " AND giorni LIKE ?";
        }else if (Boolean.TRUE.equals(ripetizioneInfoModel.getInPresenza())) {
            query += " AND inPresenza = ?";
            query += " AND giorni LIKE ?";
        }else if (Boolean.TRUE.equals(ripetizioneInfoModel.getOnline())) {
            query += " AND webCam = ?";
            query += " AND giorni LIKE ?";
        } else {
            query += " AND giorni LIKE ?";
        }


        try {
            Connection connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, ripetizioneInfoModel.getTariffa());

            if (ripetizioneInfoModel.getLuogo() != null && !ripetizioneInfoModel.getLuogo().isEmpty()) {
                //devo prendere i filtri da model

                statement.setString(2, ripetizioneInfoModel.getLuogo());
                statement.setString(3, '%' + ripetizioneInfoModel.getMateria() + '%');

                if (ripetizioneInfoModel.getInPresenza() && ripetizioneInfoModel.getOnline()) {
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }else if (Boolean.TRUE.equals(ripetizioneInfoModel.getInPresenza())) {
                    statement.setBoolean(4, ripetizioneInfoModel.getInPresenza());
                    statement.setString(5, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }else if (Boolean.TRUE.equals(ripetizioneInfoModel.getOnline())) {
                    statement.setBoolean(4, ripetizioneInfoModel.getOnline());
                    statement.setString(5, '%' + ripetizioneInfoModel.getGiorni() + '%');
                } else {
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }

            }else {
                //luogo non selezionato
                statement.setString(2, '%' + ripetizioneInfoModel.getMateria() + '%');

                if (ripetizioneInfoModel.getInPresenza() && ripetizioneInfoModel.getOnline()) {
                    statement.setBoolean(3, ripetizioneInfoModel.getInPresenza());
                    statement.setBoolean(4, ripetizioneInfoModel.getOnline());
                    statement.setString(5, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }else if (Boolean.TRUE.equals(ripetizioneInfoModel.getInPresenza())) {
                    statement.setBoolean(3, ripetizioneInfoModel.getInPresenza());
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                    String statementSQL = statement.unwrap(PreparedStatement.class).toString();
                    Printer.println(statementSQL);

                }else if (Boolean.TRUE.equals(ripetizioneInfoModel.getOnline())) {
                    statement.setBoolean(3, ripetizioneInfoModel.getOnline());
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                } else {
                    statement.setString(3, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }
            }

            rs = statement.executeQuery();


            if (rs.next()) {

                Printer.println("La materia che stai cercando è: " + ripetizioneInfoModel.getMateria());

                //stampo email di Tutor che soddisfanno la query
                do {
                    LoggedInUserBean user = new LoggedInUserBean(rs.getString("email"), rs.getString("nome"),rs.getString("cognome" ));
                    RipetizioneInfoModel risultatoCorrente = new RipetizioneInfoModel(user,
                            rs.getString("materie"), rs.getBoolean("inPresenza"),
                            rs.getBoolean("webcam"), rs.getString("luogo"),
                            rs.getString("giorni"), rs.getInt("tariffa"));

                    risultatiRicerca.add(risultatoCorrente);

                } while (rs.next());

            }

        }catch(SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

    return risultatiRicerca;
    }





    /*-------------------------------------------------INFO TUTOR--------------------------------------------------------*/
    public void modificaProfiloTutor(RipetizioneInfoModel ripetizioneInfoModel){

        /*
        metodo chiamato dal controller applicativo GestioneProfiloTutorController
        modifica i dati di un utente tutor nella tabella tutor del database
         */

        Connection connection;
        PreparedStatement statement;
        String query = "UPDATE tutor SET tariffa=?, luogo=?, materie=?, inPresenza=?, webCam=?, giorni=? WHERE email=?";

        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, ripetizioneInfoModel.getTariffa());
            statement.setString(2, ripetizioneInfoModel.getLuogo());
            statement.setString(3, ripetizioneInfoModel.getMateria());
            statement.setBoolean(4, ripetizioneInfoModel.getInPresenza());
            statement.setBoolean(5, ripetizioneInfoModel.getOnline());
            statement.setString(6, ripetizioneInfoModel.getGiorni());
            statement.setString(7, ripetizioneInfoModel.getEmail());

            statement.executeUpdate();
            Printer.println("Profilo dell'utente " + ripetizioneInfoModel.getEmail() + " aggiornato con successo!");
            statement.close();
        } catch (SQLException e) {
            logger.severe("errore in RipetizioneInfoDAO " + e.getMessage());
        }
    }


    public RipetizioneInfoModel caricaInformazioniProfilo(String email){

        RipetizioneInfoModel infoTutor = new RipetizioneInfoModel();
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        String query = "SELECT email, tariffa, luogo, materie, inPresenza, webCam, giorni, nome, cognome FROM tutor WHERE email=?";

        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);

            rs = statement.executeQuery();

            if(!rs.next()){
                Printer.println("TUTOR NON PRESENTE");
                return null;
            } else {

                infoTutor.setEmail(email);
                infoTutor.setTariffa(rs.getInt("tariffa"));
                infoTutor.setMateria(rs.getString("materie"));
                infoTutor.setOnline(rs.getBoolean("webCam"));
                infoTutor.setInPresenza(rs.getBoolean("inPresenza"));
                infoTutor.setGiorni(rs.getString("giorni"));
                infoTutor.setLuogo(rs.getString("luogo"));

                return infoTutor;
            }

        } catch (SQLException e){
            logger.severe("errore in RipetizioneInfoDAO" + e.getMessage());
        }
        return infoTutor;
    }

    private void handleDAOException(Exception e) {
        Printer.errorPrint(String.format("UserDAO: %s", e.getMessage()));
    }

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
}
