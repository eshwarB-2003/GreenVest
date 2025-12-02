package com.greenvest.repository;

import com.greenvest.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> findById(String id);

    List<Transaction> findByBuyerId(String buyerId);

    List<Transaction> findBySellerId(String sellerId);

    List<Transaction> findAll();

    Transaction save(Transaction transaction);
}

