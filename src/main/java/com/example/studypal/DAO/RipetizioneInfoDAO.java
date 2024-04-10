package com.example.studypal.DAO;

import com.example.studypal.Query.Query;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RipetizioneInfoDAO {

    /*
    DAO che gestisce le interazioni con il db relative alle informazioni di una ripetizione.
    Contiene:
        - metodo che effettua la ricerca di una ripetizione (PrenotaRipetizione)
        - metodo che effettua l'aggiornamento delle informazioni di un profilo tutor (GestioneProfiloTutor)
    In entrambi i casi d'uso viene inviato al DAO un model RipetizioneInfoModel contenente le informazioni necessarie alle query

     */


    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public RipetizioneInfoModel ricercaInfo() {
        /*
        metodo per la ricerca di ripetizioni, restituisce tutti i tutor e rispettive informazioni di ripetizioni disponibiili
        applica i parametri di ricerca ricevuti tramite istanza di RipetizioneInfoModel

        La query cerca nella tabella Tutor
         */


        RipetizioneInfoModel ripetizioneInfoModel = new RipetizioneInfoModel();
        PreparedStatement statement = null;

        //devo prendere materia da model
        //String materia = ripetizioneInfoModel.setMateria();

        //query per la ricerca di Materia
        //"SELECT * FROM tutor where materia=?"
        String query = String.format(Query.RICERCA_MATERIA, materia);

        try {

            Connection connection = Connect.getInstance().getDBConnection();
            statement.executeUpdate(query);


        }catch(SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }


        return ripetizioneInfoModel;
    }

    public void prenotaRipetizione() {
        /*
        La query inserisce tuple nella tabella RipetizioniAttive quando viene effettuata una prenotazione di ripetizione
         */
    }

    public void modificaProfiloTutor(){
        /*
        metodo chiamato dal controller applicativo GestioneProfiloTutorController per modificare i dati di un utente tutor nel database

        Modifica le tuple nella tabella Tutor
         */

    }
}
