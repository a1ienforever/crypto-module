package com.example.cryptomodule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends BaseController {

    public Button encryptId;
    public Button decryptId;
    public Button containerId;
    public Button create_containerId;
    public ImageView arrowId;
    public Button goBackId;

    @FXML
    private void encryptFile() {
        String filepath = "/com/example/cryptomodule/views/encrypt-file-view.fxml";
        String title = "File Encryption";
        Stage stage = (Stage) encryptId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }

    @FXML
    private void decryptFile() {
        String filepath = "/com/example/cryptomodule/views/decrypt-file-view.fxml";
        String title = "File Decryption";
        Stage stage = (Stage) decryptId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }

    @FXML
    private void goBack() {
        String filepath = "/com/example/cryptomodule/views/login-view.fxml";
        String title = "Login";
        Stage stage = (Stage) goBackId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }
}
