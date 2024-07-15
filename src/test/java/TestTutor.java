import com.example.studypal.dao.RipetizioneInfoDAO;
import com.example.studypal.dao.UserDAO;
import com.example.studypal.dao.UserDAOMySQL;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.RipetizioneInfoModel;
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
    public void testModifiedInfoProfilo() {
        this.loginUser();
        RipetizioneInfoDAO ripetizioneInfoDAO = new RipetizioneInfoDAO();
        this.TARIFFA = getRandomValue();
        RipetizioneInfoModel ripetizioneInfoModel = new RipetizioneInfoModel();
        ripetizioneInfoModel.setGiorni("Lunedì, Martedì");
        ripetizioneInfoModel.setTariffa(this.TARIFFA);
        ripetizioneInfoModel.setOnline(false);
        ripetizioneInfoModel.setInPresenza(true);
        ripetizioneInfoModel.setMateria("Analisi 1, Fisica 1");
        ripetizioneInfoModel.setLuogo("Milano");
        ripetizioneInfoDAO.modificaProfiloTutor(ripetizioneInfoModel);
        RipetizioneInfoModel ripetizioneInfoModel1 = ripetizioneInfoDAO.caricaInformazioniProfilo("testUser@gmail.com");
        int rate = ripetizioneInfoModel1.getTariffa();
        if (this.TARIFFA != rate) {
            System.out.println("Modifiche fallite");
            Assertions.fail("La tariffa non è stata modificata correttamente nel database");
        } else {
            System.out.println("Modifiche avvenute con successo");
        }

    }

    private void loginUser() {
        UserDAO userDAO = new UserDAOMySQL();
        CredenzialiModel credenzialiModel = new CredenzialiModel();
        credenzialiModel.setEmail(EMAIL);
        credenzialiModel.setPassword(PASSWORD);

        try {
            userDAO.loginMethod(credenzialiModel);
        } catch (CredenzialiSbagliateException | UtenteInesistenteException var4) {
            var4.fillInStackTrace();
        }

    }

    public static int getRandomValue() {
        Random random = new Random();
        int min = 15;
        int max = 50;
        return random.nextInt(max - min + 1) + min;
    }
}
