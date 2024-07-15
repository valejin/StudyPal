package com.example.studypal.bean;

public class RegistrazioneUserBean extends LoggedInUserBean {
    //classe bean per i dati provenienti dalla registrazione
    //la registrazione estende login
    //con i campi password e confermaPassword aggiuntivi

    private String password;
    private String confermaPassword;

    public RegistrazioneUserBean(String email, String nome, String cognome, boolean ruolo, String password, String confermaPassword){
        super(email, nome, cognome, ruolo );
        this.password = password;
        this.confermaPassword = confermaPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassword = confermaPassword;
    }

    public String getConfermaPassword() {
        return confermaPassword;
    }
}
