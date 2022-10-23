package com.mozzi.parcelpjt.controller;

import com.mozzi.parcelpjt.service.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping(value="/parcel-info/{company}/{waybill}", produces="application/json")
    public String getShippingInfo(@PathVariable String company, @PathVariable String waybill){
        if (company.length() == 0) return null;
        // 대한통운 :: cjLogitics :: 650226054205
        // 롯데택배 :: lotteGlogis :: 309186673526
        // 컬리넥스트마일 :: kurlyNextMile :: 220-S0-2279323560120-0001
        // 우체국 :: epost :: 6892027253841
        // 한진택배 :: hanjin :: 450701336540
        // CU편의점택배 :: cupost :: 651082637861
        // FedEx :: fedex :: 785556713563
        // 로젠택배 : logen :: 96677482381
        // 경동택배 :: kdexp :: https://mybae.kr/article/view/code/kdDeliveryNumber/codeNo/413  3204253820555
        // DHL :: dhl :: 6343512954
        // F711NKSU2AUJ7 기기고유값

        JSONObject resJo = parcelService.findFunc(company, waybill);
        return resJo.toString();
    }

}
