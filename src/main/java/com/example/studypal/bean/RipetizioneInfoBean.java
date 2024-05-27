package com.example.studypal.bean;

import java.util.HashMap;
import java.util.Map;

public class RipetizioneInfoBean extends BaseInfoBean{

    //questo bean estende BaseInfoBean che contiene il nome materia

    private Boolean inPresenza;
    private Boolean online;
    private String luogo;
    private String giorni;
    private Integer tariffa;
    private String email;
    private String nome;
    private String cognome;

    public RipetizioneInfoBean(String materia){
        super(materia);
    }

    //qui contiene tutte le informazioni per la ricerca con filtro, in caso di Prenota Ripetizione
    public RipetizioneInfoBean(String materia, Boolean inPresenza, Boolean online, String luogo, String giorni, Integer tariffa, String email){
        super(materia);
        this.inPresenza = inPresenza;
        this.online = online;
        this.luogo = luogo;
        this.giorni = giorni;
        this.tariffa = tariffa;
        this.email = email;

    }

    public RipetizioneInfoBean(String nome, String cognome, String materia, Boolean inPresenza, Boolean online, String luogo, String giorni, Integer tariffa, String email){

        this.inPresenza = inPresenza;
        this.online = online;
        this.luogo = luogo;
        this.giorni = giorni;
        this.tariffa = tariffa;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.materia = materia;
    }


    public void abbreviaGiorni() {

        Map<String, String> giorniMap = new HashMap<>();
        giorniMap.put("Lunedì", "L");
        giorniMap.put("Martedì", "Ma");
        giorniMap.put("Mercoledì", "Me");
        giorniMap.put("Giovedì", "G");
        giorniMap.put("Venerdì", "V");
        giorniMap.put("Sabato", "S");
        giorniMap.put("Domenica", "D");

        for (Map.Entry<String, String> entry : giorniMap.entrySet()) {
            this.giorni = this.giorni.replace(entry.getKey(), entry.getValue());
        }
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMaterie() {
        return this.materia;
    }

    public void setMaterie(String materie) {
        this.materia = materie;
    }
}
