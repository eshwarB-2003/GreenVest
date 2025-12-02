package com.greenvest.model;

import java.time.Instant;

public class Transaction extends BaseEntity {

    private String buyerId;
    private String sellerId;
    private String listingId;
    private int quantity;
    private double totalAmount;
    private TransactionType type = TransactionType.PURCHASE;
    private Instant completedAt;

    public Transaction() {
    }

    public Transaction(String buyerId, String sellerId, String listingId,
                       int quantity, double totalAmount) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.listingId = listingId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.completedAt = Instant.now();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getListingId() {
        return listingId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public TransactionType getType() {
        return type;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}
