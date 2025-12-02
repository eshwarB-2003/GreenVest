package com.greenvest.service;

import com.greenvest.model.Receipt;
import com.greenvest.model.Transaction;
import com.greenvest.patterns.decorator.BasicReceiptFormatter;
import com.greenvest.patterns.decorator.ReceiptFormatter;
import com.greenvest.repository.TransactionRepository;

public class ReceiptService {

    private final TransactionRepository transactionRepository;
    private final ReceiptFormatter receiptFormatter;

    public ReceiptService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        this.receiptFormatter = new BasicReceiptFormatter(); // can be decorated later
    }

    public Receipt generateReceipt(Transaction transaction) {
        String raw = receiptFormatter.format(transaction);
        return new Receipt(transaction.getId(), raw);
    }

    public String formatReceipt(Receipt receipt) {
        // For now, rawText is already formatted
        return receipt.getRawText();
    }
}
