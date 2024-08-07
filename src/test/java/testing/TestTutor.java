package testing;

import com.example.studypal.dao.PrenotazioneDAO;
import com.example.studypal.dao.RipetizioneInfoDAO;
import com.example.studypal.dao.UserDAO;
import com.example.studypal.dao.UserDAOMySQL;
import com.example.studypal.exceptions.*;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.FactoryDAO;
import com.example.studypal.other.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class TestTutor {

    /** Test di Valentina Jin 0306834*/

    /* Informazioni per test */
    private static final String EMAIL = "testUser@gmail.com";
    private static final String PASSWORD = "testUser";
    private static final String MATERIE = "Analisi 1, Fisica 1";
    private int tariffa = 0;
    private static final String LUOGO = "Milano";
    private static final boolean IN_PRESENZA = true;
    private static final boolean ONLINE = false;
    private static final String GIORNI = "Lunedì, Martedì";
    private static final boolean IS_TUTOR = true;
    private static final String SUFFISSO_EMAIL = "@gmail.com";



    /** Verifico se un utente registrato come Tutor possa modificare le informazioni relative
     * al proprio profilo nel DB */
    @Test
    void testModifiedInfoProfilo() {
        //login del utente già esistente
        loginUser();

        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();
        tariffa = getRandomValue();
        RipetizioneInfoModel ripetizioneInfoModel = new RipetizioneInfoModel();

        ripetizioneInfoModel.setGiorni(GIORNI);
        ripetizioneInfoModel.setTariffa(tariffa);
        ripetizioneInfoModel.setOnline(ONLINE);
        ripetizioneInfoModel.setInPresenza(IN_PRESENZA);
        ripetizioneInfoModel.setMateria(MATERIE);
        ripetizioneInfoModel.setLuogo(LUOGO);
        ripetizioneInfoModel.setEmail(EMAIL);

        // Carico nuove informazioni nel database
        ripetizioneInfoDAO.modificaProfiloTutor(ripetizioneInfoModel);

        RipetizioneInfoModel ripetizioneInfoModel1 = ripetizioneInfoDAO.caricaInformazioniProfilo(EMAIL);

        // Verifico se il valore di TARIFFA è stato modificato con successo
        int rate = ripetizioneInfoModel1.getTariffa();


        // Aggiungo l'asserzione per verificare che la tariffa sia stata modificata correttamente
        Assertions.assertEquals(tariffa, rate, "Modifiche fallite: La tariffa non è stata modificata correttamente nel database");
        //il test fallisce se i due valori non sono uguali

        // Stampo il messaggio di successo
        Printer.println("Modifiche avvenute con successo");

    }


    private void loginUser() {
        UserDAO userDAO = new UserDAOMySQL();
        CredenzialiModel credenzialiModel = new CredenzialiModel();
        credenzialiModel.setEmail(EMAIL);
        credenzialiModel.setPassword(PASSWORD);

        try {
            userDAO.loginMethod(credenzialiModel);
        } catch (CredenzialiSbagliateException | UtenteInesistenteException e) {
            registraTutor();
        }

    }

    public static int getRandomValue() {
        Random random = new Random();
        int min = 15;
        int max = 50;
        return random.nextInt(max - min + 1) + min;
    }

    public void registraTutor(){

        UserModel userModel = new UserModel();

        userModel.setEmail(EMAIL);
        userModel.setNome(PASSWORD +"Nome");
        userModel.setCognome(PASSWORD +"Cognome");
        userModel.setPassword(PASSWORD);
        userModel.setRuolo(IS_TUTOR);

        try {
            UserDAO registrazioneDao = FactoryDAO.getUserDAO();
            registrazioneDao.registrazioneMethod(userModel);
            registrazioneDao.registraTutorMethod(userModel.getEmail(), userModel.getNome(), userModel.getCognome());
        } catch (PersistenzaNonValida e){
            Printer.errorPrint("Persistenza non valida.");
        }

    }






    /** Verifico la creazione di una tupla corrispondente nella tabella 'tutor' nel DB
     * quando un utente si registra come Tutor */
    @Test
    void testRegistrazioneTutor(){

        /* registrazione del tutor con email creato random */

        int res = -1;
        String uniqueUsername = generateRandomUsername(PASSWORD);
        String userEmail = uniqueUsername + SUFFISSO_EMAIL;

        UserModel userModel = new UserModel();
        userModel.setNome(uniqueUsername);
        userModel.setCognome(uniqueUsername);
        userModel.setEmail(userEmail);
        userModel.setPassword(uniqueUsername);
        userModel.setRuolo(IS_TUTOR);

        // UtenteTest viene registrato con lo stesso valore per nome, cognome, password, confermaPassword
        try {
            UserDAO registrazioneDao = FactoryDAO.getUserDAO();
            registrazioneDao.registrazioneMethod(userModel);
            registrazioneDao.registraTutorMethod(userModel.getEmail(), userModel.getNome(), userModel.getCognome());

        } catch (PersistenzaNonValida e) {
            Assertions.fail("Registrazione fallito: " + e.getMessage());
        }

        // controllo l'email se viene registrato, il test fallisce se non viene catturato l'eccezione "EmailAlreadyInUse"
        try{
            UserDAO registrazioneDao = FactoryDAO.getUserDAO();
            registrazioneDao.controllaEmailMethod(userModel);

        } catch (PersistenzaNonValida e) {
            Assertions.fail("Registrazione fallito: " + e.getMessage());
        } catch (EmailAlreadyInUseException e){
            Printer.println("TestUserRandom viene registrato con successo.");
            res = 1;
        }

        Assertions.assertEquals(1, res);
    }


    private String generateRandomUsername(String baseUsername) {
        return baseUsername + System.currentTimeMillis();
    }




    /** Verifica che un utente registrato come Tutor non ha ricevuto nessuna richiesta,
     * per cui quando tenta di prelevare le richieste arrivate, restituisca l'eccezione
     * "NonProduceRisultatoException" */
    @Test
    void testRichiesteArrivate(){

        PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
        int res = -1;

        try {
            prenotazioneDAO.getRichieste(EMAIL, 0);
        } catch (NonProduceRisultatoException e) {
            //il test fallisce se non viene catturato l'eccezione "NonProduceRisultatoException"
            res = 1;
            Printer.println("Non produce risultato da DB.");
        }

        Assertions.assertEquals(1, res);

    }









}
