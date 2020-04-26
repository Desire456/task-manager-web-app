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

    public String hashPassword(String userPassword) throws HashPasswordException {
        String generatedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance(HashConstants.HASH_FUNCTION);
            md.update(salt);
            byte[] bytes = md.digest(userPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new HashPasswordException(HashConstants.HASH_EXCEPTION_MESSAGE + e.getMessage());
        }
        return generatedPassword;
    }

    public boolean validatePassword(String userPassword, String passwordDb) throws HashPasswordException {
        String hashPassword = hashPassword(userPassword);
        return hashPassword.equals(passwordDb);
    }
}

