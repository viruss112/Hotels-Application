package com.example.project.EncryptionUtil;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptionUtil {

    private static final String MESSAGE_DIGEST_ALGORITHM = "SHA";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ENCODING_UTF8 = "UTF-8";
    private static final Integer SECRET_KEY_RELEVANT_BYTES_LENGTH = 16;
    private static final String SECRET_KEY="abcdefghijklmnopqrstuvwxyz";


    private SecretKeySpec secretKeySpec;

    public void setEncryptionPassphrase(String encryptionPassphrase) {
        try {
            MessageDigest digest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
            byte[] passphraseBytes = encryptionPassphrase.getBytes();
            digest.update(passphraseBytes);
            secretKeySpec = new SecretKeySpec(digest.digest(), 0, SECRET_KEY_RELEVANT_BYTES_LENGTH, SECRET_KEY_ALGORITHM);
        } catch (Exception e) {

        }
    }

    public String encrypt(String source) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        setEncryptionPassphrase(SECRET_KEY);
        if (source == null) {
            return null;
        }

        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] cipherBytes = cipher.doFinal(source.getBytes());

        return Base64.encodeBase64String(cipherBytes);
    }

    public String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        setEncryptionPassphrase(SECRET_KEY);
        if (encryptedText == null) {
            return null;
        }

        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = Base64.decodeBase64(encryptedText);

        return new String(cipher.doFinal(encryptedBytes), ENCODING_UTF8);
    }


}
