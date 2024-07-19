package com.example.studypal.dao;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.model.BaseInfoModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;
import com.example.studypal.query.QueryProfilo;
import com.example.studypal.query.QueryRicerca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RipetizioneInfoDAO {

    /*
    DAO che gestisce le interazioni con il db relative alle informazioni utili alle ripetizioni.
    Contiene:
        - metodo che effettua la ricerca di una ripetizione (PrenotaRipetizione)
        - metodo che effettua l'aggiornamento delle informazioni di un profilo tutor (GestioneProfiloTutor)
    In entrambi i casi d'uso viene inviato al DAO un model RipetizioneInfoModel contenente le informazioni necessarie alle query

     */


    //dichiaro una lista di ripetizioneInfoModel in cui appendo i model popolati con le informazioni del result set della query di ricerca
    List<RipetizioneInfoModel> risultatiRicerca = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(RipetizioneInfoDAO.class.getName());

    private static final String MATERIE = "materie";
    private static final String  IN_PRESENZA = "inPresenza";
    private static final String  WEBCAM = "webcam";
    private static final String  LUOGO = "luogo";
    private static final String  GIORNI = "giorni";
    private static final String  TARIFFA = "tariffa";

    /*---------------------------------------------------RICERCA------------------------------------------------------*/

    public List<RipetizioneInfoModel> ricercaMateria(BaseInfoModel baseInfoModel) {
        /*
        metodo per la ricerca di ripetizioni, restituisce tutti i tutor e rispettive informazioni di ripetizioni
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
                            rs.getString(MATERIE), rs.getBoolean(IN_PRESENZA),
                            rs.getBoolean(WEBCAM), rs.getString(LUOGO),
                            rs.getString(GIORNI), rs.getInt(TARIFFA));

                    risultatiRicerca.add(risultatoCorrente);

                }while (rs.next());

            }

        }catch(SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

        return risultatiRicerca;
    }


    public List<RipetizioneInfoModel> ricercaFiltri(RipetizioneInfoModel ripetizioneInfoModel){

        Statement stmt;
        ResultSet rs;

        try {
            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            rs = QueryRicerca.ricercaFiltri(stmt, ripetizioneInfoModel);

            if (rs.next()) {

                do {
                    LoggedInUserBean user = new LoggedInUserBean(rs.getString("email"), rs.getString("nome"),rs.getString("cognome" ));
                    RipetizioneInfoModel risultatoCorrente = new RipetizioneInfoModel(user,
                            rs.getString(MATERIE), rs.getBoolean(IN_PRESENZA),
                            rs.getBoolean(WEBCAM), rs.getString(LUOGO),
                            rs.getString(GIORNI), rs.getInt(TARIFFA));

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
        Statement stmt;

        try{
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();
            QueryProfilo.modificaProfiloTutor(stmt, ripetizioneInfoModel);

        } catch (SQLException e) {
            logger.severe("errore in RipetizioneInfoDAO " + e.getMessage());
        }
    }


    public RipetizioneInfoModel caricaInformazioniProfilo(String email){

        RipetizioneInfoModel infoTutor = new RipetizioneInfoModel();
        Connection connection;
        Statement stmt = null;
        ResultSet rs = null;


        try{
            connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            rs = QueryProfilo.caricaInformazioniProfilo(stmt, email);

            if(!rs.next()){
                Printer.println("TUTOR NON PRESENTE");
                return null;
            } else {

                infoTutor.setEmail(email);
                infoTutor.setTariffa(rs.getInt(TARIFFA));
                infoTutor.setMateria(rs.getString(MATERIE));
                infoTutor.setOnline(rs.getBoolean(WEBCAM));
                infoTutor.setInPresenza(rs.getBoolean(IN_PRESENZA));
                infoTutor.setGiorni(rs.getString(GIORNI));
                infoTutor.setLuogo(rs.getString(LUOGO));

                return infoTutor;
            }

        } catch (SQLException e){
            logger.severe("errore in RipetizioneInfoDAO" + e.getMessage());
        } finally {
            closeResources(stmt, rs);
        }
        return infoTutor;
    }

    private void handleDAOException(Exception e) {
        logger.severe(e.getMessage());
        Printer.errorPrint(String.format("UserDAOMySQL: %s", e.getMessage()));
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
