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

    /**
     * Encrypts the given text using the provided key and initialization vector (IV).
     *
     * @param keySpec the SecretKeySpec representing the encryption key
     * @param text the text to be encrypted
     * @return the encrypted text as a Base64 encoded string
     */
    private String encrypt(SecretKeySpec keySpec, String text) {
        try {
            // Generate a random initialization vector (IV)
            byte[] ivBytes = generateIV();

            // Specify the algorithm parameter spec with the IV
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivBytes);

            // Initialize the cipher in encryption mode with the key and IV
            ecipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);

            // Encrypt the text bytes
            byte[] encryptedBytes = ecipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            // Combine IV and encrypted bytes into a single byte array
            byte[] combinedBytes = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, combinedBytes, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, combinedBytes, ivBytes.length, encryptedBytes.length);

            // Return the Base64 encoded encrypted text
            return Base64.getEncoder().encodeToString(combinedBytes);

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                 | BadPaddingException e) {
            log.error("encrypt", e.getMessage());
        }
        return "";
    }

    public String decrypt(String text) {
        return decrypt(secretKeySpec, text);
    }

    /**
     * Decrypts the given text using the provided key and initialization vector (IV).
     *
     * @param keySpec the SecretKeySpec representing the decryption key
     * @param text the text to be decrypted
     * @return the decrypted text
     */
    private String decrypt(SecretKeySpec keySpec, String text) {
        try {
            // Decode the combined text into bytes
            byte[] combined = Base64.getDecoder().decode(text);

            // Separate the IV and the encrypted text
            byte[] ivs = new byte[16];
            byte[] encryptedText = new byte[combined.length - 16];

            // Copy the first 16 bytes (IV) into the ivs array
            System.arraycopy(combined, 0, ivs, 0, ivs.length);

            // Copy the rest of the bytes (encrypted text) into the encryptedText array
            System.arraycopy(combined, ivs.length, encryptedText, 0, encryptedText.length);

            // Set up the initialization vector for decryption
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivs);

            // Initialize the decryption cipher with the decryption mode, key, and IV
            dcipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);

            // Perform the decryption and return the decrypted text
            return new String(dcipher.doFinal(encryptedText), StandardCharsets.UTF_8);

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                 | BadPaddingException e) {
            // Log any decryption errors
            log.error("decrypt", e.getMessage());
        }
        // Return an empty string if decryption fails
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

    /**
     * This method hashes the given text using the provided salt.
     *
     * @param text the text to be hashed
     * @param salt the salt to be used in the hashing process
     * @return the hashed text
     */
    public String hash(String text, String salt) {
        // Initialize an empty string to store the hashed value
        String hashed = "";

        // Update the message digest with the salt bytes
        messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));

        // Generate the hash bytes by digesting the text bytes
        byte[] bytes = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));

        // Create a StringBuilder to build the hashed string
        StringBuilder sb = new StringBuilder();

        // Convert each byte to a hexadecimal string and append it to the StringBuilder
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // Store the final hashed string
        hashed = sb.toString();

        // Reset the message digest for the next operation
        messageDigest.reset();

        // Return the hashed value
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
