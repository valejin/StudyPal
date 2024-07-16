package com.example.studypal.model;

import com.example.studypal.bean.LoggedInUserBean;

import java.util.List;

public class PrenotazioneModel {

    private final int idRichiesta;
    private String nome;
    private String cognome;
    private final String emailTutor;
    private final String emailStudente;
    private String materia;
    private int modLezione;
    private int tariffa;
    private String giorno;
    private String note;
    private int stato;

    public PrenotazioneModel(Integer idRichiesta, LoggedInUserBean user, String emailStudente, String materia, String giorno, String note, List<Integer> valori) {

        /**     L'array di interi contiene ordinatamente: modalità di lezione, tariffa, stato
            *       -modalità di lezione:
            *           -0 valide entrambe
            *           -1 in presenza
            *           -2 online
                *       -stato:
            *           -0 richiesta in attesa
                *           -1 prenotazione attiva
            *           -2 richiesta rifiutata
        **/

        this.idRichiesta = idRichiesta;
        this.nome = user.getNome();
        this.cognome = user.getCognome();
        this.emailTutor = user.getEmail();
        this.emailStudente = emailStudente;
        this.materia = materia;
        this.modLezione = valori.get(0);
        this.tariffa = valori.get(1);
        this.giorno = giorno;
        this.note = note;
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

    public String getEmailStudente() {
        return emailStudente;
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
