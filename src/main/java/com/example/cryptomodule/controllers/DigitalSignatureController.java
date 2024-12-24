package com.example.cryptomodule.controllers;

import com.example.cryptomodule.cryptography.CryptoContainer;
import com.example.cryptomodule.cryptography.DigitalSignatureService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DigitalSignatureController extends BaseController {
    @FXML
    private Button chooseFileId;
    @FXML
    private Button checkId;
    @FXML
    private TextField publicKeyId;
    @FXML
    private Button chooseSignId;
    @FXML
    private Text checkSign;
    @FXML
    private ImageView arrowId;
    @FXML
    private Button goBackId;
    @FXML
    private TextField publicKey;
    @FXML
    private Text filePathField;
    @FXML
    private File selectedFile;
    @FXML
    private File selectedSign;

    @FXML
    private void goBack(){
        String filepath = "/com/example/cryptomodule/views/menu-view.fxml";
        String title = "Menu";
        Stage stage = (Stage) goBackId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }

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

    @FXML
    private void handleChooseSign() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        selectedSign = fileChooser.showOpenDialog(null);

        if (selectedSign != null) {
            filePathField.setText(selectedSign.getAbsolutePath());
        }
    }

    @FXML
    private void handleCreateSignature() {
        try {
            if (selectedFile == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a file.");
                return;
            }
            DigitalSignatureService signatureService = new DigitalSignatureService();

            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey pubKey = keyPair.getPublic();
            String signaturePath = selectedFile.getParent() + "/" + selectedFile.getName() + "sign.sig";
            File signatureFile = new File(signaturePath);


            String signature = signatureService.signFile(fileBytes, privateKey);
            signatureService.saveSignatureToFile(signature, signatureFile);
            byte[] keyBytes = pubKey.getEncoded();
            String key = Base64.getEncoder().encodeToString(keyBytes);
            publicKey.setText(key);

            System.out.println("Подпись сохранена в файл: " + signatureFile.getAbsolutePath());
            System.out.println(key);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Подпись сохранена в файл: " + signatureFile.getAbsolutePath());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create crypto container: " + e.getMessage());
        }
    }

    @FXML
    private void checkSignature() {
        try {
            DigitalSignatureService signatureService = new DigitalSignatureService();

            // Проверка, что публичный ключ введён
            String publicKeyText = publicKeyId.getText();
            if (publicKeyText == null || publicKeyText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a public key.");
                return;
            }

            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());

            byte[] keyBytes = Base64.getDecoder().decode(publicKeyText);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey key  = keyFactory.generatePublic(spec);



            String savedSignature = signatureService.readSignatureFromFile(selectedSign);
            boolean isValid = signatureService.verifyFileSignature(fileBytes, savedSignature, key);
            System.out.println("Подпись действительна: " + isValid);



            if (isValid) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "The signature is valid.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "The signature is invalid.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to verify signature: " + e.getMessage());
        }
    }

}
