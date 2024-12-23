package com.example.cryptomodule.cryptography;

import javafx.scene.control.TextField;
import javafx.util.Pair;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionService {

    public static void clearPrivateKey(byte[] keyBytes) {
        try {

            if (keyBytes != null) {
                Arrays.fill(keyBytes, (byte) 0); // Затираем содержимое
            }
        } catch (Exception e) {
            System.err.println("Ошибка при очистке приватного ключа: " + e.getMessage());
        }
    }
    // AES Encryption
    public byte[] encryptAES(byte[] data, TextField keyField, TextField ivField) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        byte[] iv = new byte[16];
        new java.security.SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        keyField.setText(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        ivField.setText(Base64.getEncoder().encodeToString(iv));
        clearPrivateKey(secretKey.getEncoded());
        secretKey.destroy();
        return cipher.doFinal(data);
    }

    // AES Decryption
    public byte[] decryptAES(byte[] data, TextField keyField, TextField ivField) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyField.getText());
        byte[] ivBytes = Base64.getDecoder().decode(ivField.getText());

        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        clearPrivateKey(secretKey.getEncoded());
        secretKey.destroy();
        return cipher.doFinal(data);
    }

    // RSA Encryption
    public byte[] encryptRSA(byte[] data, TextField keyField) throws Exception {
        Pair<String, String> keys = KeyGeneratorService.generateKeyPair();
        String privateKey = keys.getKey();
        String publicCodedKey = keys.getValue();
        keyField.setText(privateKey);
        privateKey = null;

        byte[] decodedKey = Base64.getDecoder().decode(publicCodedKey);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new java.security.spec.X509EncodedKeySpec(decodedKey));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    // RSA Decryption
    public byte[] decryptRSA(byte[] data, TextField keyField) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(keyField.getText());
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        clearPrivateKey(privateKeyBytes);
        privateKeyBytes = null;
        return cipher.doFinal(data);
    }

    // DES Encryption
    public byte[] encryptDES(byte[] data, TextField keyField) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec("12345678".getBytes(), "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        keyField.setText(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        clearPrivateKey(secretKey.getEncoded());

        return cipher.doFinal(data);
    }

    // DES Decryption
    public byte[] decryptDES(byte[] data, TextField keyField) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyField.getText());
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        clearPrivateKey(secretKey.getEncoded());
        return cipher.doFinal(data);
    }
}
