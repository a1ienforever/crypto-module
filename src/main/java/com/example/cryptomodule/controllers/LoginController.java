package com.example.cryptomodule.controllers;

import com.example.cryptomodule.dao.UserDAO;
import com.example.cryptomodule.models.User;
import com.example.cryptomodule.utils.PasswordUtil;
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
        // Логика проверки пользователя
        boolean authenticated = isValidCredentials(usernameField.getText(), passwordField.getText());
        if (authenticated) {
            try {
                // Загружаем форму шифрования
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cryptomodule/views/menu-view.fxml"));
                Parent root = fxmlLoader.load();

                // Получаем текущее окно
                Stage stage = (Stage) usernameField.getScene().getWindow();

                // Устанавливаем новую сцену
                stage.setScene(new Scene(root, 640, 400));
                stage.setTitle("File Encryption");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load encryption page.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }



    @FXML
    private void handleRegister() {
        try {
            // Загружаем форму регистрации
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cryptomodule/views/register-view.fxml"));
            Parent root = fxmlLoader.load();

            // Создаем новое окно для регистрации
            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.setScene(new Scene(root, 640, 400));
            registerStage.show();

            // Закрываем текущее окно авторизации (опционально)
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load registration form.");
        }
    }

    private boolean isValidCredentials(String username, String password) {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByLoginAndPassword(username);
            return PasswordUtil.checkPassword(password, user.getPassword());
        }
        catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid username or password.");
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
