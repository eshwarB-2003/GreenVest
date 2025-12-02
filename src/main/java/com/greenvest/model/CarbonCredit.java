package com.greenvest.model;

import java.time.Instant;

public class CarbonCredit extends BaseEntity {

    private String sellerId;
    private String sustainabilityActionId;
    private int quantity;
    private CreditLifecycleState lifecycleState = CreditLifecycleState.DRAFT;
    private Instant expiryDate;

    public CarbonCredit() {
    }

    public CarbonCredit(String sellerId, String sustainabilityActionId, int quantity, Instant expiryDate) {
        this.sellerId = sellerId;
        this.sustainabilityActionId = sustainabilityActionId;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getSustainabilityActionId() {
        return sustainabilityActionId;
    }

    public int getQuantity() {
        return quantity;
    }

    public CreditLifecycleState getLifecycleState() {
        return lifecycleState;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setLifecycleState(CreditLifecycleState lifecycleState) {
        this.lifecycleState = lifecycleState;
        touch();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        touch();
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
        touch();
    }
}
