package com.example.studypal.bean;

public class RegistrazioneUserBean {
    //classe bean per i dati provenienti dalla registrazione
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String confermaPassworld;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfermaPassword() {
        return confermaPassworld;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassworld = confermaPassword;
    }

}
