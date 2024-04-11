package com.example.studypal.bean;

public class RipetizioneInfoBean extends BaseInfoBean{

    //questo bean estende BaseInfoBean che contiene il nome materia

    private Boolean inPresenza;
    private Boolean online;
    private String luogo;
    private String giorni;

    private Integer tariffa;


    //qui contiene tutte le informazioni per la ricerca con filtro, in caso di Prenota Ripetizione
    public RipetizioneInfoBean(String materia, Boolean inPresenza, Boolean online, String luogo, String giorni, Integer tariffa){
        super(materia);
        this.inPresenza = inPresenza;
        this.online = online;
        this.luogo = luogo;
        this.giorni = giorni;
        this.tariffa = tariffa;

    }


}
