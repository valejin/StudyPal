package com.example.studypal.bean;


//Bean di base che contiene informazioni per la barra di ricerca in GUIStudente
public class BaseInfoBean {

    private String materia;

    public BaseInfoBean(String materia) {
        this.materia = materia;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

}
