package com.example.cryptomodule.cryptography;

import javafx.util.Pair;

import java.security.*;
import java.util.Base64;

public class KeyGeneratorService {

    public static Pair<String, String> generateKeyPair() throws NoSuchAlgorithmException {
        // Генерация пары ключей RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // Указываем размер ключа 2048 бит
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Получаем публичный ключ
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        // Преобразуем публичный ключ в формат Base64
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        return new Pair<>(privateKeyBase64, publicKeyBase64);
    }
}