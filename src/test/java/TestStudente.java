import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.dao.UserDAO;
import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.PersistenzaNonValida;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.other.FactoryDAO;
import com.example.studypal.other.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestStudente {

     /** Elisa Marzioli 0310065 **/

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
}
