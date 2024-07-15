import com.example.studypal.dao.RipetizioneInfoDAO;
import com.example.studypal.dao.UserDAO;
import com.example.studypal.dao.UserDAOMySQL;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.RipetizioneInfoModel;
import com.example.studypal.other.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TestTutor {
    private final String EMAIL = "testUser@gmail.com";
    private final String PASSWORD = "testUser";
    private final String MATERIE = "Analisi 1, Fisica 1";
    private int TARIFFA = 0;
    private final String LUOGO = "Milano";
    private final boolean IN_PRESENZA = true;
    private final boolean ONLINE = false;
    private final String GIORNI = "Lunedì, Martedì";

    public TestTutor() {
    }

    @Test
    void testModifiedInfoProfilo() {
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
}
