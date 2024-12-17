package com.example.cryptomodule.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CryptoAppController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label helloText;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    @FXML
    protected void onButtonClick() {
        helloText.setText("Hello World");
    }
}