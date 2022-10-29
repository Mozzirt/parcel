package com.mozzi.parcelpjt.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName : com.mozzi.parcelpjt.service
 * fileName : CryptoServiceTest
 * author : kimbeomchul
 * date : 2022/10/28
 * description :
 * ===========================================================
 * DATE    AUTHOR    NOTE
 * -----------------------------------------------------------
 * 2022/10/28 kimbeomchul 최초 생성
 */

@Slf4j
@SpringBootTest
class CryptoServiceTest {

    @Autowired
    CryptoService cryptoService;

    @Test
    void encryptTest() throws Exception {
        String test = "Encrypt Me!!";
        String encrypt = cryptoService.encrypt(test);
        Assertions.assertThat(test).isNotEqualTo(encrypt);
        log.info("encrypt data = {}", encrypt);
        this.decryptTest(test, encrypt);
    }

    void decryptTest(String real, String encrypted) throws Exception {
        String decrypt = cryptoService.decrypt(encrypted);
        log.info("decrypt data = {}", decrypt);
        Assertions.assertThat(decrypt).isEqualTo(real);
    }
}