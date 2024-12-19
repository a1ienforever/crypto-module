package com.example.cryptomodule.cryptography;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

public class KeyGeneratorService {

    public static String generateKeyPair() throws NoSuchAlgorithmException {
        // Генерация пары ключей RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // Указываем размер ключа 2048 бит
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Получаем публичный ключ
        PublicKey publicKey = keyPair.getPublic();
        // Преобразуем публичный ключ в формат Base64

        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}