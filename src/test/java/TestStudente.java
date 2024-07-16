import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.dao.PrenotazioneDAO;
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
import java.util.Arrays;
import java.util.List;

class TestStudente {

     /** Elisa Marzioli 0310065 **/

    private static final String USER_EMAIL = "test@email.com";
    private static final String TEST = "test";
    private static final LoggedInUserBean tutorTEST = new LoggedInUserBean("test@tutor.com", "test", "test");

    @Test
    void testControlloCredenziali(){
        /* controlla che venga sollevata l'eccezione CredenzialiSbagliateException durante il Login*/
        int res = -1;

        String password = generaPassword();
        /* usiamo sempre la stessa email, generiamo ogni volta una password diversa*/
        try{
            UserDAO userDAO = FactoryDAO.getUserDAO();
            CredenzialiModel credenziali = new CredenzialiModel(USER_EMAIL, password);
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
        //la password corretta nel DB è "test", il test tenta il login con una password sbagliata diversa a ogni tentativo
    }



    @Test
    void testUtenteInesistente(){
        /* controlla che l'utente con cui si effettua il login sia effettivamente registrato*/
        int res = -1;
        try {
            UserDAO userDAO = FactoryDAO.getUserDAO();
            CredenzialiModel credenziali = new CredenzialiModel(generaEmail(),"test" );

            userDAO.loginMethod(credenziali);

        } catch (PersistenzaNonValida | CredenzialiSbagliateException e){
            res = 0;
        } catch (UtenteInesistenteException e){
            res = 1;
        }
        Assertions.assertEquals(1, res);
    }

    public String generaEmail(){
        return "test" + System.currentTimeMillis() + "@email.com";
    }

    @Test
    void controlloPrenotazione(){
        /* faccio una prenotazione di test e controllo che venga registrata*/
        List<Integer> valori = Arrays.asList(0,0,0);
        PrenotazioneModel richiesta = new PrenotazioneModel(0, tutorTEST, USER_EMAIL,TEST, TEST,null,valori);

        try{
            PrenotazioneDAO dao = new PrenotazioneDAO();
            dao.prenota(richiesta);
            List<PrenotazioneModel> richieste = dao.richiesteArrivate(tutorTEST.getEmail(), 0);

            //cancello per testing futuri
            dao.cancellaRichiesta(richieste.getFirst().getIdRichiesta());

            Assertions.assertEquals(1, richieste.size());
        } catch (SQLException | NonProduceRisultatoException e) {
            Assertions.fail();
        }
    }
}
