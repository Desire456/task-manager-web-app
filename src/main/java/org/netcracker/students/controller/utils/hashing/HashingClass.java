package org.netcracker.students.controller.utils.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingClass {
    private static final byte[] salt = new byte[]{49, 54, 75, -31, 28, 120, -25, -55, -128, 49 - 23, 9, -124, -113, -118, -120};
    public static HashingClass instance;

    public static synchronized HashingClass getInstance() {
        if (instance == null)
            instance = new HashingClass();
        return instance;
    }

    private HashingClass() {
    }

    public String hashPassword(String userPassword) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(userPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public boolean validatePassword(String userPassword, String passwordDb) {
        String hashPassword = hashPassword(userPassword);
        return hashPassword.equals(passwordDb);
    }
}

