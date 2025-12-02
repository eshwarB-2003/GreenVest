package com.greenvest.common;

/**
 * Simple precondition / postcondition utility.
 * Used to document and enforce method contracts.
 */
public final class Preconditions {

    private Preconditions() {
        // Utility class – no instances
    }

    /**
     * Checks a condition and throws IllegalArgumentException if false.
     *
     * @param condition boolean expression that must be true
     * @param message   error message if condition is false
     * @throws IllegalArgumentException if condition is false
     */
    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures a reference is not null.
     *
     * @param reference object that must be non-null
     * @param <T>       type of reference
     * @return the same reference if non-null
     * @throws NullPointerException if reference is null
     */
    public static <T> T requireNonNull(T reference, String message) {
        if (reference == null) {
            throw new NullPointerException(message);
        }
        return reference;
    }
}

