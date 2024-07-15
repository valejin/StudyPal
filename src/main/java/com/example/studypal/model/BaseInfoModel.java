package com.example.studypal.model;

public class BaseInfoModel {

    /* informazioni di base per una ricerca (materia) */

    protected String materia;

    public BaseInfoModel(){this.materia = null;}

    public BaseInfoModel(String materia){this.materia = materia;}

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }


}
