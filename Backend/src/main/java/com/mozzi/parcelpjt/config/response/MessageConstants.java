package com.mozzi.parcelpjt.config.response;

/**
 * 작성자 : beomchul.kim@lotte.net
 * - CommonConstants
 */
public class MessageConstants {

    // 공통
    public static final String PC_00_0001 = "성공";
    public static final String PC_00_0002 = "실패";

    // 크롤링
    public static final String RS_00_0001 = "운송장번호가 올바르지 않습니다";
    public static final String RS_00_0002 = "배송사에 상품이 전달되지 않았습니다";
    public static final String RS_00_0003 = "조회중 오류가 발생하였습니다";
    public static final String RS_00_0004 = "조회에 실패하였습니다";
    public static final String RS_00_0005 = "존재하지 않는 회사코드입니다";
    public static final String RS_00_0006 = "데이터가 존재하지 않습니다";

    // 에러
    public static final String ER_00_0001 = "API 요청 시간초과 오류";
    public static final String ER_00_0002 = "API 메소드 미지원 오류";
    public static final String ER_00_0003 = "API 클라이언트 오류";
    public static final String ER_00_0004 = "API 서버오류";
    public static final String ER_00_0005 = "기기 고유번호 중복오류";
    public static final String ER_00_0006 = "권한없는 사용자 오류";
    public static final String ER_00_0007 = "Exception E";


}
