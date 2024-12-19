package com.example.cryptomodule.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Метод для хеширования пароля
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Метод для проверки пароля
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
