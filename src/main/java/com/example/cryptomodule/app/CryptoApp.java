package com.example.cryptomodule.app;

import com.example.cryptomodule.utils.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CryptoApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CryptoApp.class.getResource("/com/example/cryptomodule/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);

        stage.setTitle("CryptoModule");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        // Ensure Hibernate resources are released when the application stops
        HibernateUtil.shutdown();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
