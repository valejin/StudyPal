package com.example.studypal.bean;


import java.util.List;

public class PrenotazioneBean {

    private int idRichiesta;
    private String nome;
    private String cognome;
    private String emailTutor;
    private String emailStudente;
    private String materia;
    private int modLezione;
    private int tariffa;
    private String giorno;
    private String note;
    private int stato;

    public PrenotazioneBean(){    }

    public PrenotazioneBean(Integer idRichiesta, LoggedInUserBean tutorInfo, String emailStudente, String materia, String giorno, String note, List<Integer> valori) {

        /*l'array di interi contiene ordinatamente: modalità di lezione, tariffa, stato*/

        this.idRichiesta = idRichiesta;
        this.nome = tutorInfo.getNome();
        this.cognome = tutorInfo.getCognome();
        this.emailTutor = tutorInfo.getEmail();
        this.emailStudente = emailStudente;
        this.materia = materia;
        this.giorno = giorno;
        this.note = note;
        this.modLezione = valori.get(0);
        this.tariffa = valori.get(1);
        this.stato = valori.get(2);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }



    public int getIdRichiesta() {
        return idRichiesta;
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

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

}
