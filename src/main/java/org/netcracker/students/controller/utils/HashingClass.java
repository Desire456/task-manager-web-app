package org.netcracker.students.controller.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HashingClass {
    private static final String SECRET_KEY = "PBKDF2WithHmacSHA1";

    static public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new BigInteger(1,hash).toString(16);
    }
}
