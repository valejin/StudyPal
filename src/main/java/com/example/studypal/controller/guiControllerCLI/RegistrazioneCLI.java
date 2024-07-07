package com.example.studypal.controller.guiControllerCLI;

import com.example.studypal.bean.RegistrazioneUserBean;
import com.example.studypal.controller.applicationController.RegistrazioneController;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.EmailNonValidaException;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RegistrazioneCLI extends AbstractState {
    /* stato concreto relativo alla registrazione, parte del pattern State */

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void action(StateMachineImpl contextSM){
        //metodo effettivo per la registrazione

        Printer.println("   Nome: ");
        String nome = scanner.next();

        Printer.println("   Cognome: ");
        String cognome = scanner.next();

        Printer.print("   Email: ");
        String email = scanner.next();

        try{
            isValidEmail(email);
        } catch(EmailNonValidaException e){
            Printer.errorPrint("Email non valida");
            return;
        }

        Printer.print("   Password: ");
        String password = scanner.next();

        Printer.print("   Conferma password: ");
        String confermaPassword = scanner.next();

        boolean sbagliato = true;
        boolean isTutor = false;

        while(sbagliato){

            Printer.println("   Ruolo[tutor/studente]: ");
            String ruolo = scanner.next();

            switch (ruolo) {
                case ("tutor"):
                    isTutor = true;
                    sbagliato = false;
                    break;
                case ("studente"):
                    sbagliato = false;
                    break;
                default:
                    Printer.println("Input invalido!");
                    break;
            }
        }

        try {
            //inserisco gli input ottenuti in BEAN
            RegistrazioneUserBean registrazioneUserBean = new RegistrazioneUserBean(email, nome, cognome, isTutor, password, confermaPassword);

            registrazioneUserBean.setNome(nome);
            registrazioneUserBean.setCognome(cognome);
            registrazioneUserBean.setEmail(email);
            registrazioneUserBean.setPassword(password);
            registrazioneUserBean.setConfermaPassword(confermaPassword);
            registrazioneUserBean.setRuolo(isTutor);

            //istanzio un controller applicativo e gli passo il bean contenente i dati per registrare l'utente
            RegistrazioneController registrazioneController = new RegistrazioneController();
            registrazioneController.registrazioneMethod(registrazioneUserBean);

            Printer.println("---------------------------------------------------------");
            Printer.println("L'utente è stato correttamente registrato.");
            //TRANSITION!!!!

        }catch(EmailAlreadyInUseException e){
            Printer.errorPrint("Email è già in uso");

        }

    }


    /*--------------------------------------------- PATTERN STATE ---------------------------------------------*/

    @Override
    public void entry(StateMachineImpl contextSM){
        /* capire se è necessaria qualche inizializzazione per lo stato*/
        stampaBenvenuto();
    }

    @Override
    public void exit(StateMachineImpl contextSM){
        //qui dobbiamo specificare azioni particolari relative all'uscita da questo stato
        Printer.println("Verrete reindirizzati alla Home");
    }

    @Override
    public void stampaBenvenuto(){
        Printer.println("-------------- Registrazione --------------");
        Printer.println("Inserite le informazioni necessarie per la registrazione.");
    }

    @Override
    public void mostraMenu(){
        Printer.println("1. Conferma"); //in questo caso facciamo il login in automatico
        Printer.println("2. Indietro");
        Printer.println("0. Esci");
        Printer.print("Opzione scelta: ");
    }


    /*--------------------------------------ALTRO----------------------------------------*/

    //funzione per il controllo della forma di email-------------------------------------------------
    public static void isValidEmail(String email) throws EmailNonValidaException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailNonValidaException();
        }
    }
}
