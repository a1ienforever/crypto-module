package com.example.cryptomodule.cryptography;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class CryptoContainer {

    // Создание криптоконтейнера
    public static void createContainer(String containerPath, byte[] data) throws Exception {
        // Генерация AES-ключа
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // Шифрование данных
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data);

        // Создание файла контейнера
        try (FileOutputStream fos = new FileOutputStream(containerPath)) {
            // Запись ключа (первые 16 байт) и зашифрованных данных
            fos.write(secretKey.getEncoded());
            fos.write(encryptedData);
        }
    }

    // Расшифровка данных из контейнера
    public static byte[] decryptContainer(String containerPath) throws Exception {
        byte[] containerData = Files.readAllBytes(new File(containerPath).toPath());

        // Извлечение ключа (первые 16 байт)
        byte[] keyBytes = new byte[16];
        System.arraycopy(containerData, 0, keyBytes, 0, 16);
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
