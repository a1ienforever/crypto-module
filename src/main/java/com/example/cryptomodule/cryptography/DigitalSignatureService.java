package com.example.cryptomodule.cryptography;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class DigitalSignatureService {

    // Создание цифровой подписи для файла
    public String signFile(byte[] fileBytes, PrivateKey privateKey) throws Exception {

        // Создать хэш от данных и подписать его
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(fileBytes);

        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }

    // Проверка цифровой подписи файла
    public boolean verifyFileSignature(byte[] fileBytes, String signatureStr, PublicKey publicKey) throws Exception {

        // Декодировать подпись из строки
        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);

        // Проверить подпись
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(fileBytes);

        return signature.verify(signatureBytes);
    }

    // Запись цифровой подписи в файл
    public void saveSignatureToFile(String signature, File signatureFile) throws IOException {
        try (FileWriter writer = new FileWriter(signatureFile)) {
            writer.write(signature);
        }
    }

    // Чтение цифровой подписи из файла
    public String readSignatureFromFile(File signatureFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(signatureFile))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
