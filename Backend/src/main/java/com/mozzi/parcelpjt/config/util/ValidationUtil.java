package com.mozzi.parcelpjt.config.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import java.util.List;

import static com.mozzi.parcelpjt.config.response.MessageConstants.RS_00_0006;

/**
 * 작성자 : beomchul.kim@lotte.net
 * ValidationUtil
 */
@Slf4j
public class ValidationUtil {

    // 운송장번호 Validation
    public static boolean validateWaybillNumber(String waybill, String company){
        int length = waybill.length();
        boolean isValid = true;
        switch (company){
            case "한진택배":
            case "대한통운":
                if (length != 10 && length != 12) isValid = false;
                break;
            case "경동택배":
            case "우체국":
                if (length != 13) isValid = false;
                break;
            case "로젠택배":
                if (length != 11) isValid = false;
                break;
            case "롯데택배":
                if (length != 12) isValid = false;
                break;
            default:
                break;
        }
        return isValid;
    }
    @Deprecated
    public static List isEmpty(List bodyList, JSONObject jo){
        bodyList.add(jo.put("error", RS_00_0006));
        return bodyList;
    }

}
