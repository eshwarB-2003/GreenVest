package com.greenvest.controller;

import com.greenvest.model.Transaction;
import com.greenvest.service.BuyerService;

import java.util.List;

public class BuyerController {

    private final BuyerService buyerService;

    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    public List<Transaction> getTransactionsForBuyer(String buyerId) {
        return buyerService.getTransactionsForBuyer(buyerId);
    }
}
