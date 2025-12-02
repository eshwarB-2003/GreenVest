package com.greenvest.patterns.state;

import com.greenvest.model.CarbonCredit;

public interface CreditState {

    void handle(CarbonCredit credit);
}
