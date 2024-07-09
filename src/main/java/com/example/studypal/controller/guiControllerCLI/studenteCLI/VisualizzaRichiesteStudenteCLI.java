package com.example.studypal.controller.guiControllerCLI.studenteCLI;

import com.example.studypal.bean.LoggedInUserBean;
import com.example.studypal.bean.PrenotazioneBean;
import com.example.studypal.controller.guiController.studente.VisualizzaRichiesteStudenteGui;
import com.example.studypal.other.Printer;
import com.example.studypal.pattern.state.AbstractState;
import com.example.studypal.pattern.state.StateMachineImpl;

import java.util.List;

public class VisualizzaRichiesteStudenteCLI extends AbstractState {

    LoggedInUserBean user;
    PrenotazioneBean dettagliRichiesta;
    List<PrenotazioneBean> listRisultati;
    int flag;


    public VisualizzaRichiesteStudenteCLI(LoggedInUserBean user, PrenotazioneBean prenotazioneBean, List<PrenotazioneBean> list, Integer flag) {
        this.user = user;
        this.dettagliRichiesta = prenotazioneBean;
        this.listRisultati = list;
        this.flag = flag;
    }

    @Override
    public void action(StateMachineImpl context){

        Printer.println(" ");
        Printer.printlnBlu(getMenuTitle() + " -> Dettagli Richiesta:");

        Printer.println("   Tutor: "+ dettagliRichiesta.getNome() + " " + dettagliRichiesta.getCognome());
        Printer.println("   Email: " + dettagliRichiesta.getEmailTutor());
        Printer.println("   Materia: " + dettagliRichiesta.getMateria());
        Printer.println("   Modalità: " + dettagliRichiesta.getModLezione());
        Printer.print("   Giorno: ");

        String giorno;

        giorno = dettagliRichiesta.getGiorno();
        if (giorno != null && !giorno.isEmpty()) {
            Printer.println(giorno);
        }else{
            Printer.println("Qualsiasi");
        }
        Printer.println("   Note aggiuntive: " + dettagliRichiesta.getNote());

        Printer.println("La richiesta è in attesa di conferma.");

        mostraMenu();

    }


    @Override
    public void mostraMenu(){

    }

    private String getMenuTitle() {
        return switch (flag) {
            case 0 -> "Home Studente -> Gestisci Prenotazioni -> Richieste Inviate";
            case 1 -> "Home Studente -> Gestisci Prenotazioni -> Prenotazioni Attive";
            case 2 -> "Home Studente -> Gestisci Prenotazioni -> Richieste Rifiutate";
            default -> "Home Studente -> Gestisci Prenotazioni";
        };
    }

}
