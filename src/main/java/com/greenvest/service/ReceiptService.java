/*package com.greenvest.service;

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
*/
package com.greenvest.service;

import com.greenvest.model.Receipt;
import com.greenvest.model.Transaction;
import com.greenvest.patterns.decorator.BasicReceiptFormatter;
import com.greenvest.patterns.decorator.ReceiptFormatter;
import com.greenvest.repository.TransactionRepository;

import java.util.Objects;

/**
 * Service that creates/formats receipts for transactions.
 * - Exposes generateReceipt(Transaction)
 * - Exposes getFormattedReceiptText(String transactionId)
 *
 * NOTE: this implementation assumes TransactionRepository has:
 *    Optional<Transaction> findById(String id)
 *
 * If your repository uses a different method name (e.g. getById or getByIdOptional),
 * tell me and I will adapt this method.
 */
public class ReceiptService {

    private final TransactionRepository transactionRepository;
    private final ReceiptFormatter receiptFormatter;

    public ReceiptService(TransactionRepository transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository, "transactionRepository required");
        this.receiptFormatter = new BasicReceiptFormatter(); // can be decorated later
    }

    /**
     * Build a Receipt model for the given Transaction using the configured formatter.
     * Controllers may call this and then use receipt.getRawText().
     */
    public Receipt generateReceipt(Transaction transaction) {
        Objects.requireNonNull(transaction, "transaction is required");
        String raw = receiptFormatter.format(transaction);
        // model.Receipt constructor assumed: Receipt(String transactionId, String rawText)
        return new Receipt(transaction.getId(), raw);
    }

    /**
     * Convenience method used by controllers to get a formatted receipt text by transaction id.
     * Matches the controller usage: receiptService.getFormattedReceiptText(transactionId)
     */
    public String getFormattedReceiptText(String transactionId) {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("transactionId is required");
        }

        Transaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));

        return generateReceipt(tx).getRawText();
    }

    /**
     * If callers already have a Receipt model, return its formatted/raw text.
     */
    public String formatReceipt(Receipt receipt) {
        Objects.requireNonNull(receipt, "receipt required");
        return receipt.getRawText();
    }
}
