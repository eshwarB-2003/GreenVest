package com.greenvest.rules;

import com.greenvest.model.CarbonCredit;
import com.greenvest.model.CreditListing;
import com.greenvest.model.Transaction;

public class RuleContext {

    private CarbonCredit credit;
    private CreditListing listing;
    private Transaction transaction;

    public CarbonCredit getCredit() {
        return credit;
    }

    public void setCredit(CarbonCredit credit) {
        this.credit = credit;
    }

    public CreditListing getListing() {
        return listing;
    }

    public void setListing(CreditListing listing) {
        this.listing = listing;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
