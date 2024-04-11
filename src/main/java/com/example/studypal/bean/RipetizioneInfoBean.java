package com.example.studypal.bean;

public class RipetizioneInfoBean extends BaseInfoBean{

    //questo bean estende BaseInfoBean che contiene il nome materia

    private Boolean inPresenza;
    private Boolean online;
    private String luogo;
    private String giorni;
    private Integer tariffa;

    private String email;

    //qui contiene tutte le informazioni per la ricerca con filtro, in caso di Prenota Ripetizione
    public RipetizioneInfoBean(String materia, Boolean inPresenza, Boolean online, String luogo, String giorni, Integer tariffa){
        super(materia);
        this.inPresenza = inPresenza;
        this.online = online;
        this.luogo = luogo;
        this.giorni = giorni;
        this.tariffa = tariffa;

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

}
