package id.tsi.mmw.manager;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import id.tsi.mmw.util.log.AppLogger;

public class EncryptionManager extends BaseManager {

    private static EncryptionManager instance;

    private static final String ALGO = "AES/CBC/PKCS5PADDING";
    private static final String KEY = "9v9rdhus135231289aulfcoe5oknr3ru";
    private static final String HASH = "SHA-256";

    private MessageDigest messageDigest;
    private SecureRandom secureRandom;
    private SecretKeySpec secretKeySpec;

    private Cipher ecipher;
    private Cipher dcipher;

    private EncryptionManager() {
        final String methodName = "Constructor";
        log = new AppLogger(EncryptionManager.class);
        log.debug(methodName, "Start");

        try {
            ecipher = Cipher.getInstance(ALGO);
            dcipher = Cipher.getInstance(ALGO);
            secretKeySpec = generateSecretKey(KEY);
            messageDigest = MessageDigest.getInstance(HASH);
            secureRandom = new SecureRandom();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error(methodName, e.getMessage());
        }
        log.debug(methodName, "Completed");
    }

    public String encrypt(String text) {
        return encrypt(secretKeySpec, text);
    }

    public String encrypt(String key, String text) {
        SecretKeySpec keySpec;
        try {
            keySpec = generateSecretKey(key);
            return encrypt(keySpec, text);
        } catch (Exception e) {
            log.error("encrypt", e);
        }
        return "";
    }

    private String encrypt(SecretKeySpec keySpec, String text) {
        try {
            byte[] ivs = generateIV();

            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivs);

            ecipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);

            byte[] encrypted = ecipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            // Combine to single byte Array
            byte[] combined = new byte[ivs.length + encrypted.length];

            System.arraycopy(ivs, 0, combined, 0, ivs.length);
            System.arraycopy(encrypted, 0, combined, ivs.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                 | BadPaddingException e) {
            log.error("encrypt", e.getMessage());
        }
        return "";
    }

    public String decrypt(String text) {
        return decrypt(secretKeySpec, text);
    }

    private String decrypt(SecretKeySpec keySpec, String text) {
        try {

            byte[] combined = Base64.getDecoder().decode(text);

            byte[] ivs = new byte[16];
            byte[] encryptedText = new byte[combined.length - 16];

            System.arraycopy(combined, 0, ivs, 0, ivs.length);
            System.arraycopy(combined, ivs.length, encryptedText, 0, encryptedText.length);

            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivs);
            dcipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);

            return new String(dcipher.doFinal(encryptedText), StandardCharsets.UTF_8);

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                 | BadPaddingException e) {
            log.error("decrypt", e.getMessage());
        }
        return "";
    }

    private byte[] generateIV() {
        byte[] ivBytes = new byte[16];
        secureRandom.nextBytes(ivBytes);
        return ivBytes;
    }

    private SecretKeySpec generateSecretKey(String key) {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    }

    public String generateRandomString(int length) {
        return new BigInteger(length * 5, secureRandom).toString(32);
    }

    public String hash(String text, String salt) {
        String hashed = "";
        messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] bytes;
        bytes = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        hashed = sb.toString();
        messageDigest.reset();

        return hashed;
    }

    public void shutdown() {
        log.debug("shutdown", "Start");
        ecipher = null;
        dcipher = null;
        log.debug("shutdown", "Completed");
    }


    public static EncryptionManager getInstance() {
        if (instance == null) {
            instance = new EncryptionManager();
        }
        return instance;
    }
}
