//package com.example.cryptomodule.cryptography;
////Интерфейс для шифрования/ дешифрования
//
//import javafx.scene.control.TextField;
//import javafx.util.Pair;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.util.Base64;
//
//public class EncryptionService {
//    // AES Encryption
//    private byte[] encryptAES(byte[] data, TextField keyField) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        SecretKey secretKey = keyGenerator.generateKey();
//
//        byte[] iv = new byte[16];
//        new java.security.SecureRandom().nextBytes(iv);
//        IvParameterSpec ivSpec = new IvParameterSpec(iv);
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
//
//        keyField.setText(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
////        ivField.setText(Base64.getEncoder().encodeToString(iv));
//
//        return cipher.doFinal(data);
//    }
//
//    // AES Decryption
//    private byte[] decryptAES(byte[] data) throws Exception {
//        byte[] keyBytes = Base64.getDecoder().decode(keyField.getText());
//        byte[] ivBytes = Base64.getDecoder().decode(ivField.getText());
//
//        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
//        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
//
//        return cipher.doFinal(data);
//    }
//
//    // RSA Encryption
//    private byte[] encryptRSA(byte[] data) throws Exception {
//        Pair<String, String> keys = KeyGeneratorService.generateKeyPair();
//        String privateKey = keys.getKey();
//        String publicCodedKey = keys.getValue();
//        keyField.setText(privateKey);
//
//        byte[] decodedKey = Base64.getDecoder().decode(publicCodedKey);
//        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new java.security.spec.X509EncodedKeySpec(decodedKey));
//
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//        return cipher.doFinal(data);
//    }
//
//    // RSA Decryption
//    private byte[] decryptRSA(byte[] data) throws Exception {
//        byte[] privateKeyBytes = Base64.getDecoder().decode(keyField.getText());
//        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes));
//
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//
//        return cipher.doFinal(data);
//    }
//
//    // DES Encryption
//    private byte[] encryptDES(byte[] data) throws Exception {
//        SecretKeySpec secretKey = new SecretKeySpec("12345678".getBytes(), "DES");
//
//        Cipher cipher = Cipher.getInstance("DES");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//        keyField.setText(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//
//        return cipher.doFinal(data);
//    }
//
//    // DES Decryption
//    private byte[] decryptDES(byte[] data) throws Exception {
//        byte[] keyBytes = Base64.getDecoder().decode(keyField.getText());
//        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DES");
//
//        Cipher cipher = Cipher.getInstance("DES");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//        return cipher.doFinal(data);
//    }
//}
