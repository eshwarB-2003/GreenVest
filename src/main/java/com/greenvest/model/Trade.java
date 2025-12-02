package com.greenvest.model;

import com.greenvest.common.Preconditions;
import java.time.LocalDateTime;

/**
 * Represents a trade between Buyer and Seller for a specific Carbon Credit.
 * Demonstrates preconditions & immutability (SOLID - SRP).
 */
public class Trade {

    private final String id;
    private final String buyerId;
    private final String sellerId;
    private final String creditId;
    private final double quantity;
    private final double pricePerUnit;
    private final LocalDateTime timestamp;

    /**
     * @pre quantity > 0
     * @pre pricePerUnit >= 0
     * @pre buyerId != null && sellerId != null && creditId != null
     */
    public Trade(String id,
                 String buyerId,
                 String sellerId,
                 String creditId,
                 double quantity,
                 double pricePerUnit,
                 LocalDateTime timestamp) {

        Preconditions.requireNonNull(id, "id must not be null");
        Preconditions.requireNonNull(buyerId, "buyerId must not be null");
        Preconditions.requireNonNull(sellerId, "sellerId must not be null");
        Preconditions.requireNonNull(creditId, "creditId must not be null");
        Preconditions.require(quantity > 0, "quantity must be > 0");
        Preconditions.require(pricePerUnit >= 0, "pricePerUnit must be >= 0");
        Preconditions.requireNonNull(timestamp, "timestamp must not be null");

        this.id = id;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.creditId = creditId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }

    public String getBuyerId() { return buyerId; }

    public String getSellerId() { return sellerId; }

    public String getCreditId() { return creditId; }

    public double getQuantity() { return quantity; }

    public double getPricePerUnit() { return pricePerUnit; }

    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return """
                Trade Summary:
                -----------------
                Trade ID: %s
                Buyer: %s
                Seller: %s
                Credit: %s
                Quantity: %.2f
                Price per Unit: %.2f
                Timestamp: %s
                """.formatted(id, buyerId, sellerId, creditId, quantity, pricePerUnit, timestamp);
    }
}
