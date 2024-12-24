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

import java.io.File;
import java.nio.file.Files;

import com.example.cryptomodule.cryptography.EncryptionService;

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
            long fileSizeInBytes = selectedFile.length(); // Размер файла в байтах
            long fileSize = 0;
            String format = "";
            if (fileSizeInBytes > 1024*1024) {
                format = "MB";
                fileSize = (fileSizeInBytes / (1024*1024));
            } else if (fileSizeInBytes < 1024*1024) {
                format = "KB";
                fileSize = (fileSizeInBytes / 1024);
            }
            String msg = selectedFile.getAbsolutePath() + " Размер файла: " + fileSize + format;
            filePathField.setText(msg);
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
            EncryptionService encryptionService = new EncryptionService();

            byte[] encryptedData;
            switch (selectedEncryption) {
                case "AES":
                    encryptedData = encryptionService.encryptAES(fileBytes, keyField, ivField);
                    System.gc();
                    break;
                case "RSA":
                    encryptedData = encryptionService.encryptRSA(fileBytes, keyField);
                    System.gc();
                    break;
                case "DES":
                    encryptedData = encryptionService.encryptDES(fileBytes, keyField);
                    System.gc();
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
            EncryptionService encryptionService = new EncryptionService();

            byte[] decryptedData;
            switch (selectedEncryption) {
                case "AES":
                    decryptedData = encryptionService.decryptAES(fileBytes, keyField, ivField);
                    System.gc();
                    break;
                case "RSA":
                    decryptedData = encryptionService.decryptRSA(fileBytes, keyField);
                    System.gc();
                    break;
                case "DES":
                    decryptedData = encryptionService.decryptDES(fileBytes, keyField);
                    System.gc();
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
}
