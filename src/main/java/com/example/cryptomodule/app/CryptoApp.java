package com.example.cryptomodule.app;

import com.example.cryptomodule.models.Key;
import com.example.cryptomodule.models.User;
import com.example.cryptomodule.utils.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class CryptoApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CryptoApp.class.getResource("/com/example/cryptomodule/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("CryptoModule");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        // Начало транзакции
        Transaction transaction = session.beginTransaction();

        try {
            // Создание пользователя
            User user = new User();
            user.setUsername("user1"); // Устанавливаем логин
            user.setPassword("user1"); // Устанавливаем пароль
            System.out.println(user);
            // Создание ключа
            Key key = new Key();
            key.setKeyValue("user1_key"); // Устанавливаем значение ключа
            key.setUser(user);        // Устанавливаем связь с пользователем
            System.out.println(key);
            // Сохранение пользователя и ключа в базу данных
            session.save(user); // Hibernate сохраняет объект User
            session.save(key);  // Hibernate сохраняет объект Key

            // Подтверждение транзакции
            transaction.commit();
        } catch (Exception e) {
            // Откат транзакции в случае ошибки
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            // Закрытие сессии
            session.close();
            // Завершение работы Hibernate
            HibernateUtil.shutdown();
        }
        launch();
    }
}
