package com.example.cryptomodule.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Base64;

public class FileEncryptionUtil {

    private static final String ALGORITHM = "AES";

    // Генерация ключа AES
    public static SecretKey generateKey() throws GeneralSecurityException {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // 128 бит
        return keyGen.generateKey();
    }

    // Сохранение ключа в файл
    public static void saveKey(SecretKey key, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(Base64.getEncoder().encode(key.getEncoded()));
        }
    }

    // Загрузка ключа из файла
    public static SecretKey loadKey(File file) throws IOException {
        byte[] encodedKey = Base64.getDecoder().decode(new String(java.nio.file.Files.readAllBytes(file.toPath())));
        return new SecretKeySpec(encodedKey, ALGORITHM);
    }

    // Шифрование файла
    public static void encryptFile(File inputFile, File outputFile, SecretKey key) throws GeneralSecurityException, IOException {
        processFile(Cipher.ENCRYPT_MODE, inputFile, outputFile, key);
    }

    // Дешифрование файла
    public static void decryptFile(File inputFile, File outputFile, SecretKey key) throws GeneralSecurityException, IOException {
        processFile(Cipher.DECRYPT_MODE, inputFile, outputFile, key);
    }

    // Обработка файла
    private static void processFile(int cipherMode, File inputFile, File outputFile, SecretKey key) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(cipherMode, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                fos.write(output);
            }
        }
    }
}
