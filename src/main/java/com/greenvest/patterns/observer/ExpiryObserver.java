package com.greenvest.patterns.observer;

import com.greenvest.model.CarbonCredit;

public interface ExpiryObserver {

    void onExpiryEvent(CarbonCredit credit);
}
