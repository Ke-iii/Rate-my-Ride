package com.example.cab302project;

import java.util.Observer;

public interface Subject {
    // Subject interface methods
    void addObserver(com.example.cab302project.Observer observer);

    void removeObserver(com.example.cab302project.Observer observer);

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

