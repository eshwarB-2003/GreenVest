package com.greenvest.patterns.decorator;

import com.greenvest.model.Transaction;

public interface ReceiptFormatter {

    String format(Transaction transaction);
}
