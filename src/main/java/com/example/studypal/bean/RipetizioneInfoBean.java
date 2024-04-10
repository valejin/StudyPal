package com.example.studypal.bean;

public class RipetizioneInfoBean extends BaseInfoBean{

    //questo bean estende BaseInfoBean che contiene il nome materia

    private String modalitaLez;
    private String luogo;
    private String giorno;
    private String fasciaOraria;
    private String tariffa;


    //qui contiene tutte le informazioni per la ricerca con filtro, in caso di Prenota Ripetizione
    public RipetizioneInfoBean(String materia, String modalitaLez, String luogo, String giorno, String fasciaOraria, String tariffa){
        super(materia);
        this.modalitaLez = modalitaLez;
        this.luogo = luogo;
        this.giorno = giorno;
        this.fasciaOraria = fasciaOraria;
        this.tariffa = tariffa;

    }


}
