package com.example.studypal.pattern.observer;

import java.util.ArrayList;

public abstract class Subject {
    /** Lista di Observer che si iscrivono per ricevere la notifica */
    private final ArrayList<Observer> observers;

    protected Subject() {
        observers = new ArrayList<>() ;
    }

    /** Aggiunge un observer alla lista dei subscribers */
    public void attach(Observer newObserver) {
        observers.add(newObserver) ;
    }

    /** Rimuove un observer dalla lista dei subscribers */
    public void detach(Observer removeObserver) {
        observers.remove(removeObserver) ;
    }

    /** Implementato con un loop su tutti i ConcreteObserver, dove ciascuno di essi chiama la funzione update()
     * Viene eseguita dal ConcreteSubject (il publisher) per notificare il suo cambio di stato

     * È BUONA NORMA RENDERLO PRIVATO PERCHÈ SOLO CONCRETEOBSERVER DOVREBBE CHIAMARLO */
    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
