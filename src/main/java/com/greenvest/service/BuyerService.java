package com.greenvest.service;

import com.greenvest.common.Preconditions;
import com.greenvest.model.Transaction;
import com.greenvest.repository.TransactionRepository;

import java.util.List;

public class BuyerService {

    private final TransactionRepository transactionRepository;

    public BuyerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsForBuyer(String buyerId) {
        Preconditions.requireNonBlank(buyerId, "buyerId is required");
        return transactionRepository.findByBuyerId(buyerId);
    }
}
