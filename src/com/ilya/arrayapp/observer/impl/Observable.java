package com.ilya.arrayapp.observer.impl;

public interface Observable {
    void attach(ArrayChangeListener listener);
    void detach(ArrayChangeListener listener);
    void notifyObservers();
}