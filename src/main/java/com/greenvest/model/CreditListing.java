package com.greenvest.model;

import java.time.Instant;

public class CreditListing extends BaseEntity {

    private String sellerId;
    private String creditId;
    private int quantity;
    private double pricePerCredit;

    private ListingStatus status = ListingStatus.PENDING_APPROVAL;
    private Instant approvedAt;
    private String approvedByAdminId;

    public CreditListing() {
    }

    public CreditListing(String sellerId, String creditId, int quantity, double pricePerCredit) {
        this.sellerId = sellerId;
        this.creditId = creditId;
        this.quantity = quantity;
        this.pricePerCredit = pricePerCredit;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getCreditId() {
        return creditId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerCredit() {
        return pricePerCredit;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public Instant getApprovedAt() {
        return approvedAt;
    }

    public String getApprovedByAdminId() {
        return approvedByAdminId;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
        touch();
    }

    public void setApprovedAt(Instant approvedAt) {
        this.approvedAt = approvedAt;
        touch();
    }

    public void setApprovedByAdminId(String approvedByAdminId) {
        this.approvedByAdminId = approvedByAdminId;
        touch();
    }
}
