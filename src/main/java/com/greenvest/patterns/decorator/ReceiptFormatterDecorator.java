package com.greenvest.patterns.decorator;

import com.greenvest.model.Transaction;

public abstract class ReceiptFormatterDecorator implements ReceiptFormatter {

    protected final ReceiptFormatter delegate;

    protected ReceiptFormatterDecorator(ReceiptFormatter delegate) {
        this.delegate = delegate;
    }

    @Override
    public String format(Transaction transaction) {
        return delegate.format(transaction);
    }
}
