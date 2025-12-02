package com.greenvest.common;

import java.util.UUID;

/**
 * Generates UUID-based identifiers for entities such as Credit, Trade, Receipt.
 */
public final class IdGenerator {

    private IdGenerator() {
    }

    public static String newId() {
        return UUID.randomUUID().toString();
    }
}

