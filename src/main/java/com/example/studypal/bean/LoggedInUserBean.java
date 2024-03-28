package com.example.studypal.bean;

public class LoggedInUserBean {
    private String nome;
    private String cognome;
    private String email;
    protected boolean isTutor;

    public LoggedInUserBean(String email, String nome, String cognome, boolean ruolo){
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.isTutor = ruolo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRuolo(boolean ruoloUtente) { this.isTutor = ruoloUtente; }
    public boolean getRuolo() { return this.isTutor; }

}
