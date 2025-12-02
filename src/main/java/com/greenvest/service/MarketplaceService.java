package com.greenvest.service;

import com.greenvest.common.IdGenerator;
import com.greenvest.common.Preconditions;
import com.greenvest.model.*;
import com.greenvest.patterns.CreditFactory;

import java.time.LocalDate;
import java.util.*;

/**
 * Very simple in-memory marketplace service.
 * This will later be refactored into BrokerService, TradeService, etc.
 */
public class MarketplaceService {

    private final Map<String, SME> smes = new HashMap<>();
    private final Map<String, Credit> credits = new HashMap<>();
    private final CreditFactory creditFactory = new CreditFactory();

    public SME registerSme(String name, String email) {
        Preconditions.require(!name.isBlank(), "name must not be blank");
        Preconditions.require(!email.isBlank(), "email must not be blank");
        String id = IdGenerator.newId();
        SME sme = new SME(id, name, email);
        smes.put(id, sme);
        return sme;
    }

    public List<Credit> listAllCredits() {
        return new ArrayList<>(credits.values());
    }

    public Credit submitCredit(String sellerId,
                               double quantity,
                               double basePrice,
                               LocalDate expiryDate) {
        Preconditions.requireNonNull(sellerId, "sellerId must not be null");
        Preconditions.require(smes.containsKey(sellerId), "Unknown sellerId");
        Credit credit = creditFactory.createPendingCredit(sellerId, quantity, basePrice, expiryDate);
        credits.put(credit.getId(), credit);
        return credit;
    }

    public void verifyCredit(String creditId) {
        Credit credit = credits.get(creditId);
        Preconditions.requireNonNull(credit, "credit not found");
        credit.verify();
    }
}

