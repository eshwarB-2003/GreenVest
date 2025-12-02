package com.greenvest.patterns.observer;

public interface ExpirySubject {

    void addObserver(ExpiryObserver observer);

    void removeObserver(ExpiryObserver observer);

    void notifyObservers();
}
