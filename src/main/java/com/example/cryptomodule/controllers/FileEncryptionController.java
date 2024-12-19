package com.example.cryptomodule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.util.Base64;

public class FileEncryptionController {

    @FXML
    private ComboBox<String> encryptionChoice;

    @FXML
    private TextField filePathField;

    private File selectedFile;

    // Метод для выбора файла
    @FXML
    private void handleChooseFile() {
        // Открываем диалог выбора файла
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt", "*.pdf", "*.docx", "*.*"));
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Отображаем путь к файлу в поле
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    // Метод для шифрования файла
    @FXML
    private void handleEncrypt() {
        try {
            if (selectedFile == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a file.");
                return;
            }

            String selectedEncryption = encryptionChoice.getValue();
            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());

            byte[] encryptedData;
            switch (selectedEncryption) {
                case "AES":
                    encryptedData = encryptAES(fileBytes);
                    break;
                case "RSA":
                    encryptedData = encryptRSA(fileBytes);
                    break;
                case "DES":
                    encryptedData = encryptDES(fileBytes);
                    break;
                default:
                    throw new Exception("Invalid encryption method");
            }

            // Сохранение зашифрованного файла
            File encryptedFile = new File(selectedFile.getParent(), "encrypted_" + selectedEncryption + "_" + selectedFile.getName());
            Files.write(encryptedFile.toPath(), encryptedData);

            showAlert(Alert.AlertType.INFORMATION, "Encryption Success", "File encrypted successfully using " + selectedEncryption);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Encryption failed: " + e.getMessage());
        }
    }

    // Методы шифрования (AES, RSA, DES) останутся такими же, как и в предыдущем коде

    private byte[] encryptAES(byte[] data) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private byte[] encryptRSA(byte[] data) throws Exception {
        // Используйте корректный Base64 строковый ключ
        String base64PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuoCPtl8/82hS7ylpSHVDHSsUtULpkrL5Bqc+q2H9NYOA5HH+xF1PYSCMrQ3oWhl1H4shyCnPf2idk3XEWrB1NLSxEal/8wkaxy6GN7IZEXpZCSv4/LvR+cvn9VDLdIyfybKY+67nZqE/1WQam/TiUfE85gcJgxxDlXaQkc2Qe6RHbrp7Wl90aU3gGP6zBZYMCUk3f9uMrmRLt5hmYI4vVmRWr7zugxVM0SP16Wnihhz+kt1Mo0/QapMLwVtd7hiBwK25XbcdDD7VfXrITg83h2RZmx8OcFbe4WEmaQfolGQupSpQOeRerBIEbWX5nzWv4Cbb8Xqz73CnU680wGTWqwIDAQAB"; // Ваш ключ в Base64

        // Проверьте, что строка Base64 корректная, и удалите пробелы
        base64PublicKey = base64PublicKey.replaceAll("[^A-Za-z0-9+/=]", "");

        // Декодируем строку Base64 в байты
        byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);

        // Генерация публичного ключа из байтов
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(decodedKey));

        // Инициализация шифра с использованием публичного ключа
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Шифрование данных
        return cipher.doFinal(data);
    }

    private byte[] encryptDES(byte[] data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec("12345678".getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
