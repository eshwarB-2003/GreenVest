package com.greenvest.patterns.decorator;

import com.greenvest.model.Transaction;

public class BasicReceiptFormatter implements ReceiptFormatter {

    @Override
    public String format(Transaction tx) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GreenVest Receipt ===").append(System.lineSeparator());
        sb.append("Transaction ID: ").append(tx.getId()).append(System.lineSeparator());
        sb.append("Buyer ID: ").append(tx.getBuyerId()).append(System.lineSeparator());
        sb.append("Seller ID: ").append(tx.getSellerId()).append(System.lineSeparator());
        sb.append("Listing ID: ").append(tx.getListingId()).append(System.lineSeparator());
        sb.append("Quantity: ").append(tx.getQuantity()).append(System.lineSeparator());
        sb.append("Total Amount: ").append(tx.getTotalAmount()).append(System.lineSeparator());
        sb.append("Completed At: ").append(tx.getCompletedAt()).append(System.lineSeparator());
        sb.append("=========================").append(System.lineSeparator());
        return sb.toString();
    }
}
