package com.greenvest.patterns.state;

import com.greenvest.model.CarbonCredit;

public class CreditContext {

    private CreditState state;

    public void setState(CreditState state) {
        this.state = state;
    }

    public void apply(CarbonCredit credit) {
        if (state != null) {
            state.handle(credit);
        }
    }
}
