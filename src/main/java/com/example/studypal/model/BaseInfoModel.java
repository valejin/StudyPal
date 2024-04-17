package com.example.studypal.model;

public class BaseInfoModel {

    /*
    classe che contiene le informazioni relative ad una ripetizione: dati del tutor, giorno e ora etc
    utilizzata nei casi d'uso di prenota ripetizione (studente) e gestisci profilo (tutor)
    Le sue istanze vengono popolate dai controller applicativi e passate al ripetizioneDAO
    */

    protected String materia;

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }


}
