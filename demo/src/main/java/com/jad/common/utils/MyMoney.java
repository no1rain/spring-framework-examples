package com.jad.common.utils;

/**
 * @(#) MyMoney.java
 * 
 */  

import java.text.DecimalFormat;

/**
 * <pre>
 * 이 Class는 화폐의 단위의 변환을 지원해주는 Util성 클래스이다.
 * MyMoney에서 지원하는 메소드들의 파라미터는 int, float, long, double, String전부를 받아서 처리한다.
 * 
 * example :
 * 
 *          System.out.println(MyMoney.toCommaFormat(120000));
 *          System.out.println(MyMoney.toCommaFormat(120000, true));
 *          // (\) 단위 표시를 추가시킨 스트링 반환
 *          System.out.println(MyMoney.toCommaFormat(070712093801.9283));
 *          // 소수점 이하는 삭제하고 맨 앞이 0인경우 무시한다.
 *          System.out.println(MyMoney.toCommaFormat("398***1293123"));
 *          // 스트링에 숫자이외의 캐릭터는 무시한다.
 *          System.out.println(MyMoney.toHanMoney(1455000002));
 *          // 금액의 한글표기를 할때
 *          System.out.println(MyMoney.toKanjiMoney(1000000002)); // 한문표기표 변환
 *          // 금액의 한문표기를 할때
 * 
 * result :
 * 
 *          120,000
 *          \120,000
 *          70,712,093,802
 *          3,981,293,123
 *          일십사억오천오백만이
 *          壹拾億貳
 * </pre>
 *
 */

public class MyMoney {
	
    /**
     * 숫자를 한글로 담고 있는 Array 
     */
    private static final String hangulA[] =
        { "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };

    /** 
     * basic 한글 화폐단위를 담고 있는 Array 
     */
    private static final String basicA[] = { "", "십", "백", "천" };
    
    /** 
     * base 한글 화폐단위를  담고 있는 Array 
     */
    private static final String baseA[] = { "", "만", "억", "조", "경" };

    /**
     * MyMoney default constructor
     */
    public MyMoney() {}

    /**
     * int값 piMoney 를 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param piMoney  int 값
     * @return  int값을 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(int piMoney) {
        return toCommaFormat((double) piMoney, false);
    }

    /**
     * int값 piMoney 를 isWon이 true일때 (\)마크를 prefix로 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param piMoney  int 값
     * @param isWon  (\)의 여부
     * @return  int값을 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(int piMoney, boolean isWon) {
        return toCommaFormat((double) piMoney, isWon);
    }

    /**
     * float pfMoney 를 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param pfMoney  float 값
     * @return  float값을 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(float pfMoney) {
        return toCommaFormat((double) pfMoney, false);
    }

    /**
     * float값 pfMoney 를 isWon가 true일때(\)마크를 prefix로 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param pfMoney  float 값
     * @param isWon  (\)의 여부
     * @return  float값을 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(float pfMoney, boolean isWon) {
        return toCommaFormat((double) pfMoney, isWon);
    }

    /**
     * long plMoney 를 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param plMoney  long 값
     * @return  long 값 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(long plMoney) {
        return toCommaFormat((double) plMoney, false);
    }

    /**
     * long 값 plMoney 를 isWon가 true일때(\)마크를 prefix로 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param plMoney  long 값
     * @param isWon  (\)의 여부
     * @return  long 값을 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(long plMoney, boolean isWon) {
        return toCommaFormat((double) plMoney, isWon);
    }

    /**
     * String psMoney 를 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param psMoney  String 값
     * @return  String 값 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(String psMoney) {
        return toCommaFormat(psMoney, false);
    }

    /**
     * String 값 psMoney 를 isWon가 true일때(\)마크를 prefix로 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param psMoney  String 값
     * @param isWon  (\)의 여부
     * @return  String 값을 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(String psMoney, boolean isWon) {
        String tempMoney = maskClear2(psMoney);
        return toCommaFormat(Double.parseDouble(tempMoney), isWon);
    }

    /**
     * double pdMoney 를 화폐단위의 표기(###,###)로 바꿔준다.
     * 
     * @param pdMoney  double 값
     * @return  double 값 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(double pdMoney) {
        return toCommaFormat(pdMoney, false);
    }

    /**
     * double 값 pdMoney 를 isWon가 true일때(\)마크를 prefix로 화폐단위의 표기(###,###)로 바꿔준다.
     * 음의 값이 나올경우 isWon 이 true인경우에는 표기하지(\100,000) 않고, false인 경우에는
     * (-100,000)와 같이 표기한다.
     * 
     * @param pdMoney  double 값
     * @param isWon  (\)의 여부
     * @return  double 값을 화폐의 단위로 표현한 String
     */
    public static String toCommaFormat(double pdMoney, boolean isWon) {
    	String preMark = "";
        if(pdMoney < 0) {
            pdMoney *= -1;
            preMark = "-";
        }
        DecimalFormat form = new DecimalFormat("#,###");
        form.setDecimalSeparatorAlwaysShown(false);
        if (isWon)
            return "\\" + form.format(pdMoney);
        else
            return preMark + form.format(pdMoney);
    }

    /**
     * int 값 piMoney 의 금액을 한글로 표시한다.
     * 
     * @param piMoney  int 값
     * @return  금액을 한글로 변환한 String
     */
    public static String toHanMoney(int piMoney) {
        return toHanMoney((double)piMoney);
    }

    /**
     * float 값 pfMoney 의 금액을 한글로 표시한다.
     * 
     * @param pfMoney  float 값
     * @return  금액을 한글로 변환한 String
     */
    public static String toHanMoney(float pfMoney) {
        return toHanMoney((double)pfMoney);
    }

    /**
     * long 값 plMoney 의 금액을 한글로 표시한다.
     * 
     * @param plMoney  long 값
     * @return  금액을 한글로 변환한 String
     */
    public static String toHanMoney(long plMoney) {
        return toHanMoney((double)plMoney);
    }

    /**
     * String 값 psMoney 의 금액을 한글로 표시한다.
     * 
     * @param psMoney  String 값
     * @return  금액을 한글로 변환한 String
     */
    public static String toHanMoney(String psMoney) {
        String tempMoney = maskClear2(psMoney);
        return toHanMoney(Double.parseDouble(tempMoney));
    }

    /**
     * double 값 pdMoney 의 금액을 한글로 표시한다.
     * 
     * @param pdMoney  double 값
     * @return  금액을 한글로 변환한 String
     */
    public static String toHanMoney(double pdMoney) {
        String sMoney = removeComma(toCommaFormat(pdMoney));
        int size = sMoney.length();
        int base = size / 4; //10억일때 2
        int basic = size % 4; //10억일때 2
        
        if ( basic == 0 )
        	base -= 1;
        
        StringBuffer hanMoney = new StringBuffer();
        
        int i = 0;
        while ( base >=0  ) {

            String tempBasic = "";
            if ( basic == 0 )
            	tempBasic = sMoney.substring(i , i+4);
            else
            	tempBasic = sMoney.substring(i , i==0 ? basic : i+4);
            
            if ( basic == 0 )
            	i += 4;
            else
            	i += i==0 ? basic : 4;
            for ( int j = 0 ; j < tempBasic.length() ; j++ ) {
                int curnum = Integer.parseInt(tempBasic.charAt(j)+"");
                if ( curnum == 0 )
                    continue;
                else {
                    hanMoney.append(hangulA[curnum]);
                    hanMoney.append(basicA[tempBasic.length()-j-1]);
                }
            }
            if(Integer.parseInt(tempBasic) == 0)
                base--;
            else
               	hanMoney.append(baseA[base--]);
               	
        }
        return hanMoney.toString();
    }

    /**
     * 입력스트링 s에서 콤마를 제외한 스트링을 반환한다.
     * 
     * @param s 입력스트링
     * @return  입력스트링에서 (,)콤마를 제외한 String
     */
    private static String removeComma(String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(",") != -1) {
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c != ',') {
                    buf.append(c);
                }
            }
            return buf.toString();
        }
        return s;
    }

    /**
     * 스트링에서 숫자를 제외한 캐릭터는 삭제하고 숫자를 가진 스트링만 반환한다.
     * 
     * @param s 입력스트링
     * @return  입력스트링에서 숫자이외의 캐릭트를 삭제한 String
     */
    private static String maskClear2(String xxx) {
        StringBuffer yyy = new StringBuffer("");
        String xyx = new String("");
        if (xxx == null)
            return new String("");

        for (int b = 0; b < xxx.length(); b++) {
            char c = xxx.charAt(b);
            if (Character.isDigit(c) == true) {
                yyy.append(c);
            }
        }
        xyx = yyy.toString();
        return xyx;
    }
}
