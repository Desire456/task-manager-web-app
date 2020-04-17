package org.netcracker.students.controller.utils.hashing;

import org.netcracker.students.controller.utils.hashing.exceptions.GeneratePasswordException;
import org.netcracker.students.controller.utils.hashing.exceptions.HashingExceptionsConstants;
import org.netcracker.students.controller.utils.hashing.exceptions.ValidatePasswordException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HashingClass {

    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        String str = "";
        return str;
    }

    public static String generatePasswordHash(String password) throws GeneratePasswordException {
        try {
            char[] chars = password.toCharArray();
            byte[] salt = getSalt();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, HashingClassConstants.ITERATIONS, HashingClassConstants.KEY_LENGTH);
            SecretKeyFactory secretKey = SecretKeyFactory.getInstance(HashingClassConstants.SECRET_KEY_ALGORITHM);
            byte[] hash = secretKey.generateSecret(spec).getEncoded();
            return toHex(salt) + ":" + toHex(hash);
        }
         catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
             throw new GeneratePasswordException(HashingExceptionsConstants.GENERATE_PASSSWORD_EXCEPTION_MESSAGE+ " "+
                     e.getMessage());
         }
    }

    public static boolean validatePassword(String originalPassword, String storedPassword) throws ValidatePasswordException {
        try {
            String[] parts = storedPassword.split(":");
            byte[] salt = fromHex(parts[0]);
            byte[] hash = fromHex(parts[1]);

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, HashingClassConstants.ITERATIONS, hash.length * 8);
            SecretKeyFactory secretKey = SecretKeyFactory.getInstance(HashingClassConstants.SECRET_KEY_ALGORITHM);
            byte[] testHash = secretKey.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0;
        }
        catch (InvalidKeySpecException | NoSuchAlgorithmException e){
            throw new ValidatePasswordException(HashingExceptionsConstants.VALIDATE_PASSWORD_EXCEPTION_MESSAGE + " "
                    +e.getMessage());
        }
    }

    private static byte[] fromHex(String hex){
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance(HashingClassConstants.SECURE_RANDOM_ALGORITHM);
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array){
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0){
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        else{
            return hex;
        }
    }
}
