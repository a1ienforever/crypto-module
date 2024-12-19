package com.example.cryptomodule.app;

import com.example.cryptomodule.utils.FileEncryptionUtil;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javax.crypto.SecretKey;
import java.io.File;

public class FileEncryptionApp extends Application {

    private SecretKey secretKey;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Encryption");

        // Кнопка для выбора файла
        Button selectFileButton = new Button("Выбрать файл для шифрования");
        selectFileButton.setOnAction(event -> selectFile(primaryStage));

        // Кнопка для генерации ключа
        Button generateKeyButton = new Button("Генерировать ключ");
        generateKeyButton.setOnAction(event -> generateKey(primaryStage));

        // Кнопка для загрузки ключа
        Button loadKeyButton = new Button("Загрузить ключ");
        loadKeyButton.setOnAction(event -> loadKey(primaryStage));

        VBox layout = new VBox(10, generateKeyButton, loadKeyButton, selectFileButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        primaryStage.setScene(new Scene(layout, 400, 200));
        primaryStage.show();
    }

    private void selectFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        File inputFile = fileChooser.showOpenDialog(stage);

        if (inputFile != null && secretKey != null) {
            FileChooser saveFileChooser = new FileChooser();
            saveFileChooser.setTitle("Сохранить зашифрованный файл");
            File outputFile = saveFileChooser.showSaveDialog(stage);

            if (outputFile != null) {
                try {
                    FileEncryptionUtil.encryptFile(inputFile, outputFile, secretKey);
                    System.out.println("Файл зашифрован и сохранён: " + outputFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Сначала сгенерируйте или загрузите ключ.");
        }
    }

    private void generateKey(Stage stage) {
        try {
            secretKey = FileEncryptionUtil.generateKey();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить ключ");
            File keyFile = fileChooser.showSaveDialog(stage);

            if (keyFile != null) {
                FileEncryptionUtil.saveKey(secretKey, keyFile);
                System.out.println("Ключ сохранён: " + keyFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadKey(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить ключ");
        File keyFile = fileChooser.showOpenDialog(stage);

        if (keyFile != null) {
            try {
                secretKey = FileEncryptionUtil.loadKey(keyFile);
                System.out.println("Ключ загружен: " + keyFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
