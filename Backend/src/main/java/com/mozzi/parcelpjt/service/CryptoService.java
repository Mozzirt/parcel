package com.mozzi.parcelpjt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Service
public class CryptoService {

    @Value("${crypto.secret}")
    private String secretKey;
    private final  String aesAlgorithm = "AES/CBC/PKCS5Padding";

    /**
     * 데이터 암호화
     */
    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(aesAlgorithm);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(secretKey.substring(0, 16).getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64Utils.encodeToUrlSafeString(encrypted);
    }

    /**
     * 데이터 복호화
     */
    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(aesAlgorithm);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(secretKey.substring(0, 16).getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
        byte[] decodedBytes = Base64Utils.decodeFromUrlSafeString(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }

}
