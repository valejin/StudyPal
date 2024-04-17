package com.example.studypal.model;

public class RipetizioneInfoModel extends BaseInfoModel {

    /*
    model conterrà i dati relativi alla gestione profilo
     */

    private Boolean inPresenza;
    private Boolean online;
    private String luogo;
    private String giorni;
    private Integer tariffa;
    private String email;

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


    public RipetizioneInfoModel(){ };

    private String nome;
    private String cognome;
    //creo un costruttore non di default, con 2 attributi aggiuntivi (nome, cognome)
    public RipetizioneInfoModel(String nome, String cognome, String materia, Boolean inPresenza,Boolean online, String luogo, String giorni, Integer tariffa, String email){
        this.nome = nome;
        this.cognome = cognome;
        super(materia);
        this.inPresenza = inPresenza;
        this.online = online;
        this.luogo = luogo;
        this.giorni = giorni;
        this.tariffa = tariffa;
        this.email = email;

    }


}
