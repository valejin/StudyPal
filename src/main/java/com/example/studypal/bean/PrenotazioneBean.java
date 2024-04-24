package com.example.studypal.bean;

public class PrenotazioneBean {

    private int idRichiesta;
    private String emailTutor;
    private String emailStudente;
    private String materia;
    private int modLezione;
    private int tariffa;
    private String giorno;
    private String note;

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setIdRichiesta(int idRichiesta) {
        this.idRichiesta = idRichiesta;
    }

    public String getEmailTutor() {
        return emailTutor;
    }

    public void setEmailTutor(String emailTutor) {
        this.emailTutor = emailTutor;
    }

    public String getEmailStudente() {
        return emailStudente;
    }

    public void setEmailStudente(String emailStudente) {
        this.emailStudente = emailStudente;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getModLezione() {
        return modLezione;
    }

    public void setModLezione(int modLezione) {
        this.modLezione = modLezione;
    }

    public int getTariffa() {
        return tariffa;
    }

    public void setTariffa(int tariffa) {
        this.tariffa = tariffa;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
