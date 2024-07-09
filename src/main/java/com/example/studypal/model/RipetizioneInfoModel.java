package com.example.studypal.model;

public class RipetizioneInfoModel extends BaseInfoModel {

    /*
    model conterrà i dati relativi alla gestione profilo
     */

    private boolean inPresenza;
    private boolean online;
    private String luogo;
    private String giorni;
    private int tariffa;
    private String email;
    private String materie;
    private String nome;
    private String cognome;

    public Integer getTariffa() {
        return tariffa;
    }

    public void setTariffa(Integer tariffa) {
        this.tariffa = tariffa;
    }

    public String getGiorni() {
        return giorni;
    }

    public void setGiorni(String giorni) {
        this.giorni = giorni;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getInPresenza() {
        return inPresenza;
    }

    public void setInPresenza(Boolean inPresenza) {
        this.inPresenza = inPresenza;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
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

    public String getMaterie() {
        return materie;
    }

    public void setMaterie(String materie) {
        this.materie = materie;
    }

    //cotruttore di default
    public RipetizioneInfoModel(){ }

    //costruttore non di default, con 2 attributi aggiuntivi (nome, cognome) - necessario per risultati della ricerca
    public RipetizioneInfoModel(String nome, String cognome, String materie, Boolean inPresenza, Boolean online, String luogo, String giorni, Integer tariffa, String email){
        super(materie);
        this.nome = nome;
        this.cognome = cognome;
        this.materie = materie;
        this.inPresenza = inPresenza;
        this.online = online;
        this.luogo = luogo;
        this.giorni = giorni;
        this.tariffa = tariffa;
        this.email = email;

    }



}
