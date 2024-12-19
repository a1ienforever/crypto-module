package com.example.cryptomodule.cryptography;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Base64;

public class KeyGeneratorService {

    public static void main(String[] args) throws Exception {
        // Генерация пары ключей RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // Указываем размер ключа 2048 бит
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Получаем публичный ключ
        PublicKey publicKey = keyPair.getPublic();
        // Преобразуем публичный ключ в формат Base64
        String base64PublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        // Выводим публичный ключ в формате Base64
        System.out.println("Public Key (Base64):");
        System.out.println(base64PublicKey);
    }
}