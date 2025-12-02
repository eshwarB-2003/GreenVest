package com.greenvest.model;

public class Receipt extends BaseEntity {

    private String transactionId;
    private String rawText;

    public Receipt() {
    }

    public Receipt(String transactionId, String rawText) {
        this.transactionId = transactionId;
        this.rawText = rawText;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getRawText() {
        return rawText;
    }
}
