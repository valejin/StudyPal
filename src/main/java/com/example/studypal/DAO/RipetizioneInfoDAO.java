package com.example.studypal.DAO;

import com.example.studypal.exceptions.MateriaNonTrovataException;
import com.example.studypal.model.BaseInfoModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.Connect;
import com.example.studypal.other.Printer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RipetizioneInfoDAO {

    /*
    DAO che gestisce le interazioni con il db relative alle informazioni utili alle prenotazioni di ripetizioni.
    Contiene:
        - metodo che effettua la ricerca di una ripetizione (PrenotaRipetizione)
        - metodo che effettua l'aggiornamento delle informazioni di un profilo tutor (GestioneProfiloTutor)
    In entrambi i casi d'uso viene inviato al DAO un model RipetizioneInfoModel contenente le informazioni necessarie alle query

     */


    //dichiaro una lista di ripetizioneInfoModel in cui appendo i model popolati con le informazioni del result set della query di ricerca
    List<RipetizioneInfoModel> risultatiRicerca = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    public List<RipetizioneInfoModel> ricercaMateria(BaseInfoModel baseInfoModel) throws MateriaNonTrovataException {
        /*
        metodo per la ricerca di ripetizioni, restituisce tutti i tutor e rispettive informazioni di ripetizioni disponibiili
        applica i parametri di ricerca ricevuti tramite istanza di RipetizioneInfoModel
        La query cerca nella tabella Tutor
         */


        PreparedStatement statement = null;
        ResultSet rs = null;

        System.out.println("Ricerca materia: ");

        //query per la ricerca di Materia
        String query = "SELECT * FROM tutor where LOWER(materie) LIKE ?";

        try {

            Connection connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            //devo prendere materia da model
            statement.setString(1, '%' + baseInfoModel.getMateria() + '%');

            rs = statement.executeQuery();

            if(rs.next()) {

                Printer.println("La materia che stai cercando è: " + baseInfoModel.getMateria());

                //stampo email di Tutor che soddisfanno la query
                Printer.println("Email tutor che soddisfano la ricerca: ");
                do {
                    RipetizioneInfoModel risultatoCorrente = new RipetizioneInfoModel(rs.getString("nome"), rs.getString("cognome"),
                            rs.getString("materie"), rs.getBoolean("inPresenza"),
                            rs.getBoolean("webcam"), rs.getString("luogo"),
                            rs.getString("giorni"), rs.getInt("tariffa"), rs.getString("email"));

                    //DEBUG
                    System.out.println("    nome: " + risultatoCorrente.getNome());
                    System.out.println("    cognome: " + risultatoCorrente.getCognome());
                    System.out.println("    materie: " + risultatoCorrente.getMaterie());
                    System.out.println("    lezioni in presenza: " + risultatoCorrente.getInPresenza());
                    System.out.println("    lezioni online: " + risultatoCorrente.getOnline());
                    System.out.println("    luogo: " + risultatoCorrente.getLuogo());
                    System.out.println("    giorni disponibili: " + risultatoCorrente.getGiorni());
                    System.out.println("    tariffa: " + risultatoCorrente.getTariffa() + "€/h");
                    System.out.println("    email: " + risultatoCorrente.getEmail());
                    System.out.println("------------------------------------------------");
                    risultatiRicerca.add(risultatoCorrente);

                }while (rs.next());

            }else{
                throw new MateriaNonTrovataException();
            }

        }catch(SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }


        return risultatiRicerca;
    }


    public List<RipetizioneInfoModel> ricercaFiltri(RipetizioneInfoModel ripetizioneInfoModel) throws MateriaNonTrovataException{

        PreparedStatement statement = null;
        ResultSet rs = null;

        UserModel userModel = new UserModel();

        String query = "SELECT * FROM tutor where tariffa <= ?";

        if (ripetizioneInfoModel.getLuogo() != null && !ripetizioneInfoModel.getLuogo().isEmpty()) {
            Printer.println("luogo selezionato");
            query += "  AND luogo = ? AND LOWER(materie) LIKE ?";

        }else{
            Printer.println("luogo non selezionato");
            query += " AND LOWER(materie) LIKE ?"; //se non ci è stato dato un luogo saltiamo il primo filtro
        }
        if (ripetizioneInfoModel.getInPresenza()  && ripetizioneInfoModel.getOnline()){
            Printer.println("si cercano sia in presenza sia online");
            query += " AND giorni LIKE ?";
        }else if (ripetizioneInfoModel.getInPresenza()) {
            Printer.println("si cercano ripetizioni solo in presenza");
            query += " AND inPresenza = ?";
            query += " AND giorni LIKE ?";
        }else if (ripetizioneInfoModel.getOnline()) {
            Printer.println("si cercano ripetizioni solo online");
            query += " AND webCam = ?";
            query += " AND giorni LIKE ?";
        } else {
            Printer.println("non ci interessa se online o in presenza");
            query += " AND giorni LIKE ?";
        }

        //query per la ricerca di Materia
        //query = "SELECT * FROM tutor where tariffa <= ? AND luogo = ? AND LOWER(materie) LIKE ? AND inPresenza = ? AND webCam = ? AND giorni LIKE ?";


        try {
            Connection connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, ripetizioneInfoModel.getTariffa());

            if (ripetizioneInfoModel.getLuogo() != null && !ripetizioneInfoModel.getLuogo().isEmpty()) {
                //devo prendere i filtri da model

                statement.setString(2, ripetizioneInfoModel.getLuogo());
                statement.setString(3, '%' + ripetizioneInfoModel.getMateria() + '%');

                if (ripetizioneInfoModel.getInPresenza() && ripetizioneInfoModel.getOnline()) {
                    //System.out.println("si cercano sia in presenza sia online");
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }else if (ripetizioneInfoModel.getInPresenza()) {
                    //System.out.println("si cercano ripetizioni solo in presenza");
                    statement.setBoolean(4, ripetizioneInfoModel.getInPresenza());
                    statement.setString(5, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }else if (ripetizioneInfoModel.getOnline()) {
                    //System.out.println("si cercano ripetizioni solo online");
                    statement.setBoolean(4, ripetizioneInfoModel.getOnline());
                    statement.setString(5, '%' + ripetizioneInfoModel.getGiorni() + '%');
                } else {
                    //System.out.println("non ci interessa se online o in presenza");
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }

            }else {
                Printer.println("luogo non selezionato");
                statement.setString(2, '%' + ripetizioneInfoModel.getMateria() + '%');

                if (ripetizioneInfoModel.getInPresenza() && ripetizioneInfoModel.getOnline()) {
                    //System.out.println("online e in presenza");
                    statement.setBoolean(3, ripetizioneInfoModel.getInPresenza());
                    statement.setBoolean(4, ripetizioneInfoModel.getOnline());
                    statement.setString(5, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }else if (ripetizioneInfoModel.getInPresenza()) {
                    //System.out.println("solo in presenza");
                    statement.setBoolean(3, ripetizioneInfoModel.getInPresenza());
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                    String statementSQL = statement.unwrap(PreparedStatement.class).toString();
                    System.out.println(statementSQL);

                }else if (ripetizioneInfoModel.getOnline()) {
                    //System.out.println("solo online");
                    statement.setBoolean(3, ripetizioneInfoModel.getOnline());
                    statement.setString(4, '%' + ripetizioneInfoModel.getGiorni() + '%');
                } else {
                    //System.out.println("non ci interessa");
                    statement.setString(3, '%' + ripetizioneInfoModel.getGiorni() + '%');
                }
                String statementSQL = statement.unwrap(PreparedStatement.class).toString();
            }



            rs = statement.executeQuery();
            System.out.println("query preparato");

            /*  usati per debug
            System.out.println(ripetizioneInfoModel.getTariffa());
            System.out.println(ripetizioneInfoModel.getLuogo());
            System.out.println(ripetizioneInfoModel.getMateria());
            System.out.println(ripetizioneInfoModel.getInPresenza());
            System.out.println(ripetizioneInfoModel.getOnline());
            System.out.println(ripetizioneInfoModel.getGiorni());
            */


            if (rs.next()) {

                Printer.println("La materia che stai cercando è: " + ripetizioneInfoModel.getMateria());

                //stampo email di Tutor che soddisfanno la query
                Printer.println("Email tutor che soddisfano la ricerca: ");
                do {

                    RipetizioneInfoModel risultatoCorrente = new RipetizioneInfoModel(rs.getString("nome"), rs.getString("cognome"),
                            rs.getString("materie"), rs.getBoolean("inPresenza"),
                            rs.getBoolean("webcam"), rs.getString("luogo"),
                            rs.getString("giorni"), rs.getInt("tariffa"), rs.getString("email"));

                        //System.out.println("MATERIE MESSE NEL MODEL: " + risultatoCorrente.getMaterie());

                    /*DEBUG*/
                    System.out.println("    nome: " + risultatoCorrente.getNome());
                    System.out.println("    cognome: " + risultatoCorrente.getCognome());
                    System.out.println("    materie: " + risultatoCorrente.getMaterie());
                    System.out.println("    lezioni in presenza: " + risultatoCorrente.getInPresenza());
                    System.out.println("    lezioni online: " + risultatoCorrente.getOnline());
                    System.out.println("    luogo: " + risultatoCorrente.getLuogo());
                    System.out.println("    giorni disponibili: " + risultatoCorrente.getGiorni());
                    System.out.println("    tariffa: " + risultatoCorrente.getTariffa() + "€/h");
                    System.out.println("    email: " + risultatoCorrente.getEmail());
                    System.out.println("------------------------------------------------");

                    risultatiRicerca.add(risultatoCorrente);

                } while (rs.next());

            } else {
                throw new MateriaNonTrovataException();
            }

        }catch(SQLException e) {
            logger.severe("errore in userDAO " + e.getMessage());
        }

    return risultatiRicerca;
    }




    //-----------------------------------------------------------------------------------------------------------------------
    public void prenotaRipetizione() {
        /*
        La query inserisce tuple nella tabella RipetizioniAttive quando viene effettuata una prenotazione di ripetizione
         */
    }


    //--------------------------------------------------------------------------------------------------------------------------------------
    public void modificaProfiloTutor(RipetizioneInfoModel ripetizioneInfoModel){

        /*
        metodo chiamato dal controller applicativo GestioneProfiloTutorController
        modifica i dati di un utente tutor nella tabella tutor del database
         */

        Connection connection;
        PreparedStatement statement = null;
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

        } catch (SQLException e) {
            logger.severe("errore in RipetizioneInfoDAO " + e.getMessage());
        }
    }

    public RipetizioneInfoModel caricaInformazioniProfilo(String email){

        //Printer.println("cercando il tutor " + email);
        RipetizioneInfoModel infoTutor = new RipetizioneInfoModel();
        Connection connection;
        PreparedStatement statement = null;
        ResultSet rs = null;

        String query = "SELECT * FROM tutor WHERE email=?";

        try{
            connection = Connect.getInstance().getDBConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, email);

            rs = statement.executeQuery();

            //TODO: capire se è veramente necessario.. è possibile che arrivati a questo punto l'email non sia presente nel database? Non credo
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
}
