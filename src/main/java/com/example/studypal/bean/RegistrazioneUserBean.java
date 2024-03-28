package com.example.studypal.bean;

public class RegistrazioneUserBean extends LoginUserBean{
    //classe bean per i dati provenienti dalla registrazione
    //la registrazione estende login
    //con i campi password e confermaPassword aggiuntivi

    private String password;
    private String confermaPassword;

    public RegistrazioneUserBean(String email, String nome, String cognome, String password, String confermaPassword){
        super(email, nome, cognome);
        this.password = password;
        this.confermaPassword = confermaPassword;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfermaPassword() {
        return confermaPassword;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassword = confermaPassword;
    }

}
