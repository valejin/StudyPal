import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.controller.application.RegistrazioneController;
import com.example.studypal.controller.application.tutor.GestisciPrenotazioniController;
import com.example.studypal.dao.RipetizioneInfoDAO;
import com.example.studypal.dao.UserDAO;
import com.example.studypal.dao.UserDAOMySQL;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.PersistenzaNonValida;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.FactoryDAO;
import com.example.studypal.other.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestTutor {

    /* Informazioni per test */
    private final String EMAIL = "testUser@gmail.com";
    private final String PASSWORD = "testUser";
    private final String MATERIE = "Analisi 1, Fisica 1";
    private int TARIFFA = 0;
    private final String LUOGO = "Milano";
    private final boolean IN_PRESENZA = true;
    private final boolean ONLINE = false;
    private final String GIORNI = "Lunedì, Martedì";
    private final boolean IS_TUTOR = true;
    private static final int RANDOM_BOUND = 10000;  // Cambia questo valore per regolare l'intervallo dei numeri randomici
    private final String SUFFISSO_EMAIL = "@gmail.com";





    public TestTutor() {
    }



    /** Verifico se un utente registrato come Tutor possa modificare le informazioni relative al proprio profilo nel DB */
    @Test
    void testModifiedInfoProfilo() {
        //login del utente già esistente
        loginUser();

        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();
        TARIFFA = getRandomValue();
        RipetizioneInfoModel ripetizioneInfoModel = new RipetizioneInfoModel();

        ripetizioneInfoModel.setGiorni(GIORNI);
        ripetizioneInfoModel.setTariffa(TARIFFA);
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
        Assertions.assertEquals(TARIFFA, rate, "Modifiche fallite: La tariffa non è stata modificata correttamente nel database");

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
            //e.fillInStackTrace();
            Assertions.fail("Login fallito: " + e.getMessage());
        }

    }

    public static int getRandomValue() {
        Random random = new Random();
        int min = 15;
        int max = 50;
        return random.nextInt(max - min + 1) + min;
    }



    //test su conferma richiesta





    /** Verifico la creazione di una tupla corrispondente nella tabella tutor nel DB quando un utente si registra come Tutor */
    @Test
    public void testRegistrazioneTutor(){

        /* registrazione del tutor con email creato random */

        int res = -1;
        String baseUsername = PASSWORD;
        String uniqueUsername = generateUniqueUsername(baseUsername);
        String userEmail = uniqueUsername + SUFFISSO_EMAIL;

        UserModel userModel = new UserModel();
        userModel.setNome(uniqueUsername);
        userModel.setCognome(uniqueUsername);
        userModel.setEmail(userEmail);
        userModel.setPassword(uniqueUsername);
        userModel.setRuolo(IS_TUTOR);

        // Utente test viene registrato con lo stesso valore per nome, cognome, password, confermaPassword
        try {
            UserDAO registrazioneDao = FactoryDAO.getUserDAO();
            registrazioneDao.registrazioneMethod(userModel);
            registrazioneDao.registraTutorMethod(userModel.getEmail(), userModel.getNome(), userModel.getCognome());

        } catch (PersistenzaNonValida e) {
            Assertions.fail("Registrazione fallito: " + e.getMessage());
        } 

        // controllo l'email se viene registrato
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



    public static String generateUniqueUsername(String baseUsername) {
        Random random = new Random();
        int randomNumber = random.nextInt(RANDOM_BOUND);
        return baseUsername + randomNumber;
    }














}
