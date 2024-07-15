package com.example.studypal.model;

public class UserModel {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private boolean ruolo;

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

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public void setRuolo(boolean ruoloUtente) { this.ruolo = ruoloUtente; }
    public boolean getRuolo() { return this.ruolo; }

    public String toString() {
        return nome + "," + cognome + "," + email + "," + password + "," + ruolo;
    }

    public static UserModel fromString(String userString) {
        String[] parts = userString.split(",");
        UserModel user = new UserModel();
        user.setNome(parts[0]);
        user.setCognome(parts[1]);
        user.setEmail(parts[2]);
        user.setPassword(parts[3]);
        user.setRuolo(Boolean.parseBoolean(parts[4]));
        return user;
    }
}
