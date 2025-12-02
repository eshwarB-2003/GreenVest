package com.greenvest.common;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHasher {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH_BYTES = 16;
    private static final int HASH_LENGTH_BITS = 256;
    private static final int ITERATIONS = 65_536;

    private final SecureRandom secureRandom = new SecureRandom();

    public String hashPassword(String rawPassword) {
        Preconditions.requireNonBlank(rawPassword, "Password cannot be blank");

        byte[] salt = new byte[SALT_LENGTH_BYTES];
        secureRandom.nextBytes(salt);

        byte[] hash = pbkdf2(rawPassword.toCharArray(), salt, ITERATIONS, HASH_LENGTH_BITS);

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        return ITERATIONS + ":" + saltBase64 + ":" + hashBase64;
    }

    public boolean verifyPassword(String rawPassword, String storedHash) {
        Preconditions.requireNonBlank(rawPassword, "Password cannot be blank");
        Preconditions.requireNonBlank(storedHash, "Stored hash cannot be blank");

        String[] parts = storedHash.split(":");
        if (parts.length != 3) {
            return false;
        }

        int iterations;
        try {
            iterations = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        byte[] salt;
        byte[] expectedHash;
        try {
            salt = Base64.getDecoder().decode(parts[1]);
            expectedHash = Base64.getDecoder().decode(parts[2]);
        } catch (IllegalArgumentException e) {
            return false;
        }

        byte[] actualHash = pbkdf2(rawPassword.toCharArray(), salt, iterations, expectedHash.length * 8);

        return slowEquals(expectedHash, actualHash);
    }

    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLengthBits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLengthBits);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Error while hashing password", e);
        }
    }

    private boolean slowEquals(byte[] a, byte[] b) {
        if (a == null || b == null) {
            return false;
        }

        int diff = a.length ^ b.length;
        int min = Math.min(a.length, b.length);

        for (int i = 0; i < min; i++) {
            diff |= a[i] ^ b[i];
        }

        return diff == 0;
    }
}
