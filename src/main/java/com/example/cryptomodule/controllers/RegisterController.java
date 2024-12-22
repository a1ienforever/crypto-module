package com.example.cryptomodule.controllers;

import com.example.cryptomodule.dao.UserDAO;
import com.example.cryptomodule.models.User;
import com.example.cryptomodule.utils.PasswordUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController extends BaseController {

    @FXML
    private ImageView arrowId;
    @FXML
    private Button goBackId;
    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void goBack() {
        String filepath = "/com/example/cryptomodule/views/login-view.fxml";
        String title = "Login";
        Stage stage = (Stage) goBackId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }

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
        String hashedPassword = PasswordUtil.hashPassword(password);


        UserDAO userDAO = new UserDAO();
        User user = new User(username, hashedPassword);
        userDAO.saveUser(user);

        try {
            // Загружаем форму шифрования
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cryptomodule/views/menu-view.fxml"));
            Parent root = fxmlLoader.load();

            // Получаем текущее окно
            Stage stage = (Stage) newUsernameField.getScene().getWindow();

            // Устанавливаем новую сцену
            stage.setScene(new Scene(root, 640, 400));
            stage.setTitle("File Encryption");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load encryption page.");
        }
    }
}
