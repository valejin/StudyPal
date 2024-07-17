package testing;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.dao.PrenotazioneDAO;
import com.example.studypal.dao.UserDAO;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.NonProduceRisultatoException;
import com.example.studypal.exceptions.PersistenzaNonValida;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.PrenotazioneModel;
import com.example.studypal.model.UserModel;
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
    private String password;
    private static final String TEST = "test";
    private static final LoggedInUserBean tutorTEST = new LoggedInUserBean("test@tutor.com", "test", "test");


    /** Verifica la corretta gestione dell’inserimento di credenziali sbagliate durante il login. **/

    @Test
    void testControlloCredenziali(){

        int res = -1;
        password = generaPassword();
        /* usiamo sempre la stessa email, generiamo ogni volta una password diversa*/

        try{
            //prima controllo se l'utente esiste, e in caso contrario lo registro
            registraTester();

            //se non viene lanciata nessuna eccezione durante l'esecuzione del metodo registraTutor allora posso tentare il login
            UserDAO dao = FactoryDAO.getUserDAO();
            CredenzialiModel credenziali = new CredenzialiModel(USER_EMAIL, password);
            dao.loginMethod(credenziali);
        } catch (PersistenzaNonValida e){
            Printer.errorPrint("Errore persistenza.");
            res = 0;
        } catch (CredenzialiSbagliateException e){
            res = 1;
        } catch (UtenteInesistenteException e){
            res = 2;
        }
        Assertions.assertEquals(1, res);
    }

    private String generaPassword(){
        return "test" + System.currentTimeMillis();
        //la password corretta nel DB è "test", il test tenta il login con una password sbagliata diversa a ogni tentativo
    }

    public void registraTester() throws PersistenzaNonValida, CredenzialiSbagliateException{

        /*registra l'utente se non esiste già*/

        UserDAO dao = FactoryDAO.getUserDAO();
        try {
            CredenzialiModel credenziali = new CredenzialiModel(USER_EMAIL, password);
            dao.loginMethod(credenziali);

        } catch (CredenzialiSbagliateException e){
            throw e;

        } catch (UtenteInesistenteException e){

            UserModel userModel = new UserModel();

            userModel.setEmail(USER_EMAIL);
            userModel.setNome("test");
            userModel.setCognome("test");
            userModel.setPassword("test");
            userModel.setRuolo(false);

            dao.registrazioneMethod(userModel);
        }

    }


    /** Verifica la corretta gestione del tentativo di login di un utente non registrato **/
    @Test
    void testUtenteInesistente(){

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



    /** testa che la prenotazione effettuata venga correttamente registrata **/
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
