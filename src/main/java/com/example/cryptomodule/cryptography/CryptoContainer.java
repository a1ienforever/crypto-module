package com.example.cryptomodule.cryptography;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class CryptoContainer {

    public static void createContainer(String containerPath, byte[] data, String passphrase) throws Exception {
        // Генерация соли
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        // Генерация ключа на основе парольной фразы
        KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, 65536, 128); // 65536 итераций
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Шифрование данных
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data);

        // Создание файла контейнера
        try (FileOutputStream fos = new FileOutputStream(containerPath)) {
            // Запись соли (16 байт) и зашифрованных данных
            fos.write(salt);
            fos.write(encryptedData);
        }
    }

    public static byte[] decryptContainer(String containerPath, String passphrase) throws Exception {
        byte[] containerData = Files.readAllBytes(new File(containerPath).toPath());

        // Извлечение соли (первые 16 байт)
        byte[] salt = new byte[16];
        System.arraycopy(containerData, 0, salt, 0, 16);

        // Генерация ключа на основе парольной фразы
        KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt, 65536, 128); // 65536 итераций
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Извлечение зашифрованных данных
        byte[] encryptedData = new byte[containerData.length - 16];
        System.arraycopy(containerData, 16, encryptedData, 0, encryptedData.length);

        // Дешифрование данных
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedData);
    }
}
