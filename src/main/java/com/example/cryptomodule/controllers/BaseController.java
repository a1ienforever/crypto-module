package com.example.cryptomodule.controllers;

import com.example.cryptomodule.app.CryptoApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    public void changeScene(String path, Stage stage, String title){
        try {
            // Загружаем форму шифрования
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = fxmlLoader.load();

            // Устанавливаем новую сцену
            stage.setScene(new Scene(root, 640, 400));
            stage.setTitle(title);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}
