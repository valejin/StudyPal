import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.dao.PrenotazioneDAO;
import com.example.studypal.dao.RipetizioneInfoDAO;
import com.example.studypal.dao.UserDAO;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.exceptions.PersistenzaNonValida;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.other.FactoryDAO;
import com.example.studypal.other.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestStudente {

     /* Elisa Marzioli 0310065*/

    private static final String USER_EMAIL = "test@email.com";

    private static final LoggedInUserBean tutorTEST = new LoggedInUserBean("test@tutor.com", "test", "test");
    private static final LoggedInUserBean studenteTEST = new LoggedInUserBean("test@studente.com", "test", "test");

    @Test
    void testControlloCredenziali(){
        /* controlla che venga sollevata l'eccezione CredenzialiSbagliateException durante il Login*/
        int res = -1;

        String PASSWORD = generaPassword();
        /* usiamo sempre la stessa email, generiamo ogni volta una password diversa*/
        try{
            UserDAO userDAO = FactoryDAO.getUserDAO();
            CredenzialiModel credenziali = new CredenzialiModel(USER_EMAIL, PASSWORD);
            userDAO.loginMethod(credenziali);


        } catch (PersistenzaNonValida e){
            Printer.errorPrint("Errore persistenza.");
            res = 0;
        } catch (UtenteInesistenteException e){
            Printer.errorPrint("Errore controllo email.");
            res = 0;
        } catch (CredenzialiSbagliateException e){
            res = 1;
        }
        Assertions.assertEquals(1, res);
    }

    private String generaPassword(){
        return "test" + System.currentTimeMillis();
        //la password corretta nel DB è "test", il test tenta il login con una password sbagliata diversa ad ogni tentativo
    }



    @Test
     void cancellaRichiestaInviata(){
        /* controlla che quando uno studente cancella una richiesta questa effettivamente venga eliminata*/
        List<PrenotazioneModel> richiesteInviate_TEST;

        int res= -1;

        List<Integer> valori = Arrays.asList(0, 0, 1); //1 è lo stato, deve essere una prenotazoione attiva
        PrenotazioneModel richiesta_test = new PrenotazioneModel(0, tutorTEST, studenteTEST.getEmail(), "test", "test", null, valori);

        PrenotazioneDAO dao = new PrenotazioneDAO();
        try{
            dao.prenota(richiesta_test);

            richiesteInviate_TEST = dao.richiesteInviate(studenteTEST.getEmail(), 0);

            dao.cancellaRichiesta(richiesteInviate_TEST.getFirst().getIdRichiesta());

        } catch (SQLException e) {
            res = 0;
        } catch (NonProduceRisultatoException e){
            res = 1;
        }

        Assertions.assertEquals(1, res);

    }
}
