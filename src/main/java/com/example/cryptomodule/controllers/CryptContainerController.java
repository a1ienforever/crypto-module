package com.example.cryptomodule.controllers;

import com.example.cryptomodule.cryptography.CryptoContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
            filePathField.setText(selectedFile.getAbsolutePath());
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

            CryptoContainer.createContainer(containerPath, fileBytes);

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

            byte[] decryptedData = CryptoContainer.decryptContainer(selectedFile.getAbsolutePath());

            File decryptedFile = new File(selectedFile.getParent(), "decrypted_" + selectedFile.getName());
            Files.write(decryptedFile.toPath(), decryptedData);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Decrypted file created at: " + decryptedFile.getAbsolutePath());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to decrypt crypto container: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBack() {
        String filepath = "/com/example/cryptomodule/views/menu-view.fxml";
        String title = "Login";
        Stage stage = (Stage) goBackId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }
}
