package org.netcracker.students.controller.utils.hashing;

import org.netcracker.students.controller.utils.hashing.exceptions.GeneratePasswordException;
import org.netcracker.students.controller.utils.hashing.exceptions.HashingExceptionsConstants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class HashingClass {
    private byte[] salt;
    public static HashingClass instance;

    public static synchronized HashingClass getInstance() throws NoSuchAlgorithmException {
        if (instance == null)
            instance = new HashingClass();
        return instance;
    }

    private HashingClass() throws NoSuchAlgorithmException {
        this.salt = new byte[16];
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.nextBytes(salt);
    }

    public String hashPassword(String password) throws GeneratePasswordException {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xFF) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new GeneratePasswordException(HashingExceptionsConstants.GENERATE_PASSWORD_EXCEPTION_MESSAGE
            + e.getMessage());
        }
        return generatedPassword;
    }

    public boolean validatePassword(String userPassword, String passwordDb) throws GeneratePasswordException {
        String hashPassword = hashPassword(userPassword);
        return hashPassword.equals(passwordDb);
    }
}

