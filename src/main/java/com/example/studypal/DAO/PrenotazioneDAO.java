package com.example.studypal.DAO;

import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PrenotazioneDAO {

    /*
        DAO che si occupa della gestione delle prenotazioni di ripetizioni. Contiene i seguenti metodi:
            - prenota: inserisce le richieste di prenotazioni nella tabella "richieste"
            -
     */
    private static final Logger logger = Logger.getLogger(PrenotazioneDAO.class.getName());
    public void prenota(PrenotazioneModel prenotazioneModel) throws SQLException {
        /*
        fa la query per inserire la richiesta di ripetizione nel database
         */
        Connection connection;
        PreparedStatement statement;
        //non ci interessa resultSet

        Printer.println("Stiamo inserendo la richiesta nel nostro database.");
        String query = "INSERT INTO richieste (emailTutor, emailStudente, materia, modLezione, tariffa, giorni, note) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, prenotazioneModel.getEmailTutor());
            statement.setString(2, prenotazioneModel.getEmailStudente());
            statement.setString(3, prenotazioneModel.getMateria());
            statement.setInt(4, prenotazioneModel.getModLezione());
            statement.setInt(5, prenotazioneModel.getTariffa());
            statement.setString(6, prenotazioneModel.getGiorno());
            statement.setString(7, prenotazioneModel.getNote());

            //eseguo
            statement.execute();

            System.out.println("Ho eseguito la query. Controlla il db per vedere se la prenotazione è stata correttamente inserita.");

        } catch (SQLException e) {
            logger.severe("errore in prenotazioneDAO " + e.getMessage());
            throw e;
            //todo: ??? è necessario rimandarla al controller applicativo?? In teoria questa si verifica solo in caso di errore vero e proprio (dato che è un inserimento)
        }
    }

    public List<PrenotazioneModel> richiesteInviate(String email) {

        /* metodo che riceve la stringa contenente l'email con cui fare la query
           restituisce una lista contenente tutte le richieste di prenotazione inviate ma ancora in attesa di conferma*/

        List<PrenotazioneModel> listaRichieste = new ArrayList<>();

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        String query = "SELECT * FROM richieste WHERE emailStudente=?";

        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);

            rs = statement.executeQuery();

            if(!rs.next()) {
                Printer.println("Nessuna richiesta in attesa di conferma per l'account " + email + ".");
                return null; //todo BRUTTO! Dovremmo lanciare un'eccezione
            }

            do {

                PrenotazioneModel richiesta = new PrenotazioneModel(rs.getString("emailTutor"),
                        rs.getString("emailStudente"), rs.getString("materia"),
                        rs.getInt("modLezione"), rs.getInt("tariffa"),
                        rs.getString("giorno"), rs.getString("note"));

                listaRichieste.add(richiesta);


            } while (rs.next());

            System.out.println("dao: preso la lista di richieste in attesa");

        } catch (SQLException e) {
            Printer.println("Errore in PrenotazioneDAO (metodo: richiesteInviate)");
        }
        return listaRichieste;
    }
}
