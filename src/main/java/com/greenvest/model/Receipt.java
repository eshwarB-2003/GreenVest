package com.greenvest.model;

import com.greenvest.common.Preconditions;

import java.time.LocalDateTime;

/**
 * UUID-based digital receipt.
 */
public class Receipt {

    private final String id;
    private final String tradeId;
    private final LocalDateTime generatedAt;

    /**
     * @pre tradeId != null
     */
    public Receipt(String id, String tradeId, LocalDateTime generatedAt) {
        Preconditions.requireNonNull(id, "id must not be null");
        Preconditions.requireNonNull(tradeId, "tradeId must not be null");
        Preconditions.requireNonNull(generatedAt, "generatedAt must not be null");
        this.id = id;
        this.tradeId = tradeId;
        this.generatedAt = generatedAt;
    }

    public String getId() {
        return id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}
