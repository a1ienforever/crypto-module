package com.example.cryptomodule.controllers;

import com.example.cryptomodule.dao.UserDAO;
import com.example.cryptomodule.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleRegisterSubmit() {
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "All fields must be filled.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
            return;
        }

        // Здесь добавьте логику сохранения пользователя в БД
        UserDAO userDAO = new UserDAO();
        User user = new User(username, password);
        userDAO.saveUser(user);
        showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User " + username + " registered successfully!");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
