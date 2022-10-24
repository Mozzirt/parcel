package com.mozzi.parcelpjt.controller;

import com.mozzi.parcelpjt.service.CryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CryptController {

    private final CryptoService cryptoService;

    @GetMapping("/encrypt")
    public String encrypt (@RequestParam String data) throws Exception {
        log.info("##### Encrypt 실행 ##### => {}", data);
        return cryptoService.encrypt(data);
    }

    @GetMapping("/decrypt")
    public String decrypt (@RequestParam String data) throws Exception {
        log.info("##### Decrypt 실행 ##### => {}", data);
        return cryptoService.decrypt(data);
    }
}
