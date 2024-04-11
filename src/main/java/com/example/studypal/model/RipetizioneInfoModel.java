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
}
