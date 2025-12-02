package com.greenvest.service;

import com.greenvest.model.Transaction;
import com.greenvest.repository.TransactionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportingService {

    private final TransactionRepository transactionRepository;

    public ReportingService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public String getTopSeller() {
        Map<String, Long> counts = transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getSellerId, Collectors.counting()));

        return counts.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public String getTopBuyer() {
        Map<String, Long> counts = transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getBuyerId, Collectors.counting()));

        return counts.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
