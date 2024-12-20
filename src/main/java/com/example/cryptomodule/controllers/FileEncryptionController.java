package com.example.cryptomodule.controllers;

import com.example.cryptomodule.cryptography.KeyGeneratorService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class FileEncryptionController extends BaseController {

    @FXML
    private Button goBackId;
    @FXML
    private ImageView arrowId;
    @FXML
    private ComboBox<String> encryptionChoice;

    @FXML
    private Text filePathField;

    private File selectedFile;

    @FXML
    private TextField keyField;

    @FXML
    private TextField ivField;

    @FXML
    private void goBack() {
        String filepath = "/com/example/cryptomodule/views/menu-view.fxml";
        String title = "Login";
        Stage stage = (Stage) goBackId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }

    // Метод для выбора файла
    @FXML
    private void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
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

            File encryptedFile = new File(selectedFile.getParent(), "encrypted_" + selectedEncryption + "_" + selectedFile.getName());
            Files.write(encryptedFile.toPath(), encryptedData);

            showAlert(Alert.AlertType.INFORMATION, "Encryption Success", "File encrypted successfully using " + selectedEncryption);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Encryption failed: " + e.getMessage());
        }
    }

    // Метод для расшифровки файла
    @FXML
    private void handleDecrypt() {
        try {
            if (selectedFile == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a file.");
                return;
            }

            String selectedEncryption = encryptionChoice.getValue();
            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());

            byte[] decryptedData;
            switch (selectedEncryption) {
                case "AES":
                    decryptedData = decryptAES(fileBytes);
                    break;
                case "RSA":
                    decryptedData = decryptRSA(fileBytes);
                    break;
                case "DES":
                    decryptedData = decryptDES(fileBytes);
                    break;
                default:
                    throw new Exception("Invalid decryption method");
            }

            File decryptedFile = new File(selectedFile.getParent(), "decrypted_" + selectedEncryption + "_" + selectedFile.getName());
            Files.write(decryptedFile.toPath(), decryptedData);

            showAlert(Alert.AlertType.INFORMATION, "Decryption Success", "File decrypted successfully using " + selectedEncryption);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Decryption failed: " + e.getMessage());
        }
    }

    // AES Encryption
    private byte[] encryptAES(byte[] data) throws Exception {
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

        return cipher.doFinal(data);
    }

    // AES Decryption
    private byte[] decryptAES(byte[] data) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyField.getText());
        byte[] ivBytes = Base64.getDecoder().decode(ivField.getText());

        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        return cipher.doFinal(data);
    }

    // RSA Encryption
    private byte[] encryptRSA(byte[] data) throws Exception {
        Pair<String, String> keys = KeyGeneratorService.generateKeyPair();
        String privateKey = keys.getKey();
        String publicCodedKey = keys.getValue();
        keyField.setText(privateKey);

        byte[] decodedKey = Base64.getDecoder().decode(publicCodedKey);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new java.security.spec.X509EncodedKeySpec(decodedKey));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    // RSA Decryption
    private byte[] decryptRSA(byte[] data) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(keyField.getText());
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(privateKeyBytes));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    // DES Encryption
    private byte[] encryptDES(byte[] data) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec("12345678".getBytes(), "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        keyField.setText(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        return cipher.doFinal(data);
    }

    // DES Decryption
    private byte[] decryptDES(byte[] data) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyField.getText());
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

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
