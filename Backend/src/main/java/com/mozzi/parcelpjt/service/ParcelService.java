package com.mozzi.parcelpjt.service;

import com.mozzi.parcelpjt.config.response.CompanyConstants;
import com.mozzi.parcelpjt.config.util.ValidationUtil;
import com.mozzi.parcelpjt.controller.exception.custom.NoDataException;
import com.mozzi.parcelpjt.controller.exception.custom.NoSuchTrackingNumberException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.mozzi.parcelpjt.config.response.MessageConstants;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import static com.mozzi.parcelpjt.config.response.MessageConstants.*;

/**
 * 운송장번호 자리수 (코드공통화필요)
 * 특수문자 - 경우 체크
 *
 * 롯데택배 : 12자리
 * CJ대한통운 : 10또는 12자리
 * 우체국 : 13자리
 * 한진택배 : 450701336540
 * 로젠택배 : 96677482381
 * 경동택배 :
 * 쿠팡로지틱스 :
 * 컬리넥스트마일 : 220-S0-2279323560120-0001
 * CU편의점택배 : 651082637861
 */

@Slf4j
@Service
public class ParcelService {

    // Fedex https://www.fedex.com/apps/fedextrack/?action=track&trackingnumber=785556713563&cntry_code=kr&locale=ko_KR
    @Value("${fedex.secret}")
    private String fedexSecret;

    @Value("${fedex.key}")
    private String fedexKey;

    @Value("${dhl.secret}")
    private String dhlSecret;

    @Value("${dhl.key}")
    private String dhlKey;

    // 롯데택배
    public JSONObject lotteGlogis(String waybill){
        JSONObject resultJson = new JSONObject();

        // 운송장번호 체크
        noSuchTrackingNumberExceptionThrower(waybill, CompanyConstants.LOTTE_GLOGIS);

        // 운송장번호 발송지 도착지 배달결과  ::  단계 시간 현재위치 처리현황
        try {
            String URL = "https://www.lotteglogis.com/home/reservation/tracking/linkView";
            Document html = Jsoup.connect(URL)
                    .data("InvNo", waybill)
                    .post();

            Elements lotteTable = html.body().getElementsByClass("tblH").select("td");

            // 존재하지않거나 준비중일경우
            if (lotteTable.size() % 4 != 0){
                throw new NoDataException(lotteTable.get(0).text());
            }

            resultJson = addDefaultResponse(resultJson, lotteTable.get(0).text(), lotteTable.get(1).text(), lotteTable.get(2).text(), lotteTable.get(3).text());

            List bodyList = new ArrayList();
            for(int i=4; i<lotteTable.size(); i=i+4){
                Map<String,String> resultMap = addMultiResponse(lotteTable.get(i).text(), lotteTable.get(i+2).text(), lotteTable.get(i+1).text(), lotteTable.get(i+3).text());
                bodyList.add(resultMap);
            }
            resultJson.put("data", bodyList);

        } catch (IOException e) {
            log.error("###### :: 롯데택배 조회에러 :: ######");
            e.printStackTrace();
        }
        return resultJson;
    }

    // 컬리넥스트마일
    public JSONObject kurlyNextMile(String waybill){
        JSONObject resultJson = new JSONObject();

        try{
            String URL = "https://tms.api.kurly.com/tms/v1/delivery/invoices/" + waybill;
            Document res = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .get();

            JSONObject body = new JSONObject(res.body().select("body").text());
            // 데이터가 없다면
            if (body.get("data").toString().equals("null")) {
                throw new NoDataException(RS_00_0006);
            }

            JSONArray bodyJr = (JSONArray) body.getJSONObject("data").get("trace_infos");

            resultJson = addDefaultResponse(resultJson, (String) body.getJSONObject("data").get("invoice_no"), "", "", (String) body.get("error_message"));

            List bodyList = new ArrayList();
            // body 데이터 체크
            if (bodyJr.length() == 0) ValidationUtil.isEmpty(bodyList, new JSONObject());

            for(int i=0; i<bodyJr.length(); i++){
                JSONObject forJson = bodyJr.getJSONObject(i);
                Map<String,String> resultMap = addMultiResponse((String) forJson.get("status"), (String) forJson.get("location"), (String) forJson.get("date_time"), "");
                bodyList.add(resultMap);
            }
            resultJson.put("data", bodyList);
        }catch (IOException e){
            log.error("###### :: 컬리넥스트마일 조회에러 :: ######");
            e.printStackTrace();
        }
        return resultJson;
    }

    // CJ대한통운
    public JSONObject cjLogitics(String waybill){
        JSONObject resultJson = new JSONObject();

        // 운송장번호 체크
        noSuchTrackingNumberExceptionThrower(waybill, CompanyConstants.CJ_LOGITICS);

        try {
            String URL = "https://www.cjlogistics.com/ko/tool/parcel/tracking";
            Connection res = Jsoup.connect(URL)
                    .method(Connection.Method.GET);
            Connection.Response resConn= res.execute();

            String csrfValue = res
                    .get()
                    .body()
                    .select("input[name=_csrf]")
                    .get(0) // 0 1 2 동일하게 제공
                    .val();

            // find Cookie
            Map<String, String> cjLogiticsCookies = resConn.cookies();
            String URL2 = "https://www.cjlogistics.com/ko/tool/parcel/tracking-detail?paramInvcNo="+waybill+"&_csrf="+csrfValue;
            Document resData = Jsoup.connect(URL2)
                    .cookies(cjLogiticsCookies)
                    .ignoreContentType(true) // Unhandled content type 에러처리
                    .post();

            String body = resData.body().select("body").text();
            JSONObject jo = new JSONObject(body);

            List bodyList = new ArrayList();

            JSONArray headJr = (JSONArray) jo.getJSONObject("parcelResultMap").get("resultList");
            // head 데이터 체크
            if (headJr.length() == 0) {
                throw new NoDataException(RS_00_0006);
            }

            resultJson = addDefaultResponse(resultJson, (String) headJr.getJSONObject(0).get("invcNo"), (String) headJr.getJSONObject(0).get("sendrNm"), (String) headJr.getJSONObject(0).get("rcvrNm"), "");
            JSONArray bodyJr = (JSONArray) jo.getJSONObject("parcelDetailResultMap").get("resultList");

            // body 데이터 체크
            if (bodyJr.length() == 0) ValidationUtil.isEmpty(bodyList, new JSONObject());

            for(int i=0; i<bodyJr.length(); i++){
                JSONObject forJson = bodyJr.getJSONObject(i);
                Map<String, String>  resultMap = addMultiResponse((String) forJson.get("scanNm"), (String) forJson.get("regBranNm"), (String) forJson.get("dTime"), (String) forJson.get("crgNm"));
                bodyList.add(resultMap);
            }
            resultJson.put("data", bodyList);
            return resultJson;
        } catch (IOException e) {
            log.error("###### :: CJ대한통운 조회에러 :: ######");
            e.printStackTrace();
        }
        return resultJson;
    }

    // 우체국 EPOST
    public JSONObject epost(String waybill){
        JSONObject resultJson = new JSONObject();

        // 운송장번호 체크
        noSuchTrackingNumberExceptionThrower(waybill, CompanyConstants.EPOST);

        try{
            String URL = "https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?sid1=" + waybill;
            Document res = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .get();

            // 등기번호 / 보내는분,접수일자 / 받는분 / 수령인,배달일자 / 취급구분 / 배달결과
            // 날짜/ 시간 / 발생국 / 처리현황
            Elements epostTd = res.body().getElementById("print").select("table").select("td");
            String[] from = epostTd.get(0).text().split("<br>");
            String[] to = epostTd.get(1).text().split("<br>");
            List bodyList = new ArrayList();
            resultJson = addDefaultResponse(resultJson, waybill, from[0], to[0], epostTd.get(4).text());

            Elements processTable = res.body().getElementById("processTable").select("td");
            // body 데이터 체크
            if (processTable.size() == 0) {
                throw new NoDataException(RS_00_0006);
                // ValidationUtil.isEmpty(bodyList, new JSONObject());
            }

            for(int i=0; i<processTable.size(); i=i+4){
                Map<String, String> resultMap = addMultiResponse(processTable.get(i+3).text(), processTable.get(i+2).text(), processTable.get(i).text() + " " + processTable.get(i+1).text(), "");
                bodyList.add(resultMap);
            }

            resultJson.put("data", bodyList);
            return resultJson;
        }catch (IOException e){
            log.error("###### :: 우체국택배 조회에러 :: ######");
            e.printStackTrace();
        }
        return resultJson;
    }

    // 한진택배
    public JSONObject hanjin(String waybill){

        JSONObject resultJson = new JSONObject();
        // 운송장번호 체크
        noSuchTrackingNumberExceptionThrower(waybill, CompanyConstants.HANJIN);

        try{
            String URL = "http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking";
            Document res = Jsoup.connect(URL)
                    .data("w_num", waybill)
                    .ignoreContentType(true)
                    .get();
            // TH : 상품명 , 보내는분, 받는분, 운임 // 날짜, 시간, 상품위치 ,진행상황
            Elements headData = res.body().select("table").get(0).select("td");
            // 테이블 체크
            if(res.body().select("table").size() == 1){
                throw new NoDataException(RS_00_0006);
            }
            Elements bodyData = res.body().select("table").get(1).select("td");

            List bodyList = new ArrayList();

            resultJson = addDefaultResponse(resultJson, waybill, headData.get(1).text(), headData.get(2).text(), bodyData.get(bodyData.size()-1).select("strong").text());

            // body 데이터 체크
            if (bodyData.size() == 0) ValidationUtil.isEmpty(bodyList, new JSONObject());

            for(int i=0; i<bodyData.size(); i=i+4){
                Map<String, String> resultMap = addMultiResponse("", bodyData.get(i+2).text(), bodyData.get(i).text() + " " + bodyData.get(i+1).text(), bodyData.get(i+3).text());
                bodyList.add(resultMap);
            }

            resultJson.put("data", bodyList);
            return resultJson;
        }catch (IOException e){
            log.error("###### :: 한진택배 조회에러 :: ######");
            e.printStackTrace();
        }
        return resultJson;
    }

    // 로젠택배
    public JSONObject logen(String waybill){

        JSONObject resultJson = new JSONObject();
        // 운송장번호 체크
        noSuchTrackingNumberExceptionThrower(waybill, CompanyConstants.ILOGEN);

        try{
            String URL = "https://www.ilogen.com/web/personal/trace/"+waybill;
            Document res = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .get();

            Elements headData = res.body().select("table").get(0).select("tr");

            // 테이블 체크
            if(res.body().select("table").size() == 1){
                throw new NoDataException(RS_00_0006);
            }
            Elements bodyData = res.body().select("table").get(1).select("td");

            // 01방문예정, 02물품수거, 03이동중, 04배송중, 05배송완료
            resultJson = addDefaultResponse(resultJson, waybill, headData.get(3).select("td").get(1).text(), headData.get(3).select("td").get(3).text(), res.body().select("ul[class=tkStep] > li[class=on]").text().substring(2));

            // body 데이터 체크
            List bodyList = new ArrayList();
            if (bodyData.size() == 0) ValidationUtil.isEmpty(bodyList, new JSONObject());

            for(int i=0; i<bodyData.size(); i=i+8){
                Map<String, String> resultMap = addMultiResponse(bodyData.get(i+2).text(), bodyData.get(i+1).text(), bodyData.get(i).text(), bodyData.get(i+3).text());
                bodyList.add(resultMap);
            }

            resultJson.put("data", bodyList);
            return resultJson;
        }catch (IOException e){
            log.error("###### :: 로젠택배 조회에러 :: ######");
            e.printStackTrace();
        }
        return resultJson;
    }

    // CU 편의점택배
    public JSONObject cupost(String waybill){
        return cjLogitics(waybill);
    }

    /**
     * Parcel API
     */
    // 경동택배
    public JSONObject kdexp(String waybill){
        JSONObject resultJson = new JSONObject();
        // 운송장번호 체크
        noSuchTrackingNumberExceptionThrower(waybill, CompanyConstants.KDEXP);

        RestTemplate rt = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String,String>> kdexpInfo = new HttpEntity<>(params,httpHeaders);

        ResponseEntity<String> response = rt.exchange(
                "https://kdexp.com/newDeliverySearch.kd?barcode=" + waybill,
                HttpMethod.GET,
                kdexpInfo,
                String.class
        );

        JSONObject jo = new JSONObject(response.getBody());

        if (jo.get("result").equals("fail")) {
            throw new NoDataException(RS_00_0006);
        }

        try{
            JSONObject info = jo.getJSONObject("info");
            JSONArray items = jo.getJSONArray("items");
            List bodyList = new ArrayList();

            resultJson = addDefaultResponse(resultJson, waybill, (String) info.get("send_name"), (String) info.get("re_name"), (String) info.get("relation"));

            for(int i=0; i<items.length(); i++){
                Map<String, String> resultMap = addMultiResponse((String) items.getJSONObject(i).get("stat"), (String) items.getJSONObject(i).get("location"), (String) items.getJSONObject(i).get("reg_date"), (String) items.getJSONObject(i).get("sc_stat"));
                bodyList.add(resultMap);
            }
            resultJson.put("data", bodyList);
            return resultJson;
        }catch(Exception e){
            log.error("###### :: 경동택배 조회에러 :: ######");
            e.printStackTrace();
        }
        return resultJson;
    }


    /**
     * Global Parcel API
     */
    // test num 740561073

    public JSONObject fedex(String waybill){
        JSONObject resultJson = new JSONObject();
        // TODO
        return resultJson;
    }

    // DHL
    public JSONObject dhl(String waybill){

        JSONObject resultJson = new JSONObject();

        try{
            RestTemplate rt = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("DHL-API-Key", dhlKey);

            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

            HttpEntity<MultiValueMap<String,String>> dhlInfo = new HttpEntity<>(params,httpHeaders);

            ResponseEntity<String> response = rt.exchange(
                    "https://api-eu.dhl.com/track/shipments?language=ko&offset=0&limit=5&trackingNumber=" + waybill,
                    HttpMethod.GET,
                    dhlInfo,
                    String.class
            );

            // 결과리스트
            JSONObject jo = (JSONObject) new JSONObject(response.getBody()).getJSONArray("shipments").get(0);
            // 바디리스트
            JSONArray bodyJr = jo.getJSONArray("events");

            List bodyList = new ArrayList();

            resultJson = addDefaultResponse(resultJson, waybill, (String) jo.getJSONObject("origin").getJSONObject("address").get("addressLocality"), (String) jo.getJSONObject("destination").getJSONObject("address").get("addressLocality"), (String) jo.getJSONObject("status").get("status"));
            resultJson.put("service", jo.get("service"));

            for (int i = 0; i < bodyJr.length(); i++) {
                Map<String, String> resultMap = addMultiResponse("", (String) bodyJr.getJSONObject(i).getJSONObject("location").getJSONObject("address").get("addressLocality"), (String) bodyJr.getJSONObject(i).get("timestamp"), (String) bodyJr.getJSONObject(i).get("description"));
                bodyList.add(resultMap);
            }

            resultJson.put("data", bodyList);
            return resultJson;
        }catch (HttpStatusCodeException e){
            String statusCode = e.getStatusCode().toString();
            if (statusCode.equals("404 NOT_FOUND")){
                noSuchTrackingNumberExceptionThrower(waybill, CompanyConstants.LOTTE_GLOGIS);
                // resultJson.put("error", MessageConstants.RS_00_0001);
                // return resultJson;
            }
        }catch (Exception e){
            log.error("##### :: DHL 조회에러 :: #####");
            e.printStackTrace();
        }

        return resultJson;
    }

    /**
     * 공통화
     */
    private void noSuchTrackingNumberExceptionThrower(String waybill, String company) {
        boolean validate = ValidationUtil.validateWaybillNumber(waybill, company);
        StringBuilder mixedData = new StringBuilder(company + "," + waybill);
        if(!validate) throw new NoSuchTrackingNumberException(mixedData.toString());
    }

    private Map<String, String> addMultiResponse(String process, String location, String time, String explain) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("process", process);
        resultMap.put("location", location);
        resultMap.put("time", time);
        resultMap.put("explain", explain);
        return resultMap;
    }

    private JSONObject addDefaultResponse(JSONObject resultJson, String no, String from, String to, String result) {
        resultJson.put("no", no);
        resultJson.put("from", from);
        resultJson.put("to", to);
        resultJson.put("result", result);
        return resultJson;
    }

    /**
     * 분기코드
     */
    public JSONObject findFunc(String company, String waybill){
        JSONObject jo = new JSONObject();
        switch (company){
            case "hanjin":
                jo = hanjin(waybill);
                break;
            case "cupost":
            case "cjLogitics":
                jo = cjLogitics(waybill);
                break;
            case "epost":
                jo = epost(waybill);
                break;
            case "logen":
                jo = logen(waybill);
                break;
            case "lotteGlogis":
                jo = lotteGlogis(waybill);
                break;
            case "kurlyNextMile":
                jo = kurlyNextMile(waybill);
                break;
            case "fedex":
                jo = fedex(waybill);
                break;
            case "kdexp":
                jo = kdexp(waybill);
                break;
            case "dhl":
                jo = dhl(waybill);
                break;
            default:
                log.warn("#### 존재하지않은 회사 접근시도 :: 회사명 :: 운송장번호 ::  {} , [ {} ]", company, waybill);
                jo.put("error", MessageConstants.RS_00_0005);
                break;
        }
        return jo;
    }
}