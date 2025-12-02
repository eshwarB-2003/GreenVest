package com.greenvest.repository.json;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.model.Transaction;
import com.greenvest.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonTransactionRepository
        extends AbstractJsonRepository<Transaction>
        implements TransactionRepository {

    public JsonTransactionRepository(JsonFileUtil fileUtil) {
        super(fileUtil, "transactions.json", Transaction[].class);
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return readAllInternal().stream()
                .filter(t -> id.equals(t.getId()))
                .findFirst();
    }

    @Override
    public List<Transaction> findByBuyerId(String buyerId) {
        return readAllInternal().stream()
                .filter(t -> buyerId.equals(t.getBuyerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findBySellerId(String sellerId) {
        return readAllInternal().stream()
                .filter(t -> sellerId.equals(t.getSellerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(readAllInternal());
    }

    @Override
    public Transaction save(Transaction transaction) {
        List<Transaction> all = readAllInternal();
        all.removeIf(t -> t.getId().equals(transaction.getId()));
        all.add(transaction);
        writeAllInternal(all);
        return transaction;
    }
}
