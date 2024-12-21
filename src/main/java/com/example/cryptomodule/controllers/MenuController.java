package com.example.cryptomodule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController extends BaseController {
    @FXML
    private Button encryptId;
    @FXML
    private Button decryptId;
    @FXML
    private Button create_containerId;
    @FXML
    private ImageView arrowId;
    @FXML
    private Button goBackId;
    @FXML
    private Button open_containerId;

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
    @FXML
    public void createContainer() {
        String filepath = "/com/example/cryptomodule/views/create-container-view.fxml";
        String title = "Container";
        Stage stage = (Stage) create_containerId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }

    public void openContainer() {
        String filepath = "/com/example/cryptomodule/views/open-container-view.fxml";
        String title = "Container";
        Stage stage = (Stage) create_containerId.getScene().getWindow();

        changeScene(filepath, stage, title);
    }
}
