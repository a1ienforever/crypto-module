module com.example.cryptomodule {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    requires jbcrypt;
    requires org.bouncycastle.provider;
    requires java.desktop; // для работы с JDBC

    // Разрешаем javafx.graphics доступ к классу CryptoApp
    opens com.example.cryptomodule.app to javafx.graphics;

    // Разрешаем javafx.fxml доступ к контроллерам
    opens com.example.cryptomodule.controllers to javafx.fxml;

    // Открываем пакеты для Hibernate
    opens com.example.cryptomodule.models to org.hibernate.orm.core;

    // Экспортируем нужные пакеты
    exports com.example.cryptomodule.app;
    exports com.example.cryptomodule.controllers;

}
