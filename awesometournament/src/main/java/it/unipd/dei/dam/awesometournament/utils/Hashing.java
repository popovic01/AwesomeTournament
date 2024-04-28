package it.unipd.dei.dam.awesometournament.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Provides utility methods for hashing passwords.
 */
public class Hashing {
    /**
     * Hashes a password using the SHA-256 algorithm.
     *
     * @param clearPassword The password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String clearPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(clearPassword.getBytes());

            byte[] hashedBytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
