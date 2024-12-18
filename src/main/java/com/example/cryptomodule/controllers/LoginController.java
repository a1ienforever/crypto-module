package com.example.cryptomodule.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidCredentials(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            // Загружаем форму регистрации
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cryptomodule/register-view.fxml"));
            Parent root = fxmlLoader.load();

            // Создаем новое окно для регистрации
            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.setScene(new Scene(root, 400, 200));
            registerStage.show();

            // Закрываем текущее окно авторизации (опционально)
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load registration form.");
        }
    }

    private boolean isValidCredentials(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
