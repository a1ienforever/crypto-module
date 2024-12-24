package com.example.cryptomodule.controllers;

import com.example.cryptomodule.cryptography.CryptoContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;

public class CryptContainerController extends BaseController {

    public ImageView arrowId;
    public Button goBackId;
    public PasswordField passwordPhraseId;
    @FXML
    private Text filePathField;

    @FXML
    private File selectedFile;

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

    // Создание криптоконтейнера
    @FXML
    private void handleCreateContainer() {
        try {
            if (selectedFile == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a file.");
                return;
            }

            byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
            String containerPath = selectedFile.getParent() + "/cryptoContainer.dat";
            String phrase = passwordPhraseId.getText();
            CryptoContainer.createContainer(containerPath, fileBytes, phrase);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Crypto container created at: " + containerPath);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create crypto container: " + e.getMessage());
        }
    }

    // Расшифровка данных из контейнера
    @FXML
    private void handleDecryptContainer() {
        try {
            if (selectedFile == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a container file.");
                return;
            }

            String phrase = passwordPhraseId.getText();
            byte[] decryptedData = CryptoContainer.decryptContainer(selectedFile.getAbsolutePath(), phrase);
            File decryptedFile = new File(selectedFile.getParent(), "decrypted_" + selectedFile.getName());
            Files.write(decryptedFile.toPath(), decryptedData);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Decrypted file created at: " + decryptedFile.getAbsolutePath());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to decrypt crypto container: " + e.getMessage());
        }
    }



    @FXML
    private void goBack() {
        String filepath = "/com/example/cryptomodule/views/menu-view.fxml";
        String title = "Login";
        Stage stage = (Stage) goBackId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }
}
